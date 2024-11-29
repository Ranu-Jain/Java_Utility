package com.sbi.epay.hazelcast.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JsonValueInput implements EPayCachebleData {

    /**
     *
     */
    private String mapName;
    private String keyName;
    private String jsonString;
}
