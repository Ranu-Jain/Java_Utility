package com.sbi.epay.notification.validator;

import com.sbi.epay.notification.exception.NotificationException;
import com.sbi.epay.notification.model.ErrorDto;
import com.sbi.epay.notification.model.SmsDTO;
import com.sbi.epay.notification.util.NotificationConstant;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: SMSValidator
 * *
 * Description:main purpose of this class is to take SMS DTO as parameter and check that each parameter weather they contain value or if they do not have value
 * *
 * Author: V1017903(bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class SmsValidator {

    static LoggerUtility logger = LoggerFactoryUtility.getLogger(SmsValidator.class);
    /**
     * This method will be used for validating the sms
     *
     * @param smsRequest for validate sms
     * @throws NotificationException if any exception occurs
     */
    public static void validateSMS(SmsDTO smsRequest) throws NotificationException {
        logger.info("ClassName - SMSValidator,MethodName - validateSMS, method-start");
        List<ErrorDto> errorList = new ArrayList<>();

        if (StringUtils.isEmpty(smsRequest.getMessage())) {
            errorList.add(new ErrorDto(NotificationConstant.MANDATORY_ERROR_CODE, MessageFormat.format(NotificationConstant.MANDATORY_ERROR_MESSAGE, "SMS Message")));
        }

        if (StringUtils.isEmpty(smsRequest.getMobileNumber())) {
            errorList.add(new ErrorDto(NotificationConstant.MANDATORY_ERROR_CODE, MessageFormat.format(NotificationConstant.MANDATORY_ERROR_MESSAGE, "Mobile Number")));
        }

        /**
         *  this method of Collection.isEmpty check if
         *  the error list have some error
         *  then it throw exception
         **/
        if (!CollectionUtils.isEmpty(errorList)) {
            logger.error("Error -> ",errorList);
            throw new NotificationException(errorList);
        }
        logger.info("ClassName - SMSValidator,MethodName - validateSMS, method-end");
    }
}
