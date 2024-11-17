package com.sbi.epay.encryptdecrypt.util;

import lombok.Getter;

@Getter
public enum HashAlgorithm {
    SHA_512("SHA-512"), SHA_256("SHA-256");

    private final String algorithmName;

    HashAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
