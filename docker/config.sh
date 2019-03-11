#!/bin/bash 

#--- SETTING DEFAULT VALUES ---
export USE_CLIENT_AUTHENTICATION_FOR_PAT=${USE_CLIENT_AUTHENTICATION_FOR_PAT:=true}
export TRUST_ALL_CERTS=${TRUST_ALL_CERTS:=true}
export TRUST_STORE_PATH=${TRUST_STORE_PATH:=""}
export TRUST_STORE_PASSWORD=${TRUST_STORE_PASSWORD:=""}
export CRYPT_PROVIDER_KEY_STORE_PATH=${CRYPT_PROVIDER_KEY_STORE_PATH:=""}
export CRYPT_PROVIDER_KEY_STORE_PASSWORD=${CRYPT_PROVIDER_KEY_STORE_PASSWORD:=""}
export CRYPT_PROVIDER_DN_NAME=${CRYPT_PROVIDER_DN_NAME:=""}
export SUPPORT_GOOGLE_LOGOUT=${SUPPORT_GOOGLE_LOGOUT:=true}
export STATE_EXPIRATION_IN_MINUTES=${STATE_EXPIRATION_IN_MINUTES:=5}
export NONCE_EXPIRATION_IN_MINUTES=${NONCE_EXPIRATION_IN_MINUTES:=5}
export PUBLIC_OP_KEY_CACHE_EXPIRATION_IN_MINUTES=${PUBLIC_OP_KEY_CACHE_EXPIRATION_IN_MINUTES:=60}
export PROTECT_COMMANDS_WITH_ACCESS_TOKEN=${PROTECT_COMMANDS_WITH_ACCESS_TOKEN:=false}
export UMA2_AUTO_REGISTER_CLAIMS_GATHERING_ENDPOINT_AS_REDIRECT_URI_OF_CLIENT=${UMA2_AUTO_REGISTER_CLAIMS_GATHERING_ENDPOINT_AS_REDIRECT_URI_OF_CLIENT:=true}
export MIGRATION_SOURCE_FOLDER_PATH=${MIGRATION_SOURCE_FOLDER_PATH:=""}
export STORAGE=${STORAGE:=h2}
export DB_FILE_LOCATION=${DB_FILE_LOCATION:="\/opt\/oxd-server\/data\/oxd_db"}
export GLUU_LOG_LEVEL=${GLUU_LOG_LEVEL:=INFO}
export XDI_LOG_LEVEL=${XDI_LOG_LEVEL:=INFO}
export APPLICATION_CONNECTOR_HTTPS_PORT=${APPLICATION_CONNECTOR_HTTPS_PORT:=8443}
export APPLICATION_KEYSTORE_PATH=${APPLICATION_KEYSTORE_PATH:="\/opt\/oxd-server\/conf\/oxd-server.keystore"}
export APPLICATION_KEYSTORE_PASSWORD=${APPLICATION_KEYSTORE_PASSWORD:=example}
export APPLICATION_KEYSTORE_VALIDATE_CERTS=${APPLICATION_KEYSTORE_VALIDATE_CERTS:=false}
export ADMIN_CONNECTOR_HTTPS_PORT=${ADMIN_CONNECTOR_HTTPS_PORT:=8444}
export ADMIN_KEYSTORE_PATH=${ADMIN_KEYSTORE_PATH:="\/opt\/oxd-server\/conf\/oxd-server.keystore"}
export ADMIN_KEYSTORE_PASSWORD=${ADMIN_KEYSTORE_PASSWORD:=example}
export ADMIN_KEYSTORE_VALIDATE_CERTS=${ADMIN_KEYSTORE_VALIDATE_CERTS:=false}
export THRESHOLD=${THRESHOLD:=TRACE}
export CURRENT_LOG_FILENAME=${CURRENT_LOG_FILENAME:="\/var\/log\/oxd-server\/oxd-server.log"}
export ARCHIVED_FILE_COUNT=${ARCHIVED_FILE_COUNT:=7}
export TIME_ZONE=${TIME_ZONE:=UTC}
export MAX_FILE_SIZE=${MAX_FILE_SIZE:=10MB}
export DEFAULT_SITE_CONFIG_OP_HOST=${DEFAULT_SITE_CONFIG_OP_HOST:=""}
export DEFAULT_SITE_CONFIG_OP_DISCOVERY_PATH=${DEFAULT_SITE_CONFIG_OP_DISCOVERY_PATH:=""}
export DEFAULT_SITE_CONFIG_RESPONSE_TYPES=${DEFAULT_SITE_CONFIG_RESPONSE_TYPES:="['code']"}
export DEFAULT_SITE_CONFIG_GRANT_TYPES=${DEFAULT_SITE_CONFIG_GRANT_TYPES:="['authorization_code']"}
export DEFAULT_SITE_CONFIG_ACR_VALUES=${DEFAULT_SITE_CONFIG_ACR_VALUES:="['']"}
export DEFAULT_SITE_CONFIG_SCOPE=${DEFAULT_SITE_CONFIG_SCOPE:="['openid', 'profile', 'email']"}
export DEFAULT_SITE_CONFIG_UI_LOCALES=${DEFAULT_SITE_CONFIG_UI_LOCALES:="['en']"}
export DEFAULT_SITE_CONFIG_CLAIMS_LOCALES=${DEFAULT_SITE_CONFIG_CLAIMS_LOCALES:="['en']"}
export DEFAULT_SITE_CONFIG_CONTACTS=${DEFAULT_SITE_CONFIG_CONTACTS:="[]"}

