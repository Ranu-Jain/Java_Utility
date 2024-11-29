

package com.sbi.epay.hazelcast.serviceimpl;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.sbi.epay.hazelcast.constants.HazelcastConstants;
import com.sbi.epay.hazelcast.dao.DataForTest;
import com.sbi.epay.hazelcast.exception.HazelcastException;
import com.sbi.epay.hazelcast.model.CacheableEntity;
import com.sbi.epay.hazelcast.model.EPayCachebleData;
import com.sbi.epay.hazelcast.model.HazelCastJsonValueData;
import com.sbi.epay.hazelcast.model.PredicateInput;
import com.sbi.epay.hazelcast.service.HazelcastService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * this class contains test cases
 * <p>
 * Class Name: HazelcastServiceTest
 * *
 * Description:This class contains test cases.
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@SpringBootTest
class HazelcastServiceTest {

    private final String key = "1";
    private final String mapName = "myMap";
    private final String empId="V1018212";
    private final String empName="Hrishi";
    private String responseStr="";

    HazelcastInstance hazelcastInstance;

    String response = null;
    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setClusterName("My-Cluster");
        return Hazelcast.newHazelcastInstance(config);
    }

    HazelcastService hazelcastService=new HazelcastService();


    @BeforeEach
    public void setUp(){
        try {
            DataForTest dataForTest = new DataForTest();
            dataForTest.setEmpId(empId);
            dataForTest.setName(empName);

            CacheableEntity cacheableEntity = CacheableEntity.builder().key(key).mapName(mapName).cacheableEntityData((EPayCachebleData) dataForTest).build();
            response = hazelcastService.addDataToCache(cacheableEntity, hazelcastInstance());

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("aaa");
            jsonArray.put(1);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("details", (Object) jsonArray);
            responseStr = hazelcastService.saveDataByJsonObject(mapName, "2", jsonObject.toString(), hazelcastInstance());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void addDataToCache() {
        assertNotNull(response);
        assertEquals(HazelcastConstants.DATA_ADDED, response);
    }

    @Test
    void getDataByKeyTest() {
        try{
        CacheableEntity cacheableEntity1 = hazelcastService.getDataByKey(mapName, key,hazelcastInstance());
        DataForTest cacheableEntityData = (DataForTest) cacheableEntity1.getCacheableEntityData();
        assertNotNull(cacheableEntityData);
        assertEquals(empId, cacheableEntityData.getEmpId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getDataBySqlTest(){
        try {
            CacheableEntity cacheableEntity1 = hazelcastService.getDataBySql(mapName, "__key like 1", hazelcastInstance());

            assertNotNull(cacheableEntity1);
            DataForTest dataForTest = (DataForTest) cacheableEntity1.getResponseData().toArray()[0];
            assertNotNull(dataForTest);

            assertEquals(empId, dataForTest.getEmpId());
            assertEquals(empName, dataForTest.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDataByPredicateTest()  {
        try {
            Predicate<String, Object> keyPredicate = Predicates.equal("empId", empId);
            PredicateInput predicateInput = new PredicateInput(mapName, keyPredicate, key);
            CacheableEntity cacheableEntity1 = hazelcastService.getDataByPredicate(predicateInput, hazelcastInstance());
            assertNotNull(cacheableEntity1);
            DataForTest dataForTest = (DataForTest) cacheableEntity1.getCacheableEntityData();
            assertNotNull(dataForTest);
            assertEquals(empId, dataForTest.getEmpId());
            assertEquals(empName, dataForTest.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveDataByJsonObjectTest(){
        assertNotNull(responseStr);
        assertEquals(HazelcastConstants.DATA_ADDED, responseStr);
    }

    @Test
    void getJsonData() {
        try {
            CacheableEntity cacheableEntity1 = hazelcastService.getJSONData(mapName, "2", hazelcastInstance());
            assertNotNull(cacheableEntity1);
            HazelCastJsonValueData hazelCastJsonValueData = cacheableEntity1.getHazelCastJsonValueData();
            assertNotNull(hazelCastJsonValueData);
            HazelcastJsonValue hazelCastJsonValue = hazelCastJsonValueData.getHazelcastJsonValue();
            assertNotNull(hazelCastJsonValue);
            System.out.println("Response get JSON data :- " + hazelCastJsonValue.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void updateTest(){
        try {
            DataForTest dataForTestNew = new DataForTest();
            dataForTestNew.setEmpId("V1018212NEW");
            dataForTestNew.setName("HrishiNew");
            CacheableEntity cacheableEntityNew = CacheableEntity.builder().mapName(mapName).key(key).cacheableEntityData((EPayCachebleData) dataForTestNew).build();
            String updateResponse = hazelcastService.updateData(cacheableEntityNew, hazelcastInstance());
            assertNotNull(updateResponse);
            assertEquals(HazelcastConstants.DATA_UPDATE, updateResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateJsonData(){
        try {
            JSONArray jsonArrayNew = new JSONArray();
            jsonArrayNew.put("bbb");
            jsonArrayNew.put(2);

            JSONObject jsonObjectNew = new JSONObject();
            jsonObjectNew.put("detailsNew", (Object) jsonArrayNew);
            String updateResponse = hazelcastService.updateJsonData(mapName, "2", jsonObjectNew.toString(), hazelcastInstance());
            assertNotNull(updateResponse);
            assertEquals(HazelcastConstants.DATA_UPDATE, updateResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeDataTest(){
        try {
            String responseData = hazelcastService.removeData(mapName, key, hazelcastInstance());
            assertNotNull(response);
            assertEquals(HazelcastConstants.DATA_REMOVED, responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

