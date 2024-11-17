package com.sbi.epay.encryptdecrypt.util;

import lombok.Getter;

@Getter
public enum SecretKeyLength {

    AES_128(128),
    AES_192(192),
    AES_256(256);

    private final int lengthInBits;

    SecretKeyLength(int lengthInBits) {
        this.lengthInBits = lengthInBits;
    }
}
