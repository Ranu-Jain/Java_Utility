package com.sbi.epay.validator;

import com.sbi.epay.exception.NotificationException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.model.EmailDTO;
import com.sbi.epay.model.ErrorDto;
import com.sbi.epay.service.EmailService;
import com.sbi.epay.util.NotificationConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: EMailValidator
 * *
 * Description:Email Validator for Validating email takes Email object
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class EMailValidator {
    static LoggerUtility logger = LoggerFactoryUtility.getLogger(EMailValidator.class);

    /**
     * This method will be used for validating the email
     *
     * @param emailRequest for validating email
     * @throws NotificationException if nay exception occurs
     */
    public static void validateEMAIL(EmailDTO emailRequest) throws NotificationException {
        logger.info("ClassName - EMailValidator,MethodName -validateEMAIL, Method-start ");
        List<ErrorDto> errorList = new ArrayList<>();

        if (StringUtils.isEmpty(emailRequest.getRecipient())) {
            errorList.add(new ErrorDto(NotificationConstant.MAIL_CODE_001, NotificationConstant.MAIL_MSG_001));
        }
        if (StringUtils.isBlank(emailRequest.getSubject())) {
            errorList.add(new ErrorDto(NotificationConstant.MAIL_CODE_002, NotificationConstant.MAIL_MSG_002));
        }

        if (StringUtils.isBlank(emailRequest.getBody())) {
            errorList.add(new ErrorDto(NotificationConstant.MAIL_CODE_003, NotificationConstant.MAIL_CODE_003));
        }

        if (!CollectionUtils.isEmpty(errorList)) {
            throw new NotificationException(errorList);
        }
        logger.info("ClassName - EMailValidator,MethodName -validateEMAIL, Method-end ");
    }
}

