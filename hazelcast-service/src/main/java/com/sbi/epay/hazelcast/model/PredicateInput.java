package com.sbi.epay.hazelcast.model;

import com.hazelcast.query.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is created for input
 * <p>
 * Class Name: PredicateInput
 * *
 * Description:This class is used for adding input to getByPredicate() method
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class PredicateInput implements EPayCachebleData {
    /**
     *
     */
    private String mapName;
    private Predicate<String,Object> predicate;
    private String key;
}
