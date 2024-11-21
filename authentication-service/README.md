# Authentication-service

## Description

This project demonstrates how to implement JWT (JSON Web Tokens) for secure token-based authentication in a Spring Boot
application using Spring Security. The project provides several methods token generation, token validation based on User
and role ensuring a secure and scalable authentication mechanism.
This utility JAR provides a simple and reusable way to generate and validate JSON Web Tokens (JWTs) in Java
applications. It is designed to work seamlessly with Spring Security but can also be used independently in any Java
project. The utility handles token creation, signing, and validation, making it easier to implement secure
authentication in your applications.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [API Reference](#api-reference)
- [Authors](#authors)
- [Project Status](#project-status)
- [License](#license)

## Features

- Generate JWT tokens with Username, password and tokenType
- Generate JWT tokens with Api key, Secret Key and tokenType
- Generate JWT token with sbiOrderReference and mid (merchant ID)
- Generate JWT tokens with Hash Value
- Generate JWT tokens with customizable claims
- Validate JWT tokens and check expiration
- Support for different signing algorithms (HS256, RS256, etc.)
- Easy integration with Spring Security
- Configurable token expiration and secret key

## Requirements

- Java 21 or higher
- Spring Boot
- Gradle (for dependency management)
- A relational database (e.g., H2, MySQL)

## Installation

1. Clone the repository:
   ```bash

   git clone https://gitlab.epay.sbi/epay/epay_java_utilities.git
   cd authentication-service

2. Build the utility JAR:
   ```bash
   gradle clean build

3. Add the JAR to your project dependencies. For Gradle projects, add the following dependency to your `build.gradle`
   file
   Build the utility JAR:
   ```
   implementation 'com.sbi.epay.authentication:authentication-service-0.0.1-SNAPSHOT'

## Usage

### Configuration

Before Using Utility, Configure the JWT properties in your `application.properties`

      jwt.secret.key=your_jwt_secret 
      whitelist = list of urls.

## UserInfo

Store user-info details in UserInfo class

    package com.sbi.epay.authentication.model;

    private String username;
    private String role;
    private String password;

     

## AuthRequest

Before generating JWT token please add values in AuthRequest class.

      package com.sbi.epay.authentication.model;
      private TokenType tokenType;
      private String customerId;
      private String password;
      private String role;
      private String apiKey;
      private String secretKey;
      private String mid;
      private String sbiOrderReference;
      private String hashValue;
      private long expirationTime;

### Enumeration

Use 'TokenType' enumeration to pass parameter as TokenType while generating JWT token. Here's how :

     import com.sbi.epay.authentication.util.enums.TokenType

     TokenType.ORDER
     //or
     TokenType.TRANSACTION
     //or
     TokenType.CUSTOMER
     //or
      TokenType.MERCHANT,


### Token Generation

Use the `AuthService` interface to generate a JWT token. Here’s how:

    package com.sbi.epay.authentication.service;
    String token = generateToken(AuthRequest tokenRequest) 

## Request Details for generating JWT token.
     // Example 1: Required fields to Generate JWT token for CUSTOMER/ORDER
            String customerId;   // optional
            String apiKey;
            String secretKey;
            TokenType tokenType;
            long expirationTime;

      // Example 2: Required fields to Generate JWT token for TRANSACTION

            String customerId;   // optional
            String sbiOrderReference;
            String mid;
            TokenType tokenType;
            long expirationTime;

            // OR 

             String customerId;   // optional
             String hashValue;
             TokenType tokenType;
             long expirationTime;

       // Example 3: Required fields to Generate JWT token for MERCHANT
            
             String customerId;   // optional
             String username;
             String password;
             TokenType tokenType;
             long expirationTime;

        
       

### Token Validation

To validate a token, use the following method:

    boolean isValid = JwtService.validateToken(token);

## API-Reference

JwtService Class

* `generateToken(String username)`
  Generates a JWT token for the given username.
* `validateToken(String token)`
  Validates the provided JWT token and checks its expiration.
* `extractUserName(String token)`
  Retrieves the username from the given JWT token.

## Authors

- Nirvay K bikram - nirvay.bikram.cedge@sbi.co.in -https://gitlab.epay.sbi/V0000004
- Shilpa Kothre - shilpa.kothre.cedge@sbi.co.in -https://gitlab.epay.sbi/V0000001

## License

This Project is own by SBI.

## Project status

Project Status is Completed
