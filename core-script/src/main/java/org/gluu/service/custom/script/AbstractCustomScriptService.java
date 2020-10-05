/*
 * oxCore is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */
package org.gluu.service.custom.script;

import com.google.common.base.Optional;
import org.gluu.model.custom.script.CustomScriptType;
import org.gluu.model.custom.script.model.CustomScript;
import io.jans.persist.PersistenceEntryManager;
import io.jans.search.filter.Filter;
import org.gluu.util.OxConstants;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Operations with custom scripts
 *
 * @author Yuriy Movchan Date: 12/03/2014
 * @author Mougang T.Gasmyr
 */
public abstract class AbstractCustomScriptService implements Serializable {

    private static final long serialVersionUID = -6187179012715072064L;

    @Inject
    protected Logger log;

    @Inject
    protected PersistenceEntryManager persistenceEntryManager;

    public void add(CustomScript customScript) {
        persistenceEntryManager.persist(customScript);
    }

    public void update(CustomScript customScript) {
        persistenceEntryManager.merge(customScript);
    }

    public void remove(CustomScript customScript) {
        persistenceEntryManager.remove(customScript);
    }

    public CustomScript getCustomScriptByDn(String customScriptDn, String... returnAttributes) {
        return persistenceEntryManager.find(customScriptDn, CustomScript.class, returnAttributes);
    }

    public CustomScript getCustomScriptByDn(Class<?> customScriptType, String customScriptDn) {
        return (CustomScript) persistenceEntryManager.find(customScriptType, customScriptDn);
    }

    public Optional<CustomScript> getCustomScriptByINum(String baseDn, String inum, String... returnAttributes) {

        final List<Filter> customScriptTypeFilters = new ArrayList<Filter>();

        final Filter customScriptTypeFilter = Filter.createEqualityFilter("inum", inum);
        customScriptTypeFilters.add(customScriptTypeFilter);

        final Filter filter = Filter.createORFilter(customScriptTypeFilters);

        final List<CustomScript> result = persistenceEntryManager.findEntries(baseDn, CustomScript.class, filter, returnAttributes);

        if (result.isEmpty()) {

            return Optional.absent();
        }

        return Optional.of(result.get(0));
    }


    public List<CustomScript> findAllCustomScripts(String[] returnAttributes) {
        String baseDn = baseDn();

        List<CustomScript> result = persistenceEntryManager.findEntries(baseDn, CustomScript.class, null, returnAttributes);

        return result;
    }

    public List<CustomScript> findCustomScripts(List<CustomScriptType> customScriptTypes, String... returnAttributes) {
        String baseDn = baseDn();

        if ((customScriptTypes == null) || (customScriptTypes.size() == 0)) {
            return findAllCustomScripts(returnAttributes);
        }

        List<Filter> customScriptTypeFilters = new ArrayList<Filter>();
        for (CustomScriptType customScriptType : customScriptTypes) {
            Filter customScriptTypeFilter = Filter.createEqualityFilter("oxScriptType", customScriptType.getValue());
            customScriptTypeFilters.add(customScriptTypeFilter);
        }

        Filter filter = Filter.createORFilter(customScriptTypeFilters);

        List<CustomScript> result = persistenceEntryManager.findEntries(baseDn, CustomScript.class, filter, returnAttributes);

        return result;
    }

    public CustomScript getScriptByInum(String inum) {
        return persistenceEntryManager.find(CustomScript.class, buildDn(inum));
    }

