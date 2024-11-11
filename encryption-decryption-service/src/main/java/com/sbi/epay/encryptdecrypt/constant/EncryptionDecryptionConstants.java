package com.sbi.epay.encryptdecrypt.constant;

/**
 *
 * Class Name: EncryptionDecryptionConstants
 * *
 * Description:This class is used for defining constant
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

public final class EncryptionDecryptionConstants {
    public static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    public static final String ALGO = "AES/GCM/NoPadding";
    public static final String ALGO_HASH ="SHA-512";


    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;
    //public static final int KEY_LENGTH = 256;//to do
    //public static final String KEY_GENERATION_ALGO="AES";//to do
    public static final String ENCDEC_1001="1001";
    public static final String ENCDEC_1001_msg="Error in generating SecretKey.";
    public static final String ENCDEC_1002="1002";
    public static final String ENCDEC_1002_msg="Invalid Data {0} ";
    public static final String ENCDEC_1003="1003";
    public static final String ENCDEC_1003_msg="Error in encrypting a SecretKey.";
    public static final String ENCDEC_1004="1004";
    public static final String ENCDEC_1004_msg="Error in encrypting given value.";
    public static final String ENCDEC_1005="1005";
    public static final String ENCDEC_1005_msg="Invalid Key {0}";
    public static final String ENCDEC_1006="1006";
    public static final String ENCDEC_1006_msg="Error in decrypting text ; please check your key.";



}
