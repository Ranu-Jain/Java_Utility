/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.sbi.epay.logging.exceptions;


/**
 * Class Name: InvalidInputException
 * *
 * Description: This for handling invalid input request
 * *
 * Author: V1018400(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class InvalidInputException extends RuntimeException{


    /**
     * Constructor to set the message for Exception.
     * @param message
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
