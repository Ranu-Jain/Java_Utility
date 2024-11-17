package com.sbi.epay.encryptdecrypt.service;


import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.util.KeyGenerationAlgo;
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
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(KeyGeneratorService.class);

    /**
     * this method will generate a SecretKey based on size specified (256/128)
     *
     * @param size it specifies the size of key
     * @return the String of encoded SecretKey
     */
    public String generateKeyByDefaultAlgo(int size) throws EncryptionDecryptionException {
        log.debug("KeyGeneratorService :: generateKey size {}", size);
        return Base64.getEncoder().encodeToString(getSecretKey(size, KeyGenerationAlgo.AES).getEncoded());
    }

    /**
     * this method will generate a SecretKey based on size specified (256/128)
     *
     * @param size              it specifies the size of key
     * @param keyGenerationAlgo it specifies the algorithm for key generation
     * @return the String of encoded SecretKey
     */
    public String generateKeyByAlgo(int size, KeyGenerationAlgo keyGenerationAlgo) throws EncryptionDecryptionException {
        log.debug("KeyGeneratorService :: generateKey size {}, keyGenerationAlgo {} ", size, keyGenerationAlgo);
        return Base64.getEncoder().encodeToString(getSecretKey(size, keyGenerationAlgo).getEncoded());
    }


    /**
     * this method will generate a SecretKey based on size specified (256/128) and key generation algorithm
     *
     * @param keySize           it specifies the size of key
     * @param keyGenerationAlgo it specifies the algorithm for key generation
     * @return the SecretKey of encoded SecretKey
     */
    public SecretKey getSecretKey(int keySize, KeyGenerationAlgo keyGenerationAlgo) throws EncryptionDecryptionException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(keyGenerationAlgo.getAlgorithmName());
            SecureRandom random = new SecureRandom();
            keyGen.init(keySize, random);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException | InvalidParameterException e) {
            log.error("KeyGeneratorService :: getSecretKey ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.GENERIC_ERROR_CODE, EncryptionDecryptionConstants.GENERIC_ERROR_MESSAGE);
        }
    }
}
