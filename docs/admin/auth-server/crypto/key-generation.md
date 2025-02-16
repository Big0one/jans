---
tags:
  - administration
  - auth-server
  - cryptography
---

# Key Generation 

## Generating Cryptographic Keys

The Jans Server is compatible with the [Java KeyGenerator](https://docs.oracle.com/javase/7/docs/api/javax/crypto/KeyGenerator.html) to create new cryptographic keys if needed.

### Backup

* Backup `jansConfWebKeys` attribute data from LDAP. Location of this attribute is: `o=jans > ou=configuration > ou=jans-auth`
* Backup `jans-auth-keys.p12` from `/etc/certs/`

[N.B] Below if `Keystore` location is anywhere except `/etc/certs/` no need to backup.

### Key Generate
To get KeyGenerator, run the following command inside the terminal.

You can put expiration according to your own policy. For testing purpose we are keeping it 2 days.

```commandLine
/opt/jre/bin/java -Dlog4j.defaultInitOverride=true -cp /opt/dist/jans/jans-auth-client-jar-with-dependencies.jar io.jans.as.client.util.KeyGenerator -keystore /etc/certs/jans-auth-keys.p12 -keypasswd <Password> -sig_keys RS256 RS384 -enc_keys RSA1_5 RSA-OAEP -dnname "CN=jansAuth CA Certificates" -expiration 2 > /etc/certs/jans-auth-keys.json
```
Lets see our newly generated crypto keys

```commandLine
keytool -list -v -keystore /etc/certs/jans-auth-keys.p12 -storetype pkcs12 -storepass <password>
```

The jans implementation of KeyGenerator accepts the following arguments:
|Argument	|Description|
|------|------|
|-at|	oxEleven Access Token|
|-dnname|	DN of certificate issuer|
|-key_length|Length of hash key|
|-enc_keys	|Encryption keys to generate (For example: RSA_OAEP, RSA1_5)|
|-expiration|	Expiration in days|
|-expiration_hours|Expiration in hours|
|-h	|Show help|
|-keypasswd|	Key Store password|
|-keystore|	Key Store file (such as /etc/certs/jans-auth-keys.p12)|
|-ox11|	oxEleven Generate Key Endpoint.|
|-sig_keys|	Signature keys to generate. (For example: RS256 RS384 RS512 ES256 ES384 ES512 PS256 PS384 PS512)|
|test_prop_file||
|_keyId|Key name suffix|


