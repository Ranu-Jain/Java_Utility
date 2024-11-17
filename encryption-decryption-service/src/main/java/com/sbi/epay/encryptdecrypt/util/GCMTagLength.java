package com.sbi.epay.encryptdecrypt.util;

import lombok.Getter;

@Getter
public enum GCMTagLength {

    STANDARD(128);

    private final int lengthInBits;

    GCMTagLength(int lengthInBits) {
        this.lengthInBits = lengthInBits;
    }

}

