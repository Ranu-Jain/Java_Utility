package com.sbi.epay.encryptdecrypt.service;


import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import jdk.jfr.Description;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Base64;


/**
 *
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
    private static final LoggerUtility log= LoggerFactoryUtility.getLogger(DecryptionService.class);

    /**
     * This method will be used for decryption of KEK .
     *
     * @param kek encrypted and encoded KEK (Key Encryption Key)
     * @param aek encoded AEK(Aggregators Encryption Key)
     * @return the String of original encoded KEK
     */


    public static String decryptKeK(String kek, String aek) throws EncryptionDecryptionException {
        log.info("DecryptionService :: decryptKeK start");
        SecretKey secretKeyKeK = decSecretKeyWithOtherSecretKey(decodedSecretKey(kek).getEncoded(), decodedSecretKey(aek));
        log.info("DecryptionService :: decryptKeK end");
        return Base64.getEncoder().encodeToString(secretKeyKeK.getEncoded());
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

    /**
     * This method will be used for decryption of MEK .
     *
     * @param mek encrypted and encoded MEK (Merchant's Encryption Key)
     * @param kek encrypted and encoded KEK (Key Encryption Key)
     * @param aek encoded AEK(Aggregators Encryption Key)
     * @return a String of a SecretKey MEK encoded
     */
    public static String decryptMeK(String mek, String kek, String aek) throws EncryptionDecryptionException {
        log.info("DecryptionService :: decryptMeK start");
        String secretKeK = decryptKeK(kek, aek);
        SecretKey meK = decSecretKeyWithOtherSecretKey(decodedSecretKey(mek).getEncoded(), decodedSecretKey(secretKeK));
        log.info("DecryptionService :: decryptMeK end");
        return Base64.getEncoder().encodeToString(meK.getEncoded());
    }

    /**
     * decrypts the given key using the other key.
     *
     * @param secretKey          the SecretKey by which we want use for decrypting the given key
     * @param encryptedKeyWithIV byte[] of encrypted key with IV
     * @return the decrypted SecretKey
     */
    private static SecretKey decSecretKeyWithOtherSecretKey(byte[] encryptedKeyWithIV, SecretKey secretKey) throws EncryptionDecryptionException {
        try {
            log.info("DecryptionService :: decSecretKeyWithOtherSecretKey  start");
            byte[] iv = Arrays.copyOfRange(encryptedKeyWithIV, 0, EncryptionDecryptionConstants.GCM_IV_LENGTH);
            byte[] encryptedKey = Arrays.copyOfRange(encryptedKeyWithIV, EncryptionDecryptionConstants.GCM_IV_LENGTH, encryptedKeyWithIV.length);

            Cipher cipher = Cipher.getInstance(EncryptionDecryptionConstants.ENCRYPT_ALGO);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(EncryptionDecryptionConstants.GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] decSecretKeyBytes = cipher.doFinal(encryptedKey);
            log.info("DecryptionService :: decSecretKeyWithOtherSecretKey  end");
            return new SecretKeySpec(decSecretKeyBytes, "AES");
        }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
               InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException | IllegalStateException | IllegalBlockSizeException | BadPaddingException e){
           log.error("DecryptionService :: decSecretKeyWithOtherSecretKey "+e.getMessage());
           throw new EncryptionDecryptionException(EncryptionDecryptionConstants.ENCDEC_1005, MessageFormat.format(EncryptionDecryptionConstants.ENCDEC_1005_msg, secretKey));

        }
    }

    /**
     * decrypts the given encrypted plain-text using the key.
     *
     * @param encryptedPlainTextWithIV byte[] of encrypted key with IV
     * @param secretKey                the SecretKey by which we want use for decrypting the given text
     * @return the decrypted String
     */
    public  String decrypt(byte[] encryptedPlainTextWithIV, SecretKey secretKey) throws EncryptionDecryptionException {
       try {
            log.info("DecryptionService :: decrypt ");
            byte[] iv = Arrays.copyOfRange(encryptedPlainTextWithIV, 0, EncryptionDecryptionConstants.GCM_IV_LENGTH);
            byte[] encryptedKey = Arrays.copyOfRange(encryptedPlainTextWithIV, EncryptionDecryptionConstants.GCM_IV_LENGTH, encryptedPlainTextWithIV.length);
            Cipher cipher = Cipher.getInstance(EncryptionDecryptionConstants.ENCRYPT_ALGO);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(EncryptionDecryptionConstants.GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] decSecretKeyBytes = cipher.doFinal(encryptedKey);

            return new String(decSecretKeyBytes);
       } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException | IllegalStateException |IllegalBlockSizeException |BadPaddingException e) {
           log.error("DecryptionService :: decrypt "+e.getMessage());
           throw new EncryptionDecryptionException(EncryptionDecryptionConstants.ENCDEC_1006,EncryptionDecryptionConstants.ENCDEC_1006_msg);
        }
    }
}

