package com.sbi.epay.encryptdecrypt.service;


import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.text.MessageFormat;
import java.util.Base64;

/**
 *
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
    private static final LoggerUtility log= LoggerFactoryUtility.getLogger(EncryptionService.class);

    /**
     * This method will be used for generating HASH values .
     *
     * @param data the data to be hashed
     * @param algo algorithm for HASHING SHA-512/SHA-256
     * @return the byte[] array of HASHED DATA
     */

    public static byte[] generateHash(byte[] data, String algo) throws EncryptionDecryptionException {
        try {
            log.info("EncryptionService :: generateHash started ");
            MessageDigest digest = MessageDigest.getInstance(EncryptionDecryptionConstants.ALGO_HASH);//"SHA-512"
            log.info("EncryptionService :: generateHash end ");
            return digest.digest(data);
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            log.error("EncryptionService :: generateHash "+e.getMessage());
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.ENCDEC_1002, MessageFormat.format(EncryptionDecryptionConstants.ENCDEC_1002_msg,data));
        }
    }

    /**
     * this method will be used for encrypting KEK
     *
     * @param kek it specifies the encoded KEK
     * @param aek it specifies the encoded aek
     * @return the String of encrypted and encoded KEK
     */
    public static String encryptKeK(String kek, String aek) throws EncryptionDecryptionException {
        log.info("EncryptionService :: encryptKeK-kek,aek ");
        EncryptionService encryptionService = new EncryptionService();
        byte[] encryptedKeK = encryptionService.encryptSecretKeyWithOtherSecretKey(decodedSecretKey(kek), decodedSecretKey(aek));
        return Base64.getEncoder().encodeToString(encryptedKeK);
    }

    /**
     * this method will be used for encrypting MEK
     *
     * @param aek it specifies the encoded AEK(Aggregators Encryption Key)
     * @param kek it specifies the encrypted and encoded KEK(Key Encryption Key)
     * @param mek it specifies the encoded MEK(Merchants Encryption Key)
     * @return the String of encrypted and encoded MEK
     */
    public static String encryptMeK(String aek, String kek, String mek)  throws EncryptionDecryptionException {
        log.info("EncryptionService :: encryptKeK-kek,aek,mek ");
        return encryptKeK(mek, DecryptionService.decryptKeK(kek, aek));
    }

    /**
     * this method will be used for decode a SecretKey key
     *
     * @param key it specifies the String of encoded SecretKey
     * @return the SecretKey
     */
    private static SecretKey decodedSecretKey(String key) {
        log.info("EncryptionService :: decodedSecretKey ");
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    /**
     * Encrypts the given plain-text using the SecretKey.
     *
     * @param key   the SecretKey by which we want use for encrypting the given text
     * @param value String which we want to encrypt
     * @return the byte[] array of encrypted plain-text
     */
    public byte[] encrypt(SecretKey key, String value) throws EncryptionDecryptionException {
        try {
            log.info("EncryptionService :: encrypt ");
            Cipher cipher = Cipher.getInstance(EncryptionDecryptionConstants.ENCRYPT_ALGO);
            byte[] iv = new byte[EncryptionDecryptionConstants.GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(EncryptionDecryptionConstants.GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);

            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            byte[] encryptValueWithIV = new byte[EncryptionDecryptionConstants.GCM_IV_LENGTH + encryptedValue.length];

            System.arraycopy(iv, 0, encryptValueWithIV, 0, EncryptionDecryptionConstants.GCM_IV_LENGTH);
            System.arraycopy(encryptedValue, 0, encryptValueWithIV, EncryptionDecryptionConstants.GCM_IV_LENGTH, encryptedValue.length);

            return encryptValueWithIV;
        }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException | IllegalStateException |IllegalBlockSizeException |BadPaddingException e){
              log.error("EncryptionService :: encrypt "+e.getMessage());
           throw new EncryptionDecryptionException(EncryptionDecryptionConstants.ENCDEC_1004,EncryptionDecryptionConstants.ENCDEC_1004_msg);
        }
    }

    /**
     * Encrypts the given key using the other key.
     *
     * @param keyToEncrypt SecretKey which we want to encrypt
     * @param key          the SecretKey by which we want use for encrypting the given key
     * @return the byte[] of encrypted key
     */
    private byte[] encryptSecretKeyWithOtherSecretKey(SecretKey key, SecretKey keyToEncrypt) throws EncryptionDecryptionException {

        try {
            log.info("EncryptionService :: encryptSecretKeyWithOtherSecretKey ");
            Cipher cipher = Cipher.getInstance(EncryptionDecryptionConstants.ENCRYPT_ALGO);
            byte[] iv = new byte[EncryptionDecryptionConstants.GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(EncryptionDecryptionConstants.GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keyToEncrypt, gcmParameterSpec);

            byte[] encryptedKey = cipher.doFinal(key.getEncoded());
            byte[] encryptKeyWithIV = new byte[EncryptionDecryptionConstants.GCM_IV_LENGTH + encryptedKey.length];//60

            System.arraycopy(iv, 0, encryptKeyWithIV, 0, EncryptionDecryptionConstants.GCM_IV_LENGTH);
            System.arraycopy(encryptedKey, 0, encryptKeyWithIV, EncryptionDecryptionConstants.GCM_IV_LENGTH, encryptedKey.length);

            return encryptKeyWithIV;
        }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |InvalidAlgorithmParameterException | IllegalArgumentException | UnsupportedOperationException | IllegalStateException |IllegalBlockSizeException |BadPaddingException e){
              log.error("EncryptionService :: encryptSecretKeyWithOtherSecretKey "+e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.ENCDEC_1003,EncryptionDecryptionConstants.ENCDEC_1003_msg);
        }

    }

}

