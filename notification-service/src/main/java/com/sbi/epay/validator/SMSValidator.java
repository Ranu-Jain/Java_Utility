package com.sbi.epay.validator;

import com.sbi.epay.exception.NotificationException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.model.ErrorDto;
import com.sbi.epay.model.SmsDTO;
import com.sbi.epay.service.EmailService;
import com.sbi.epay.util.NotificationConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

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
public class SMSValidator {

    static LoggerUtility logger = LoggerFactoryUtility.getLogger(SMSValidator.class);
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
            errorList.add(new ErrorDto(NotificationConstant.SMS_CODE_001, NotificationConstant.SMS_MSG_001));
        }

        if (StringUtils.isEmpty(smsRequest.getMobileNumber())) {
            errorList.add(new ErrorDto(NotificationConstant.MAIL_CODE_002, NotificationConstant.SMS_MSG_002));
        }

        /**
         *  this method of Collection.isEmpty check if
         *  the errolist have some error
         *  then it throw exception
         **/
        if (!CollectionUtils.isEmpty(errorList)) {
            logger.info("Error -> ",errorList);
            throw new NotificationException(errorList);
        }
        logger.info("ClassName - SMSValidator,MethodName - validateSMS, method-end");
    }
}
