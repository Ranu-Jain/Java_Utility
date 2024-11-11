## Features
-AES encryption and decryption
-GSM mode for authenticated encryption
-Secure random key generation 
-Secure key management
-No padding required

##Requirements
-Java 8 or higher
-Gradle(for dependency management)
 
##Usage-
1)To encrypt and decrypt data use EncryptionDecryptionUtil class it contains following methods :- 

    -encryptByKMS(String value, String mek, String kek, String aek) 
        value - the data to be encrypted
        mek -   a String which contains encrypted and encoded MEK(Merchants Encryption Key)
        kek -   a String which contains encrypted and encoded KEK(Key Encryption Key)
        aek -   a String which contains encoded AEK(Aggregators Encryption Key)
    this method will be used for encryption; it will return encoded string.

    -decryptByKMS(String value, String mek, String kek, String aek)
        value - the encrypted and encoded String
        mek   - a String which contains encrypted and encoded MEK(Merchants Encryption Key)
        kek   - a String which contains encrypted and encoded KEK(Key Encryption Key)
        aek   - a String which contains encoded AEK(Aggregators Encryption Key)
    this method will be used for decryption; it will return origanal value.

2)To get MEK use KeyProviderService class it contains following method 

    -getDecryptedMEK(String mek, String kek, String aeK)
        mek -   a String which contains encrypted and encoded MEK(Merchants Encryption Key)
        kek -   a String which contains encrypted and encoded KEK(Key Encryption Key)
        aek -   a String which contains encoded AEK(Aggregators Encryption Key)
    this method will return SecretKey MEK.

3)To generate a key use KeyGeneratorService class it contains following method

    -generateKey(int size)
        size - it specifies the key size.
    the String of encoded SecretKey.

4)To encrypt the KEK and MEK use EncryptionService class it contains following method

    -encryptKeK(String kek, String aek)
        kek - it specifies the encoded KEK
        aek - it aek it specifies the encoded aek
    this method will return the String of encrypted and encoded KEK.

    -encryptMeK(String aek, String kek, String mek)
        aek - it specifies the encoded AEK(Aggregators Encryption Key)
        kek - it specifies the encrypted and encoded KEK(Key Encryption Key)
        mek - it specifies the encoded MEK(Merchants Encryption Key)
    this method will return the String of encrypted and encoded MEK

5)To decrypt the KEK and MEK use DecryptionService class it contains following method
    
    -decryptKeK(String kek, String aek)
        kek - it specifies the encrypted and encoded KEK (Key Encryption Key)
        aek - it specifies the aek encoded AEK(Aggregators Encryption Key)
    this method will return the String of original encoded KEK

    -decryptMeK(String mek, String kek, String aek)
        mek - encrypted and encoded MEK (Merchant's Encryption Key)
        kek - encrypted and encoded KEK (Key Encryption Key)
        aek - aek encoded AEK(Aggregators Encryption Key)
    this method will return a String of a MEK encoded

