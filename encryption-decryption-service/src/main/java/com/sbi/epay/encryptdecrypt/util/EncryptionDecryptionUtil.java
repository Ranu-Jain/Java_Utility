package com.sbi.epay.encryptdecrypt.util;


import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.KeyProviderService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 *
 * Class Name: EncryptionDecryptionUtil
 * *
 * Description:This class will be used for  getting encrypted and decrypted data
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class EncryptionDecryptionUtil {
    /**
     * Encrypts the given plaintext using the MEK.
     *
     * @param value the data to be encrypted
     * @param mek   a String which contains encrypted and encoded MEK(Merchants Encryption Key)
     * @param kek   a String which contains encrypted and encoded KEK(Key Encryption Key)
     * @param aek   a String which contains encoded AEK(Aggregators Encryption Key)
     * @return the Data as encrypted and encoded String
     */
    private static final LoggerUtility log=LoggerFactoryUtility.getLogger(EncryptionDecryptionUtil.class);

    public String encryptByKMS(String value, String mek, String kek, String aek) throws EncryptionDecryptionException {
        log.info("EncryptionDecryptionUtil :: encryptByKMS");
        SecretKey decodedMEK = KeyProviderService.getDecryptedMEK(mek, kek, aek);
        EncryptionService encryptionService = new EncryptionService();
        byte[] encryptStrArr = encryptionService.encrypt(decodedMEK, value);
        return Base64.getEncoder().encodeToString(encryptStrArr);
    }

    /**
     * decrypt the given plaintext using the MEK.
     *
     * @param value the encrypted and encoded String
     * @param mek   a String which contains encrypted and encoded MEK(Merchants Encryption Key)
     * @param kek   a String which contains encrypted and encoded KEK(Key Encryption Key)
     * @param aek   a String which contains encoded AEK(Aggregators Encryption Key)
     * @return the Data as encrypted and encoded String
     */
    public String decryptByKMS(String value, String mek, String kek, String aek) throws EncryptionDecryptionException {
        log.info("EncryptionDecryptionUtil :: decryptByKMS");
        SecretKey MeK = KeyProviderService.getDecryptedMEK(mek, kek, aek);
        DecryptionService decryptionService = new DecryptionService();
        byte[] strDecrypt = Base64.getDecoder().decode(value);
        return decryptionService.decrypt(strDecrypt, MeK);
    }

}
