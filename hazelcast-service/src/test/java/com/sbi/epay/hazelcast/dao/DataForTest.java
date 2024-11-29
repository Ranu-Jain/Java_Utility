package com.sbi.epay.hazelcast.dao;


import com.sbi.epay.hazelcast.model.EPayCachebleData;

public class DataForTest implements EPayCachebleData {
    private String empId;
    private String name;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
