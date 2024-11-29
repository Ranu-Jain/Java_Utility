package com.sbi.epay.hazelcast.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InputForSQLPredicate implements EPayCachebleData{
    /**
     *
     */

    private String mapName;
    private String sqlConditions;
}
