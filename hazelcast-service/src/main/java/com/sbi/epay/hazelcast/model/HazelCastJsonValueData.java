package com.sbi.epay.hazelcast.model;

import com.hazelcast.core.HazelcastJsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HazelCastJsonValueData implements  EPayCachebleData{
    HazelcastJsonValue hazelcastJsonValue;
}
