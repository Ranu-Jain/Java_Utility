package com.sbi.epay.hazelcast.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * this class will provide inputs for caching
 * <p>
 * Class Name: CacheableEntity
 * *
 * Description:This class is used for defining constant
 * *
 * Author: V1018212(Hrishikesh Pandirakar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Data
@Builder
public class CacheableEntity implements Serializable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -5378486477607481094L;
    private String mapName;
    private String key;
    private EPayCachebleData cacheableEntityData;
    private HazelCastJsonValueData hazelCastJsonValueData;
    private Collection<Object> responseData;

}
