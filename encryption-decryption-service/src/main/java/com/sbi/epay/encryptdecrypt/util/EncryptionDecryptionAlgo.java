package com.sbi.epay.encryptdecrypt.util;

import lombok.Getter;

@Getter
public enum EncryptionDecryptionAlgo {

    AES_GCM_NO_PADDING("AES/GCM/NoPadding");

    private final String name;

    EncryptionDecryptionAlgo(String name) {
        this.name = name;
    }

}

