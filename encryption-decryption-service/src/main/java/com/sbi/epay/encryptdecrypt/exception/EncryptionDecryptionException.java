package com.sbi.epay.encryptdecrypt.exception;

/**
 *
 * Class Name: EncryptionDecryptionException
 * *
 * Description:This class will be used for handling exceptions
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.1
 */
public class EncryptionDecryptionException extends EncryptionDecryptionMgmtException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6449448937575072256L;
	public EncryptionDecryptionException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public EncryptionDecryptionException(String message, String errorCode, String errorMessage) {
		super(message, errorCode, errorMessage);
	}

	public EncryptionDecryptionException(String message, Throwable cause, String errorCode, String errorMessage) {
		super(message, cause, errorCode, errorMessage);
	}

}
