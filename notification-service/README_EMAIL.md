# Notification Utility



## Overview
The Notification utility that is used for sending email and sms.

## Email Client
This class is responsible for email creation and sending.
The `EmailClient` class is a utility that sends emails using Java's `JavaMailSender`
and templates provided by the `EmailTemplateService`. It supports HTML-formatted
emails with optional CC and BCC fields. It provides methods for constructing and
sending email messages and includes error handling via `NotificationException
 
#### Methods:
- `boolean sendEmail(EmailDTO emailDTO)`: Sends an email based on the `EmailDTO` object provided. Returns `true`
- if the email is sent successfully. Throws `NotificationException` on failure.
- `MimeMessage createMimeMessage(EmailDTO emailDTO)`: Creates a `MimeMessage`
- based on the `EmailDTO` object.
- `setMimeMessageHelper(EmailDTO emailDTO, MimeMessage message)`:
- Configures the email's content and recipients, including setting CC/BCC if provided.

### NotificationException
This class extends `RuntimeException` and is used to handle email notification errors.

#### Constructors:
- `NotificationException(String errorCode, String errorMessage)`:
- Creates a `NotificationException`
- with a specific error code and message.
- `NotificationException(List<ErrorDto> errorMessages)`:
- Creates a `NotificationException` with a list of error messages.

## Usage Example
EmailDTO emailDTO = new EmailDTO();
emailDTO.setFrom("from@example.com");
emailDTO.setRecipient("to@example.com");
emailDTO.setSubject("Test Subject");
emailDTO.setBody("Test Body");
emailDTO.setEmailType("testType");
EmailClient emailClient = new EmailClient(javaMailSender, emailTemplateService);
boolean emailSent = emailClient.sendEmail(emailDTO);
if (emailSent) {
System.out.println("Email sent successfully.");
} else {
System.out.println("Email sending failed.");
}

## Conclusion
The `EmailClient` provides a robust mechanism for
sending templated emails with HTML content, handling errors
gracefully. With its testability using JUnit and Mockito,
it can be easily extended to handle more complex email-sending scenarios.


