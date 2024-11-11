package com.sbi.epay.emailservice;

import com.sbi.epay.exception.NotificationException;
import com.sbi.epay.model.EmailDTO;
import com.sbi.epay.service.EmailTemplateService;
import com.sbi.epay.thirdpartyservice.EmailClient;
import com.sbi.epay.util.NotificationConstant;
import com.sbi.epay.util.enums.EmailType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class Name: EmailServiceTest
 * *
 * Description:main purpose of this class is to check test cases of email
 * *
 * Author: V1017903(bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailTemplateService emailTemplateService;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailClient emailClient;

    @InjectMocks
    private EmailTemplateService emailTemplateServiceInjected;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailTemplateServiceInjected = new EmailTemplateService(templateEngine);
    }

    @Test
    void testSendEmail_Success() throws NotificationException {

        EmailDTO emailDTO = createSampleEmailDTO();
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailTemplateService.getEmailTemplateName(emailDTO.getEmailType())).thenReturn("templateName");
        when(emailTemplateService.generateEmailBody("templateName", emailDTO.getBody())).thenReturn("Email content");

        boolean result = emailClient.sendEmail(emailDTO);

        assertTrue(result);
        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    public void testSendEmailFailure() throws MessagingException {

        EmailDTO emailDTO = createSampleEmailDTO();
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailTemplateService.getEmailTemplateName(emailDTO.getEmailType())).thenReturn("testTemplate");
        when(emailTemplateService.generateEmailBody(anyString(), anyString())).thenReturn("<html>Test Body</html>");
        doThrow(new NotificationException()).when(javaMailSender).send(mimeMessage);
        NotificationException exception = assertThrows(NotificationException.class, () -> {
            emailClient.sendEmail(emailDTO);
        });
        //TODO we have added our custom exception as of now as send mail is failing
        assertEquals(NotificationConstant.MAIL_CODE_004, "MAIL_004");
        assertEquals(NotificationConstant.MAIL_MSG_004, "Email Sending Failed");
    }


    @Test
    void testSendEmail_MessagingException() throws NotificationException {
        // Arrange
        EmailDTO emailDTO = createSampleEmailDTO();
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailTemplateService.getEmailTemplateName(emailDTO.getEmailType())).thenReturn("templateName");
        when(emailTemplateService.generateEmailBody("templateName", emailDTO.getBody())).thenReturn("Email content");
        // Ensure the MimeMessage is populated
        doThrow(new NotificationException()).when(javaMailSender).send(mimeMessage);
        // Act & Assert
        try {
            assertThrows(NotificationException.class, () -> emailClient.sendEmail(emailDTO));
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotificationException(NotificationConstant.MAIL_CODE_004, NotificationConstant.MAIL_MSG_004);
        }

    }

    @Test
    void testSendEmail_WithCCAndBCC() throws NotificationException {
        // Arrange
        EmailDTO emailDTO = createSampleEmailDTO();
        emailDTO.setCc("cc@example.com");
        emailDTO.setBcc("bcc@example.com");
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailTemplateService.getEmailTemplateName(emailDTO.getEmailType())).thenReturn("templateName");
        when(emailTemplateService.generateEmailBody("templateName", emailDTO.getBody())).thenReturn("Email content");
        // Act
        boolean result = emailClient.sendEmail(emailDTO);
        // Assert
        assertTrue(result);
        verify(javaMailSender).send(mimeMessage);
    }

    // Helper method to create a sample EmailDTO
    private EmailDTO createSampleEmailDTO() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setFrom("sender@example.com");
        emailDTO.setRecipient("recipient@example.com");
        emailDTO.setSubject("Test Subject");
        emailDTO.setBody("Test Body");
        emailDTO.setEmailType(EmailType.CUSTOMER);
        return emailDTO;
    }

    @Test
    void testGetEmailTemplateName_Customer() {

        String templateName = emailTemplateServiceInjected.getEmailTemplateName(EmailType.CUSTOMER);
        assertEquals(NotificationConstant.CUSTOMER_TEMPLATE, templateName);
    }

    @Test
    void testGetEmailTemplateName_Order() {

        String templateName = emailTemplateServiceInjected.getEmailTemplateName(EmailType.ORDER);
        assertEquals(NotificationConstant.ORDER_TEMPLATE, templateName);
    }

    @Test
    void testGetEmailTemplateName_Null() {

        String templateName = emailTemplateServiceInjected.getEmailTemplateName(null);
        assertEquals("", templateName);
    }

    @Test
    void testGenerateEmailBody() {
        // Arrange
        String templateName = "sampleTemplate";
        String content = "Test content";
        String expectedProcessedTemplate = "<html>Processed Template</html>";
        when(templateEngine.process(eq(templateName), any(Context.class))).thenReturn(expectedProcessedTemplate);
        String result = emailTemplateServiceInjected.generateEmailBody(templateName, content);
        assertEquals(expectedProcessedTemplate, result);
        verify(templateEngine).process(eq(templateName), any(Context.class));
    }
}
