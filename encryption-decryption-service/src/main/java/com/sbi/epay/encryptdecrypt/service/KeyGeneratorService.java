package com.sbi.epay.encryptdecrypt.service;


import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import jdk.jfr.Description;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * Class Name: KeyGeneratorService
 * *
 * Description:This class will be used for generating keys and for there encryption and decryption.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Description("This class will be used for generating keys and for there encryption and decryption.")
public class KeyGeneratorService {
    private static final LoggerUtility log= LoggerFactoryUtility.getLogger(KeyGeneratorService.class);

    /**
     * this method will generate a SecretKey based on size specified (256/128)
     *
     * @param size it specifies the size of key
     * @return the String of encoded SecretKey
     */
    public String generateKey(int size)  throws EncryptionDecryptionException {
        log.info("KeyGeneratorService :: generateKey ");
        return Base64.getEncoder().encodeToString(getSecretKey(size).getEncoded());
    }

    /**
     * this method will generate a SecretKey based on size specified (256/128) and key generation algorithm
     *
     * @param keySize it specifies the size of key
     * @return the SecretKey of encoded SecretKey
     */
    private SecretKey getSecretKey(int keySize) throws EncryptionDecryptionException {
        log.info("KeyGeneratorService :: getSecretKey-keySize ");
        return getSecretKey(keySize, "AES");
    }

    /**
     * this method will generate a SecretKey based on size specified (256/128) and key generation algorithm
     *
     * @param keySize it specifies the size of key
     * @param algo    it specifies the algorithm for key generation
     * @return the SecretKey of encoded SecretKey
     */
    private SecretKey getSecretKey(int keySize, String algo) throws EncryptionDecryptionException {
        try {
            log.info("KeyGeneratorService :: getSecretKey-keySize,algo ");
            KeyGenerator keyGen = KeyGenerator.getInstance(algo);
            SecureRandom random = new SecureRandom();
            keyGen.init(keySize, random);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException | NullPointerException | InvalidParameterException e) {
            log.error("KeyGeneratorService :: getSecretKey "+e.getMessage());
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.ENCDEC_1001,EncryptionDecryptionConstants.ENCDEC_1001_msg);
        }
    }
}
