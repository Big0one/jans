/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2022, Janssen Project
 */
package io.jans.as.server.ssa.ws.rs;

import io.jans.as.common.model.ssa.Ssa;
import io.jans.as.common.model.ssa.SsaState;
import io.jans.as.model.config.StaticConfiguration;
import io.jans.as.model.config.WebKeysConfiguration;
import io.jans.as.model.configuration.AppConfiguration;
import io.jans.as.model.crypto.AbstractCryptoProvider;
import io.jans.as.model.crypto.signature.SignatureAlgorithm;
import io.jans.as.model.jwt.Jwt;
import io.jans.as.model.ssa.SsaScopeType;
import io.jans.as.server.model.common.ExecutionContext;
import io.jans.as.server.model.token.JwtSigner;
import io.jans.orm.PersistenceEntryManager;
import io.jans.orm.exception.EntryPersistenceException;
import io.jans.orm.search.filter.Filter;
import io.jans.util.StringHelper;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static io.jans.as.model.ssa.SsaRequestParam.*;

/**
 * Provides SSA methods to save, update, search, etc.
 */
@Stateless
@Named
public class SsaService {

    @Inject
    private Logger log;

    @Inject
    private PersistenceEntryManager persistenceEntryManager;

    @Inject
    private AppConfiguration appConfiguration;

    @Inject
    private StaticConfiguration staticConfiguration;

    /**
     * Persist SSA in to the database
     *
     * @param ssa New SSA that should be created.
     */
    public void persist(Ssa ssa) {
        persistenceEntryManager.persist(ssa);
    }

    /**
     * Updates an existing SSA in the database
     *
     * @param ssa SSA to be updated.
     */
    public void merge(Ssa ssa) {
        persistenceEntryManager.merge(ssa);
    }

    /**
     * Find SSA based on "jti"
     * <p>
     * Method returns null if the SSA is not found.
     * </p>
     *
     * @param jti Unique identifier
     * @return {@link Ssa} found
     */
    public Ssa findSsaByJti(String jti) {
        try {
            return persistenceEntryManager.find(Ssa.class, getDnForSsa(jti));
        } catch (EntryPersistenceException e) {
            return null;
        }
    }

    /**
     * Get list of SSAs based on "jti", "org_id" or "status" filters
     * <p>
     * If the client only has ssa.portal scope, then it is filtered by the client that created the SSA
     * </p>
     *
     * @param jti      Unique identifier
     * @param orgId    Organization ID
     * @param status   Status
     * @param clientId Client ID
     * @param scopes   List of scope
     * @return List of SSA
     */
    public List<Ssa> getSsaList(String jti, Long orgId, SsaState status, String clientId, String[] scopes) {
        List<Filter> filters = new ArrayList<>();
        if (hasDeveloperScope(Arrays.asList(scopes))) {
            filters.add(Filter.createEqualityFilter("creatorId", clientId));
        }
        if (jti != null) {
            filters.add(Filter.createEqualityFilter("inum", jti));
        }
        if (orgId != null) {
            filters.add(Filter.createEqualityFilter("o", orgId));
        }
        if (status != null) {
            filters.add(Filter.createEqualityFilter("jansState", status));
        }
        Filter filter = null;
        if (!filters.isEmpty()) {
            filter = Filter.createANDFilter(filters);
            log.trace("Filter with AND created: " + filters);
        }
        return persistenceEntryManager.findEntries(getDnForSsa(null), Ssa.class, filter);
    }

    /**
     * Generates a new JWT using a given SSA.
     * <p>
     * Method throws an {@link RuntimeException} if it fails to create the jwt
     * </p>
     * <p>
     * Method executes a postProcessor in case it has been sent in the execution context parameter.
     * </p>
     *
     * @param ssa                  Ssa
     * @param executionContext     Execution context
     * @param webKeysConfiguration Web keys configuration
     * @param cryptoProvider       Crypto provider
     * @return Jwt with SSA structure
     */
    public Jwt generateJwt(Ssa ssa, ExecutionContext executionContext, WebKeysConfiguration webKeysConfiguration, AbstractCryptoProvider cryptoProvider) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.fromString(appConfiguration.getSsaConfiguration().getSsaSigningAlg());
            JwtSigner jwtSigner = new JwtSigner(appConfiguration, webKeysConfiguration, signatureAlgorithm, null, null, cryptoProvider);
            Jwt jwt = jwtSigner.newJwt();
            jwt.getClaims().setJwtId(ssa.getId());
            jwt.getClaims().setIssuedAt(ssa.getCreationDate());
            jwt.getClaims().setExpirationTime(ssa.getExpirationDate());
            jwt.getClaims().setClaim(SOFTWARE_ID.getName(), ssa.getAttributes().getSoftwareId());
            jwt.getClaims().setClaim(ORG_ID.getName(), Long.parseLong(ssa.getOrgId()));
            jwt.getClaims().setClaim(SOFTWARE_ROLES.getName(), ssa.getAttributes().getSoftwareRoles());
            jwt.getClaims().setClaim(GRANT_TYPES.getName(), ssa.getAttributes().getGrantTypes());

            Jwt jwr = jwtSigner.sign();
            if (executionContext.getPostProcessor() != null) {
                executionContext.getPostProcessor().apply(jwr);
            }
            return jwr;
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("Failed to sign session jwt! " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a {@link Response.ResponseBuilder} with status 422
     *
     * @return Response builder
     */
    public Response.ResponseBuilder createUnprocessableEntityResponse() {
        return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).type(MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * Create a {@link Response.ResponseBuilder} with status 406
     *
     * @return Response builder
     */
    public Response.ResponseBuilder createNotAcceptableResponse() {
        return Response.status(HttpStatus.SC_NOT_ACCEPTABLE).type(MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * Check if there is only one "ssa.developer" scope
     *
     * @param scopes List of scope
     * @return true if is only one "ssa.developer", or false otherwise
     */
    private boolean hasDeveloperScope(List<String> scopes) {
        Iterator<String> scopesIterator = scopes.iterator();
        boolean result = false;
        while (scopesIterator.hasNext()) {
            String scope = scopesIterator.next();
            if (scope.equals(SsaScopeType.SSA_ADMIN.getValue()) || scope.equals(SsaScopeType.SSA_PORTAL.getValue())) {
                return false;
            } else if (scope.equals(SsaScopeType.SSA_DEVELOPER.getValue())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Build a DN for SSA
     *
     * @param ssaId SSA ID
     * @return DN of SSA
     */
    private String getDnForSsa(String ssaId) {
        String baseDn = staticConfiguration.getBaseDn().getSsa();
        if (StringHelper.isEmpty(ssaId)) {
            return baseDn;
        }
        return String.format("inum=%s,%s", ssaId, baseDn);
    }
}
