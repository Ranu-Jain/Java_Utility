 # Notification Utility

# SmsClient Documentation

## Overview

### SmsClient
This class is responsible for sending SMS messages by constructing and executing an HTTP POST request.

The `SmsClient` class is an extension of the `ApiClient`
and is used to send SMS messages through an external gateway. 
It uses `RestTemplate` to send HTTP requests to the configured SMS gateway URL.

- **Fields**:

    - `ObjectMapper objectMapper`: Used for converting `SmsDTO` objects to JSON strings.

    - `Logger logger`: Logs information for debugging purposes.

#### Constructor:

- `SmsClient(String baseUrl)`: The constructor accepts the SMS gateway URL, which is injected using Spring's `@Value` annotation.

#### Methods:

- `boolean sendSMS(SmsDTO smsDTO) throws NotificationException, JsonProcessingException`:

    - Converts the provided `SmsDTO` into a JSON string.
    - Prepares the HTTP request headers and body.
    - Sends an HTTP POST request to the configured SMS gateway URL.
    - Returns `true` if the request is successful (2xx HTTP status code).
    - Throws `NotificationException` if the response indicates failure.

### NotificationException
This custom exception is thrown if there is an issue with sending the SMS.

## How it Works
1. The `sendSMS(SmsDTO smsDTO)` method converts the `smsDTO` object to a JSON string using `ObjectMapper`.
2. It prepares an HTTP entity (`HttpEntity<String>`) by calling `prepareHttpEntity`, which takes in the JSON payload and headers from `prepareHttpHeaders()`.
3. The `RestTemplate` executes the HTTP POST request to the configured SMS gateway URL.
4. If the response status code is successful (2xx), the method returns `true`, indicating that the SMS was sent successfully.
5. If the response is not successful, the method throws a `NotificationException` with an appropriate error code and message.

## Example Usage

SmsDTO smsDTO = new SmsDTO();
smsDTO.setPhoneNumber("1234567890");
smsDTO.setMessage("Hello, this is a test message!");
SmsClient smsClient = new SmsClient("https://example.com/sms-gateway");
boolean smsSent = smsClient.sendSMS(smsDTO);
if (smsSent) {
    System.out.println("SMS sent successfully.");
} else {

    System.out.println("SMS sending failed.");

}

## Exception Handling

The `sendSMS` method throws `NotificationException` when the HTTP response is unsuccessful. The exception contains an error code and a message to help identify the issue.

### Example: NotificationException

try {

    smsClient.sendSMS(smsDTO);

} catch (NotificationException e) {

    System.err.println("Error sending SMS: " + e.getMessage());

}

## Conclusion

The `SmsClient` provides a convenient way to send SMS messages using an external gateway,
with built-in error handling through `NotificationException`.
This class can be extended or modified for additional SMS gateway configurations or advanced functionality.
 