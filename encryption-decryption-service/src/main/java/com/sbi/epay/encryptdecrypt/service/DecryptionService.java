package com.sbi.epay.encryptdecrypt.service;


import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import jdk.jfr.Description;
import lombok.NonNull;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


/**
 * Class Name: DecryptionService
 * *
 * Description:This class will be used for  decryption using AES-GSM-NOPADDING algorithm
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Description("This class will be used for  decryption using AES-GSM-NOPADDING algorithm")
public class DecryptionService {
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(DecryptionService.class);

    /**
     * This method will be used for decryption of Keys .
     *
     * @param key          String which we want to decrypt
     * @param keyToDecrypt the SecretKey by which we want use for decrypt the given text
     * @param algorithm    EncryptionDecryptionAlgo algorithm
     * @param gcmIvLength  algorithm iv length
     * @param gcmTagLength algorithm tag length
     * @return the String of original encoded KEK
     */
    public static String decryptKey(String key, String keyToDecrypt, @NonNull EncryptionDecryptionAlgo algorithm, @NonNull GCMIvLength gcmIvLength, @NonNull GCMTagLength gcmTagLength) throws EncryptionDecryptionException {
        try {
            log.info("DecryptionService :: decryptKeK start");
            byte[] decSecretKeyBytes = decrypt(decodedSecretKey(key).getEncoded(), decodedSecretKey(keyToDecrypt), algorithm, gcmIvLength, gcmTagLength);
            SecretKey secretKeyKeK = new SecretKeySpec(decSecretKeyBytes, "AES");
            log.info("DecryptionService :: decryptKeK end");
            return Base64.getEncoder().encodeToString(secretKeyKeK.getEncoded());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException |
                 IllegalStateException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("DecryptionService :: decrypt ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.GENERIC_ERROR_CODE, EncryptionDecryptionConstants.GENERIC_ERROR_MESSAGE);
        }
    }

    /**
     * This method will be used for decryption of Value .
     *
     * @param value        String which we want to decrypt
     * @param secretKey    the SecretKey by which we want use for decrypt the given text
     * @param algorithm    EncryptionDecryptionAlgo algorithm
     * @param gcmIvLength  algorithm iv length
     * @param gcmTagLength algorithm tag length
     * @return the String of original encoded KEK
     */
    public String decryptValue(@NonNull byte[] value, @NonNull SecretKey secretKey, @NonNull EncryptionDecryptionAlgo algorithm, @NonNull GCMIvLength gcmIvLength, @NonNull GCMTagLength gcmTagLength) throws EncryptionDecryptionException {
        try {
            byte[] decSecretKeyBytes = decrypt(value, secretKey, algorithm, gcmIvLength, gcmTagLength);
            return new String(decSecretKeyBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException |
                 IllegalStateException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("DecryptionService :: decrypt ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.GENERIC_ERROR_CODE, EncryptionDecryptionConstants.GENERIC_ERROR_MESSAGE);
        }
    }

    /**
     * This method will be used for decode SecretKey .
     *
     * @param key encoded SecretKey
     * @return a SecretKey
     */
    public static SecretKey decodedSecretKey(String key) {
        log.info("DecryptionService :: decodedSecretKey start");
        byte[] decodedKey = Base64.getDecoder().decode(key);
        log.info("DecryptionService :: decodedSecretKey end");
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private static byte[] decrypt(byte[] value, SecretKey secretKey, EncryptionDecryptionAlgo algorithm, GCMIvLength gcmIvLength, GCMTagLength gcmTagLength) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        log.info("DecryptionService :: decSecretKeyWithOtherSecretKey  start");
        byte[] iv = Arrays.copyOfRange(value, 0, gcmIvLength.getLengthInBytes());
        byte[] encryptedKey = Arrays.copyOfRange(value, gcmIvLength.getLengthInBytes(), value.length);

        Cipher cipher = Cipher.getInstance(algorithm.getName());
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(gcmTagLength.getLengthInBits(), iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] decSecretKeyBytes = cipher.doFinal(encryptedKey);
        log.info("DecryptionService :: decSecretKeyWithOtherSecretKey  end");
        return decSecretKeyBytes;
    }

}