sed -i "s/\$USE_CLIENT_AUTHENTICATION_FOR_PAT/$USE_CLIENT_AUTHENTICATION_FOR_PAT/g" config_template.yml
sed -i "s/\$TRUST_ALL_CERTS/$TRUST_ALL_CERTS/g" config_template.yml
sed -i "s/\$TRUST_STORE_PATH/$TRUST_STORE_PATH/g" config_template.yml
sed -i "s/\$TRUST_STORE_PASSWORD/$TRUST_STORE_PASSWORD/g" config_template.yml
sed -i "s/\$CRYPT_PROVIDER_KEY_STORE_PATH/$CRYPT_PROVIDER_KEY_STORE_PATH/g" config_template.yml
sed -i "s/\$CRYPT_PROVIDER_KEY_STORE_PASSWORD/$CRYPT_PROVIDER_KEY_STORE_PASSWORD/g" config_template.yml
sed -i "s/\$CRYPT_PROVIDER_DN_NAME/$CRYPT_PROVIDER_DN_NAME/g" config_template.yml
sed -i "s/\$SUPPORT_GOOGLE_LOGOUT/$SUPPORT_GOOGLE_LOGOUT/g" config_template.yml
sed -i "s/\$STATE_EXPIRATION_IN_MINUTES/$STATE_EXPIRATION_IN_MINUTES/g" config_template.yml
sed -i "s/\$NONCE_EXPIRATION_IN_MINUTES/$NONCE_EXPIRATION_IN_MINUTES/g" config_template.yml
sed -i "s/\$PUBLIC_OP_KEY_CACHE_EXPIRATION_IN_MINUTES/$PUBLIC_OP_KEY_CACHE_EXPIRATION_IN_MINUTES/g" config_template.yml
sed -i "s/\$PROTECT_COMMANDS_WITH_ACCESS_TOKEN/$PROTECT_COMMANDS_WITH_ACCESS_TOKEN/g" config_template.yml
sed -i "s/\$UMA2_AUTO_REGISTER_CLAIMS_GATHERING_ENDPOINT_AS_REDIRECT_URI_OF_CLIENT/$UMA2_AUTO_REGISTER_CLAIMS_GATHERING_ENDPOINT_AS_REDIRECT_URI_OF_CLIENT/g" config_template.yml
sed -i "s/\$MIGRATION_SOURCE_FOLDER_PATH/$MIGRATION_SOURCE_FOLDER_PATH/g" config_template.yml
sed -i "s/\$STORAGE/$STORAGE/g" config_template.yml
sed -i "s/\$DB_FILE_LOCATION/$DB_FILE_LOCATION/g" config_template.yml
sed -i "s/\$GLUU_LOG_LEVEL/$GLUU_LOG_LEVEL/g" config_template.yml
sed -i "s/\$XDI_LOG_LEVEL/$XDI_LOG_LEVEL/g" config_template.yml
sed -i "s/\$APPLICATION_CONNECTOR_HTTPS_PORT/$APPLICATION_CONNECTOR_HTTPS_PORT/g" config_template.yml
sed -i "s/\$APPLICATION_KEYSTORE_PATH/$APPLICATION_KEYSTORE_PATH/g" config_template.yml
sed -i "s/\$APPLICATION_KEYSTORE_PASSWORD/$APPLICATION_KEYSTORE_PASSWORD/g" config_template.yml
sed -i "s/\$APPLICATION_KEYSTORE_VALIDATE_CERTS/$APPLICATION_KEYSTORE_VALIDATE_CERTS/g" config_template.yml
sed -i "s/\$ADMIN_CONNECTOR_HTTPS_PORT/$ADMIN_CONNECTOR_HTTPS_PORT/g" config_template.yml
sed -i "s/\$ADMIN_KEYSTORE_PATH/$ADMIN_KEYSTORE_PATH/g" config_template.yml
sed -i "s/\$ADMIN_KEYSTORE_PASSWORD/$ADMIN_KEYSTORE_PASSWORD/g" config_template.yml
sed -i "s/\$ADMIN_KEYSTORE_VALIDATE_CERTS/$ADMIN_KEYSTORE_VALIDATE_CERTS/g" config_template.yml
sed -i "s/\$THRESHOLD/$THRESHOLD/g" config_template.yml
sed -i "s/\$CURRENT_LOG_FILENAME/$CURRENT_LOG_FILENAME/g" config_template.yml
sed -i "s/\$ARCHIVED_FILE_COUNT/$ARCHIVED_FILE_COUNT/g" config_template.yml
sed -i "s/\$TIME_ZONE/$TIME_ZONE/g" config_template.yml
sed -i "s/\$MAX_FILE_SIZE/$MAX_FILE_SIZE/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_OP_HOST/$DEFAULT_SITE_CONFIG_OP_HOST/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_OP_DISCOVERY_PATH/$DEFAULT_SITE_CONFIG_OP_DISCOVERY_PATH/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_RESPONSE_TYPES/$DEFAULT_SITE_CONFIG_RESPONSE_TYPES/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_GRANT_TYPES/$DEFAULT_SITE_CONFIG_GRANT_TYPES/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_ACR_VALUES/$DEFAULT_SITE_CONFIG_ACR_VALUES/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_SCOPE/$DEFAULT_SITE_CONFIG_SCOPE/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_UI_LOCALES/$DEFAULT_SITE_CONFIG_UI_LOCALES/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_CLAIMS_LOCALES/$DEFAULT_SITE_CONFIG_CLAIMS_LOCALES/g" config_template.yml
sed -i "s/\$DEFAULT_SITE_CONFIG_CONTACTS/$DEFAULT_SITE_CONFIG_CONTACTS/g" config_template.yml

cat /config_template.yml > /opt/oxd-server/conf/oxd-server.yml 
java -Djava.net.preferIPv4Stack=true -cp /opt/oxd-server/lib/bcprov-jdk15on-1.54.jar:/opt/oxd-server/lib/oxd-server.jar org.xdi.oxd.server.OxdServerApplication server /opt/oxd-server/conf/oxd-server.yml
