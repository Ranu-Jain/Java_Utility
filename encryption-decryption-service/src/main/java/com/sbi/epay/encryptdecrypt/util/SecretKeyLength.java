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

    public static SecretKeyLength fromLengthInBits(int lengthInBits) {
        return switch (lengthInBits) {
            case 128 -> SecretKeyLength.AES_128;
            case 192 -> SecretKeyLength.AES_192;
            case 256 -> SecretKeyLength.AES_256;
            default -> throw new IllegalArgumentException("No SecretKeyLength found for lengthInBits: " + lengthInBits);
        };
    }
}
