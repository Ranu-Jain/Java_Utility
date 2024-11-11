package com.sbi.epay.encryptdecrypt.exception;

import com.sbi.epay.encryptdecrypt.dto.ErrorInfoDto;
/**
 *
 * Class Name: EncryptionDecryptionMgmtException
 * *
 * Description:This class will be used for handling exceptions
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class EncryptionDecryptionMgmtException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4462743110687012478L;
	
	
	protected ErrorInfoDto errorInfo;
	public EncryptionDecryptionMgmtException(String errorCode, String errorMessage) {
		super();
		errorInfo = fromErrorInfo(errorCode, errorMessage);
	}

	public EncryptionDecryptionMgmtException(String message, Throwable cause, String errorCode, String errorMessage) {
		super(message, cause);
		errorInfo = fromErrorInfo(errorCode, errorMessage);
	}

	public EncryptionDecryptionMgmtException(String message, String errorCode, String errorMessage) {
		super(message);
		errorInfo = fromErrorInfo(errorCode, errorMessage);
	}

	private ErrorInfoDto fromErrorInfo(String errorCode, String errorMessage) {
		ErrorInfoDto errorInfo = null;

		errorInfo = new ErrorInfoDto();
		errorInfo.setErrorCode(errorCode);
		errorInfo.setErrorMessage(errorMessage);
		return errorInfo;
	}

	public ErrorInfoDto getErrorInfo() {
		return errorInfo;
	}

}


