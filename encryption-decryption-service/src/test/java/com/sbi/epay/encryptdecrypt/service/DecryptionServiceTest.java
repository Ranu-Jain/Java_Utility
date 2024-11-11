package com.sbi.epay.encryptdecrypt.service;

import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * Class Name: DecryptionServiceTest
 * *
 * Description:Contains test cases related to DecryptionService.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@SpringBootTest
class DecryptionServiceTest {

    KeyGeneratorService keyGeneratorService=new KeyGeneratorService();
    EncryptionDecryptionUtil encryptionDecryptionUtil=new EncryptionDecryptionUtil();



    int keysize=256;
    String encodedAeK="";
    String encodedAndEncryptedKeK="";
    String secretKeKEncoded ="";
    String encodedMeK="";
    String encodedAndEncryptedMeK="";
    String encryptedPlainText="";
    String plainText="TEST_DECRYPTION";


    @BeforeEach
    public void setUp() {
        try {
            encodedAeK=keyGeneratorService.generateKey(keysize);
            secretKeKEncoded = keyGeneratorService.generateKey(keysize);
            encodedAndEncryptedKeK=EncryptionService.encryptKeK(secretKeKEncoded, encodedAeK);
            encodedMeK=keyGeneratorService.generateKey(keysize);
            encodedAndEncryptedMeK=EncryptionService.encryptMeK(encodedAeK,encodedAndEncryptedKeK,encodedMeK);
            encryptedPlainText=encryptionDecryptionUtil.encryptByKMS(plainText,encodedAndEncryptedMeK,encodedAndEncryptedKeK,encodedAeK);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void decryptKeK() {
        try {
            String secretKeyKeK = DecryptionService.decryptKeK(encodedAndEncryptedKeK, encodedAeK);
            assertNotNull(secretKeyKeK);
            assertEquals(secretKeKEncoded, secretKeyKeK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void decryptWithInvalidKeK() {

        Exception exception=null;
        try {
            String invalidSecretKey=keyGeneratorService.generateKey(keysize);
            DecryptionService.decryptKeK(invalidSecretKey,encodedAeK);
        }
        catch (Exception e) {
            exception=e;
        }
        assertNotNull(exception);
    }

    private SecretKey getSecretKey(int keySize, String algo) {
        try{
        KeyGenerator keyGen = KeyGenerator.getInstance(algo);
        SecureRandom random = new SecureRandom();
        keyGen.init(keySize, random);
        return keyGen.generateKey();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void decodedSecretKey()  {
        try {
            SecretKey secretKey = getSecretKey(256, "AES");
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            SecretKey secretKeyAfterDecoding = DecryptionService.decodedSecretKey(encodedKey);
            assertEquals(secretKey, secretKeyAfterDecoding);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void decryptMeK(){
        try {
            String decryptedMek = DecryptionService.decryptMeK(encodedAndEncryptedMeK, encodedAndEncryptedKeK, encodedAeK);

            assertNotNull(decryptedMek);
            assertEquals(decryptedMek, encodedMeK);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void decryptWithInvalidMeK() {


        Exception exception=null;
        try {
            String invalidSecretKey=keyGeneratorService.generateKey(keysize);
            DecryptionService.decryptMeK(invalidSecretKey,encodedAndEncryptedKeK,encodedAeK);
        }
        catch (Exception e) {
            exception=e;
            e.printStackTrace();
        }
        assertNotNull(exception);
    }

    @Test
    void decrypt() {
        try {
            SecretKey meK = KeyProviderService.getDecryptedMEK(encodedAndEncryptedMeK, encodedAndEncryptedKeK, encodedAeK);
            DecryptionService decryptionService = new DecryptionService();
            byte[] strDecrypt = Base64.getDecoder().decode(encryptedPlainText);
            String decryptedText = decryptionService.decrypt(strDecrypt, meK);
            assertNotNull(decryptedText);
            assertEquals(decryptedText, plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}