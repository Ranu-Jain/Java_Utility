package com.sbi.epay.thirdpartyservice;

import com.sbi.epay.exception.NotificationException;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.model.EmailDTO;
import com.sbi.epay.service.EmailTemplateService;
import com.sbi.epay.service.SmsService;
import com.sbi.epay.util.NotificationConstant;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Class Name: EmailClient
 * *
 * Description:EmailClient this class mainly used for sending mail and createMessage and MimeMessageHelper
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class EmailClient {
    LoggerUtility logger = LoggerFactoryUtility.getLogger(EmailClient.class);
    private final JavaMailSender javaMailSender;
    private final EmailTemplateService emailTemplateService;
    /**
     * This method will be used for calling createMimeMessage and send method
     *
     * @param emailDTO object for calling createMimeMessage method
     * @return true if email send successfully otherwise return false
     * @throws NotificationException if any exception occur
     */
    public boolean sendEmail(EmailDTO emailDTO) throws NotificationException {
        logger.info("ClassName - EmailClient,MethodName - sendEmail,Method-start");
        try {
            MimeMessage message = createMimeMessage(emailDTO);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            logger.info("ClassName - EmailClient,MethodName - sendEmail, inside catch"+e);
            throw new NotificationException(NotificationConstant.MAIL_CODE_004, NotificationConstant.MAIL_MSG_004);
        }
    }

    /**
     * This method will be used for createMimeMessage
     *
     * @param emailDTO object for mimeMessage
     * @return notification message
     * @throws MessagingException if any exception occur
     */
    private MimeMessage createMimeMessage(EmailDTO emailDTO) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-start");
        MimeMessage message = javaMailSender.createMimeMessage();
        setMimeMessageHelper(emailDTO, message);
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-end");
        return message;
    }

    /**
     * This method will be used for set email details into MimeMessageHelper
     *
     * @param emailDTO for set parameter into MimeMessageHelper
     * @param message
     * @throws MessagingException
     */
    private void setMimeMessageHelper(EmailDTO emailDTO, MimeMessage message) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - setMimeMessageHelper,Method-start");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailDTO.getFrom());
        helper.setTo(emailDTO.getRecipient());
        helper.setSubject(emailDTO.getSubject());
        String emailContent = emailTemplateService.generateEmailBody(emailTemplateService.getEmailTemplateName(emailDTO.getEmailType()), emailDTO.getBody());
        helper.setText(emailContent, true); // true indicates HTML content
        if (StringUtils.isNotEmpty(emailDTO.getCc())) {
            helper.setCc(emailDTO.getCc());
        }
        if (StringUtils.isNotEmpty(emailDTO.getBcc())) {
            helper.setBcc(emailDTO.getBcc());
        }
        logger.info("ClassName - EmailClient,MethodName - setMimeMessageHelper,Method-end");
    }

}
