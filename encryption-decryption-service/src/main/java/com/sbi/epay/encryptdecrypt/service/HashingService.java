package com.sbi.epay.encryptdecrypt.service;

import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.util.HashAlgorithm;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

public class HashingService {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(HashingService.class);

    /**
     * This method will be used for generating HASH values .
     *
     * @param data the data to be hashed
     * @return the byte[] array of HASHED DATA
     */

    public static byte[] generateHash(byte @NonNull [] data, @NonNull HashAlgorithm algorithm) throws EncryptionDecryptionException {
        try {
            log.debug("HashingService :: generateHash  data {}, algorithm {}", data, algorithm);
            MessageDigest digest = MessageDigest.getInstance(algorithm.getAlgorithmName());
            return digest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            log.error("HashingService :: generateHash ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.INVALID_ERROR_CODE, MessageFormat.format(EncryptionDecryptionConstants.INVALID_ERROR_MESSAGE, "hash algorithm"));
        } catch (Exception e) {
            log.error("HashingService :: generateHash ", e);
            throw new EncryptionDecryptionException(EncryptionDecryptionConstants.GENERIC_ERROR_CODE, EncryptionDecryptionConstants.GENERIC_ERROR_MESSAGE);
        }
    }

}
