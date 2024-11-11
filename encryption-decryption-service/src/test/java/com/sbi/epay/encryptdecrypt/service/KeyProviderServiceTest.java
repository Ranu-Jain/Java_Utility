package com.sbi.epay.encryptdecrypt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * Class Name: KeyProviderServiceTest
 * *
 * Description:Contains test cases related to KeyProviderService.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
class KeyProviderServiceTest {
    int keysize=256;
    KeyGeneratorService keyGeneratorService=new KeyGeneratorService();
    String encodedAeK="";
    String secretKeKEncoded ="";
    String encodedAndEncryptedKeK="";
    String encodedMeK="";
    @BeforeEach
    public void setUp() throws Exception {

        KeyGeneratorService keyGeneratorService=new KeyGeneratorService();
        encodedAeK=keyGeneratorService.generateKey(256);
        secretKeKEncoded = keyGeneratorService.generateKey(keysize);
        encodedAndEncryptedKeK=EncryptionService.encryptKeK(secretKeKEncoded, encodedAeK);
        encodedMeK=keyGeneratorService.generateKey(keysize);
    }

    @Test
    void getDecryptedMEK() throws Exception{
        String encodedAndEncryptedMeK=EncryptionService.encryptMeK(encodedAeK,encodedAndEncryptedKeK,encodedMeK);
        SecretKey secretKey=KeyProviderService.getDecryptedMEK(encodedAndEncryptedMeK,encodedAndEncryptedKeK,encodedAeK);
        assertEquals(secretKey,DecryptionService.decodedSecretKey(encodedMeK));
    }

    @Test
    void decryptWithInvalidMeK() throws Exception {
        String invalidSecretKey=keyGeneratorService.generateKey(keysize);

        Exception exception=null;
        try {
            SecretKey secretKey=KeyProviderService.getDecryptedMEK(invalidSecretKey,encodedAndEncryptedKeK,encodedAeK);
        }
        catch (Exception e) {
            exception=e;
        }
        assertNotNull(exception);
    }
}