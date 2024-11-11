package com.sbi.epay.encryptdecrypt.service;

import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import jdk.jfr.Description;

import javax.crypto.SecretKey;

/**
 *
 * Class Name: KeyProviderService
 * *
 * Description:This class will be used for getting the KEY.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Description("This class will provide a SecretKey")
public class KeyProviderService {
    private static final LoggerUtility log= LoggerFactoryUtility.getLogger(KeyGeneratorService.class);

    /**
     * This method will be used to get MEK .
     *
     * @param mek encrypted and encoded MEK (Merchant's Encryption Key)
     * @param kek encrypted and encoded KEK (Key Encryption Key)
     * @param aeK encoded AEK(Aggregators Encryption Key)
     * @return a SecretKey MEK
     */
    public static SecretKey getDecryptedMEK(String mek, String kek, String aeK) throws EncryptionDecryptionException {
        log.info("KeyProviderService :: getDecryptedMEK ");
        String encodedMeK = DecryptionService.decryptMeK(mek, kek, aeK);
        return DecryptionService.decodedSecretKey(encodedMeK);
    }
}
