package com.sbi.epay.notification.service;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.notification.util.NotificationConstant;
import com.sbi.epay.notification.util.enums.EmailType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Class Name: EmailTemplateService
 * *
 * Description:EmailTemplateService is used for generatingEmailTemplateName by taking type and second it is generate email body using template name and content
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Service
public class EmailTemplateService {
    private final TemplateEngine templateEngine;
    LoggerUtility logger = LoggerFactoryUtility.getLogger(EmailService.class);

    /**
     * This constructor used for taking templateEngine
     *
     * @param templateEngine
     */
    public EmailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * This method will be used for decide email Template
     *
     * @param type are required for decide template name
     * @return template name
     */
    public String getEmailTemplateName(EmailType type) {
        logger.info("ClassName - EmailTemplateService,MethodName - getEmailTemplateName, Method-start");
        return switch (type) {
            case CUSTOMER -> NotificationConstant.CUSTOMER_TEMPLATE;
            case ORDER -> NotificationConstant.ORDER_TEMPLATE;
            default -> StringUtils.EMPTY;
        };

    }

    /**
     * This Method to generate email body using template name and content
     *
     * @param templateName contain name of template
     * @param content      contain data we are set into template
     * @return emailbody
     */
    public String generateEmailBody(String templateName, String content) {
        logger.info("ClassName - EmailTemplateService,MethodName - generateEmailBody, method-start");
        Context context = new Context();
        context.setVariable("data", content);
        logger.info("ClassName - EmailTemplateService,MethodName - generateEmailBody, method-end");
        return templateEngine.process(templateName, context);
    }
}
