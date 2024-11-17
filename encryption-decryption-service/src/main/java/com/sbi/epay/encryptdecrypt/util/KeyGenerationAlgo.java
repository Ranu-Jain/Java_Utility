package com.sbi.epay.encryptdecrypt.util;

import lombok.Getter;

@Getter
public enum KeyGenerationAlgo {
    AES("AES");

    private final String algorithmName;
    KeyGenerationAlgo(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
