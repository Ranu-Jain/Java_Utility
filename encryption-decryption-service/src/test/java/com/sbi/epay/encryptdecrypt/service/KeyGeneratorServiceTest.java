package com.sbi.epay.encryptdecrypt.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * Class Name: KeyGeneratorServiceTest
 * *
 * Description:Contains test cases related to KeyGeneratorService.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
class KeyGeneratorServiceTest {

    KeyGeneratorService keyGeneratorService=new KeyGeneratorService();
    @Test
    void generateKey() throws Exception{
        String secretKey1=keyGeneratorService.generateKey(256);
        String secretKey2=keyGeneratorService.generateKey(256);
        assertNotEquals(secretKey1,secretKey2);
    }
}