    public List<CustomScript> findCustomAuthScripts(String pattern, int sizeLimit) {
        String[] targetArray = new String[]{pattern};
        Filter descriptionFilter = Filter.createSubstringFilter(OxConstants.DESCRIPTION, null, targetArray, null);
        Filter scriptTypeFilter = Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE,
                CustomScriptType.PERSON_AUTHENTICATION);
        Filter displayNameFilter = Filter.createSubstringFilter(OxConstants.DISPLAY_NAME, null, targetArray, null);
        Filter searchFilter = Filter.createORFilter(descriptionFilter, displayNameFilter);

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class,
                Filter.createANDFilter(searchFilter, scriptTypeFilter), sizeLimit);
    }

    public List<CustomScript> findCustomAuthScripts(int sizeLimit) {
        Filter searchFilter = Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE,
                CustomScriptType.PERSON_AUTHENTICATION.getValue());

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class, searchFilter,
                sizeLimit);
    }


    public CustomScript getScriptByDisplayName(String name) {
        Filter searchFilter = Filter.createEqualityFilter("displayName",
                name);
        List<CustomScript> result=persistenceEntryManager.findEntries(baseDn(), CustomScript.class, searchFilter,
                1);
        if(result!=null && !result.isEmpty()){
            return result.get(0);
        }
        return null;
    }
    public int getScriptLevel(CustomScript customScript) {
        if(customScript!=null){
            return customScript.getLevel();
        }
        return -2;
    }

    public List<CustomScript> findOtherCustomScripts(String pattern, int sizeLimit) {
        String[] targetArray = new String[]{pattern};
        Filter descriptionFilter = Filter.createSubstringFilter(OxConstants.DESCRIPTION, null, targetArray, null);
        Filter scriptTypeFilter = Filter.createNOTFilter(
                Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE, CustomScriptType.PERSON_AUTHENTICATION));
        Filter displayNameFilter = Filter.createSubstringFilter(OxConstants.DISPLAY_NAME, null, targetArray, null);
        Filter searchFilter = Filter.createORFilter(descriptionFilter, displayNameFilter);

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class,
                Filter.createANDFilter(searchFilter, scriptTypeFilter), sizeLimit);
    }

    public List<CustomScript> findScriptByType(CustomScriptType type, int sizeLimit) {
        Filter searchFilter = Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE, type);

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class, searchFilter,
                sizeLimit);
    }

    public List<CustomScript> findScriptByType(CustomScriptType type) {
        Filter searchFilter = Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE, type);

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class, searchFilter, null);
    }

    public List<CustomScript> findScriptByPatternAndType(String pattern, CustomScriptType type, int sizeLimit) {
        String[] targetArray = new String[]{pattern};
        Filter descriptionFilter = Filter.createSubstringFilter(OxConstants.DESCRIPTION, null, targetArray, null);
        Filter displayNameFilter = Filter.createSubstringFilter(OxConstants.DISPLAY_NAME, null, targetArray, null);
        Filter searchFilter = Filter.createORFilter(descriptionFilter, displayNameFilter);
        Filter typeFilter = Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE, type);

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class,
                Filter.createANDFilter(searchFilter, typeFilter), sizeLimit);
    }

    public List<CustomScript> findScriptByPatternAndType(String pattern, CustomScriptType type) {
        String[] targetArray = new String[]{pattern};
        Filter descriptionFilter = Filter.createSubstringFilter(OxConstants.DESCRIPTION, null, targetArray, null);
        Filter displayNameFilter = Filter.createSubstringFilter(OxConstants.DISPLAY_NAME, null, targetArray, null);
        Filter searchFilter = Filter.createORFilter(descriptionFilter, displayNameFilter);
        Filter typeFilter = Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE, type);

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class,
                Filter.createANDFilter(searchFilter, typeFilter), null);
    }

    public List<CustomScript> findOtherCustomScripts(int sizeLimit) {
        Filter searchFilter = Filter.createNOTFilter(
                Filter.createEqualityFilter(OxConstants.SCRIPT_TYPE, CustomScriptType.PERSON_AUTHENTICATION));

        return persistenceEntryManager.findEntries(baseDn(), CustomScript.class, searchFilter,
                sizeLimit);
    }

    public String buildDn(String customScriptId) {
        final StringBuilder dn = new StringBuilder();
        dn.append(String.format("inum=%s,", customScriptId));
        dn.append(baseDn());
        return dn.toString();
    }

    public abstract String baseDn();

}
