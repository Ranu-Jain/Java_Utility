package com.sbiepay.loggerutility.utility;
import java.util.HashMap;
import java.util.Map;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;

import org.junit.jupiter.api.*;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class Name: LoggerFactory
 * *
 * Description: This class is for getting logger instances
 * *
 * Author: V1018400(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public class LoggerFactoryUtilityTest {

    /**
     * This setup before class
     * @throws Exception
     */
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        // Empty method
    }
    /**
     * This setup beforeEach class
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Empty method
    }


    /**
     *  Test for null key values
     */
    @Test
    public void testPutMDCWithNullKeyOrValue() {

       assertThrows(IllegalArgumentException.class, () -> LoggerFactoryUtility.putMDC(null, "value"));
       assertThrows(IllegalArgumentException.class, () -> LoggerFactoryUtility.putMDC("key", null));

    }

    /**
     *Test for put and remove mdc
     */
    @Test
    public void testPutAndRemoveMDC() {

		Map<String, String> mapcontect = new HashMap<>();
		mapcontect.put("testKey", "testValue");
        LoggerFactoryUtility.setMDCContext(mapcontect);
        assertEquals("testValue", MDC.get("testKey"));
        MDC.remove("testKey");
       assertNull(MDC.get("testKey"));
    }

    /**
     * Test for clear MDC
     */
    @Test
    public void testClearMDC() {

        LoggerFactoryUtility.clearMDCContext();
        assertNull(MDC.get("testKey"));
    }

    /**
     * Test for non-null values
     */
    @Test
    public void testPutMDCNotNull(){
        LoggerFactoryUtility.putMDC("testKey", "testValue");
        assertNotNull(MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for special charcter
     */
    @Test
    public void testPutMDCSpecialChar()
    {
        LoggerFactoryUtility.putMDC("testKey", "@#$");
        assertEquals("@#$",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for number
     */
    @Test
    public void testPutMDCNumber()
    {
        LoggerFactoryUtility.putMDC("testKey", "123456");
        assertEquals("123456",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     *
     */
    @Test
    public void testPutMDCCharandNumber()
    {
        LoggerFactoryUtility.putMDC("testKey", "@#abc");
        assertEquals("@#abc",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for charcter+alpha
     */
    @Test
    public void testPutMDCCharAndAlphs()
    {
        LoggerFactoryUtility.putMDC("testKey", "@#123456");
        assertEquals("@#123456",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for number+alpha
     */
    @Test
    public void testPutMDCNumberAndAlphs()
    {
        LoggerFactoryUtility.putMDC("testKey", "123456abc");
        assertEquals("123456abc",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for Starting space+alpha
     */
    @Test
    public void testPutMDCStartingSpaceAndAlphs()
    {
        LoggerFactoryUtility.putMDC("testKey", " abc");
        assertEquals(" abc",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for alphabet with ending space
     */
    @Test
    public void testPutMDCAlphspusEndindSpace()
    {
        LoggerFactoryUtility.putMDC("testKey", "abc ");
        assertEquals("abc ",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * Test for not equal for special case
     */
    @Test
    public void testPutMDCNotEqulspacial()
    {
        LoggerFactoryUtility.putMDC("testKey", "abc789");
        assertNotEquals("xyz123 ",MDC.get("testKey"));
        LoggerFactoryUtility.clearMDCContext();
    }

    /**
     * This is default method AfterEach
     * @throws Exception
     */
    @AfterEach
    public void tearDown() throws Exception {
        // Empty method
    }

    /**
     * This is default method AfterAll
     * @throws Exception
     */
    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        // Empty method
    }

}
