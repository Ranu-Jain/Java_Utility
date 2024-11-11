package com.sbi.epay.util;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name: NotificationConstant
 * *
 * Description:NotificationConstant this class mainly contains CONSTANT Value we use constant on different part of SMS and EMAIL related Classes
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class NotificationConstant {

    public static final String SMS_CODE_001 = "SMS_001";
    public static final String SMS_MSG_001 = "Mobile Number Can not be blank";
    public static final String SMS_CODE_002 = "SMS_002";
    public static final String SMS_MSG_002 = "Mobile Number should not contain Space";
    public static final String SMS_CODE_003 = "SMS_003";
    public static final String SMS_MSG_003 = "Mobile Number should not contain More the 10 Character";
    public static final String SMS_CODE_004 = "SMS_004";
    public static final String SMS_MSG_004 = "Message Can not be blank";
    public static final String SMS_CODE_005 = "SMS_005";
    public static final String SMS_MSG_005 = "SMS Sending failed";


    public static final String MAIL_CODE_001 = "MAIL_001";
    public static final String MAIL_MSG_001 = "Recipient Address can not be blank";
    public static final String MAIL_CODE_002 = "MAIL_002";
    public static final String MAIL_MSG_002 = "Subject Content is empty or Contains only white space";
    public static final String MAIL_CODE_003 = "MAIL_003";
    public static final String MAIL_MSG_003 = "Body Content is empty or Contains only white space";
    public static final String MAIL_CODE_004 = "MAIL_004";
    public static final String MAIL_MSG_004 = "Email Sending Failed";

    public static final String CUSTOMER_TEMPLATE = "customer-template";
    public static final String ORDER_TEMPLATE = "order-template";

    public static final String EMAIL_SERIALIZED_FAILED = "failed to serialize string body";
    public static final String EMAIL_SENDING_FAILED = "Email Sending Failed";
    public static final String EMAIL_SENDING_SUCCESSFULL = "Email Sent Successfully";
    public static final String ERROR_SENDING_EMAIL = "Error in Sending Email";


}
