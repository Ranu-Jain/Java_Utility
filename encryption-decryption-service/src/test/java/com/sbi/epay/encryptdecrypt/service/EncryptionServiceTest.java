package com.sbi.epay.encryptdecrypt.service;

import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *
 * Class Name: EncryptionServiceTest
 * *
 * Description:Contains test cases related to EncryptionService.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
class EncryptionServiceTest {

    KeyGeneratorService keyGeneratorService=new KeyGeneratorService();
    EncryptionService encryptionService = new EncryptionService();
    String valueToHash="TestHashing";
    String valueToHash2="TestHashing";
    String valueToHash3="TestHashingNew";

    String plainText="TEST_ENCRYPTION";

    int keysize=256;
    String encodedAeK="";
    String encodedAndEncryptedKeK="";
    String secretKeKEncoded ="";
    String encodedMeK="";
    String encodedAndEncryptedMeK="";
    String encryptedPlainText="";

    @BeforeEach
    public void setUp() throws Exception {
        encodedAeK=keyGeneratorService.generateKey(keysize);
        secretKeKEncoded = keyGeneratorService.generateKey(keysize);
        encodedAndEncryptedKeK=EncryptionService.encryptKeK(secretKeKEncoded, encodedAeK);
        encodedMeK=keyGeneratorService.generateKey(keysize);
        encodedAndEncryptedMeK=EncryptionService.encryptMeK(encodedAeK,encodedAndEncryptedKeK,encodedMeK);
    }

    @Test
    void generateSHA512Hash() throws Exception{
        byte hashesdValue[]=EncryptionService.generateHash(valueToHash.getBytes(), EncryptionDecryptionConstants.ALGO_HASH);
        byte hashesdValue2[]=EncryptionService.generateHash(valueToHash2.getBytes(), EncryptionDecryptionConstants.ALGO_HASH);
        assertArrayEquals(hashesdValue,hashesdValue2);
        byte hashesdValue3[]=EncryptionService.generateHash(valueToHash3.getBytes(), EncryptionDecryptionConstants.ALGO_HASH);

        assertNotSame(hashesdValue2,hashesdValue3);
    }

    @Test
    void encryptKeK() throws Exception{
        String encodedAndEncryptedKeK1=EncryptionService.encryptKeK(secretKeKEncoded,encodedAeK);
        String encodedKeK=DecryptionService.decryptKeK(encodedAndEncryptedKeK1,encodedAeK);

        assertNotNull(encodedKeK);
        assertEquals(encodedKeK,secretKeKEncoded);
    }

    @Test
    void encryptMeK() throws Exception{
        String encryptedAndEncodedMek=EncryptionService.encryptMeK(encodedAeK,encodedAndEncryptedKeK,encodedMeK);
        String encodedMeK1=DecryptionService.decryptMeK(encryptedAndEncodedMek,encodedAndEncryptedKeK,encodedAeK);

        assertNotNull(encodedMeK1);
        assertEquals(encodedMeK1,encodedMeK);
    }

    @Test
    void encrypt()throws Exception {
        SecretKey meK = KeyProviderService.getDecryptedMEK(encodedAndEncryptedMeK, encodedAndEncryptedKeK, encodedAeK);
        DecryptionService decryptionService = new DecryptionService();
        byte[] encryptedText=encryptionService.encrypt(meK,plainText);
        String decryptedText=decryptionService.decrypt(encryptedText,meK);

        assertNotNull(decryptedText);
        assertEquals(decryptedText,plainText);
    }

    @Test
    void encryptWithInvalidMeK() throws Exception {
        String invalidSecretKey=keyGeneratorService.generateKey(keysize);
        Exception exception=null;
        try {
            SecretKey meK = KeyProviderService.getDecryptedMEK(invalidSecretKey, encodedAndEncryptedKeK, encodedAeK);
            byte[] encryptedText=encryptionService.encrypt(meK,plainText);
        }
        catch (Exception e) {
            exception=e;
        }
        assertNotNull(exception);
    }
}