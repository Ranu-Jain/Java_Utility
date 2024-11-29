package com.sbi.epay.hazelcast.exception;

/**
 *
 * Class Name: HazelcastException
 * *
 * Description:This class will be used for handling exceptions
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.1
 */
public class HazelcastException extends HazelcastMgmtException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6449448937575072256L;
	public HazelcastException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public HazelcastException(String message, String errorCode, String errorMessage) {
		super(message, errorCode, errorMessage);
	}

	public HazelcastException(String message, Throwable cause, String errorCode, String errorMessage) {
		super(message, cause, errorCode, errorMessage);
	}

}
