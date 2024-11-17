package com.sbi.epay.encryptdecrypt.service;


import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.NonNull;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Class Name: EncryptionService
 * *
 * Description:This class will be used for  encryption using AES-GSM-NOPADDING algorithm
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

public class EncryptionService {
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(EncryptionService.class);

    /**
     * Encrypts the given SecretKey using the another SecretKey.
     *
     * @param key          the SecretKey by which we want use for encrypting the given key
     * @param keyToEncrypt SecretKey which we want to encrypt
     * @return the String of encrypted and encoded KEK
     */
    public static String encryptSecretKey(@NonNull String key, @NonNull String keyToEncrypt, @NonNull EncryptionDecryptionAlgo algorithm, @NonNull GCMIvLength gcmIvLength, @NonNull GCMTagLength gcmTagLength) throws EncryptionDecryptionException {
        try {
            log.debug("EncryptionService encryptSecretKey key : {} keyToEncrypt: {} ", key, keyToEncrypt);
            byte[] encryptedKeK = encryptValueBySecretKey(algorithm, gcmIvLength, gcmTagLength, decodedSecretKey(keyToEncrypt), decodedSecretKey(key).getEncoded());
            return Base64.getEncoder().encodeToString(encryptedKeK);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException |
                 IllegalStateException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("EncryptionService :: encrypt ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.GENERIC_ERROR_CODE, EncryptionDecryptionConstants.GENERIC_ERROR_MESSAGE);
        }
    }

    /**
     * Encrypts the given plain-text using the SecretKey.
     *
     * @param key          the SecretKey by which we want use for encrypting the given text
     * @param value        String which we want to encrypt
     * @param algorithm    EncryptionDecryptionAlgo algorithm
     * @param gcmIvLength  algorithm iv length
     * @param gcmTagLength algorithm tag length
     * @return the byte[] array of encrypted plain-text
     */
    public byte[] encryptValue(@NonNull SecretKey key, @NonNull String value, @NonNull EncryptionDecryptionAlgo algorithm, @NonNull GCMIvLength gcmIvLength, @NonNull GCMTagLength gcmTagLength) throws EncryptionDecryptionException {
        try {
            log.info("EncryptionService :: encrypt  key : {}, value : {}, algorithm : {}, gcmIvLength : {}, gcmTagLength : {}", key, value, algorithm, gcmIvLength, gcmTagLength);
            return encryptValueBySecretKey(algorithm, gcmIvLength, gcmTagLength, key, value.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException |
                 IllegalStateException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("EncryptionService :: encrypt ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.GENERIC_ERROR_CODE, EncryptionDecryptionConstants.GENERIC_ERROR_MESSAGE);
        }
    }

    /**
     * This method will be used for decode a SecretKey key
     *
     * @param key it specifies the String of encoded SecretKey
     * @return the SecretKey
     */
    private static SecretKey decodedSecretKey(@NonNull String key) {
        log.info("EncryptionService :: decodedSecretKey ");
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private static byte[] encryptValueBySecretKey(EncryptionDecryptionAlgo algorithm, GCMIvLength gcmIvLength, GCMTagLength gcmTagLength, SecretKey key, byte[] value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorithm.getName());
        byte[] iv = new byte[gcmIvLength.getLengthInBytes()];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(gcmTagLength.getLengthInBits(), iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);

        byte[] encryptedValue = cipher.doFinal(value);
        byte[] encryptValueWithIV = new byte[gcmIvLength.getLengthInBytes() + encryptedValue.length];

        System.arraycopy(iv, 0, encryptValueWithIV, 0, gcmIvLength.getLengthInBytes());
        System.arraycopy(encryptedValue, 0, encryptValueWithIV, gcmIvLength.getLengthInBytes(), encryptedValue.length);

        return encryptValueWithIV;
    }

}

