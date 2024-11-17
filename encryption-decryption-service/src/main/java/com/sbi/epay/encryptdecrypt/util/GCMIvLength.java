package com.sbi.epay.encryptdecrypt.util;

import lombok.Getter;

@Getter
public enum GCMIvLength {
    /**
     * Recommended IV length for AES/GCM (96 bits = 12 bytes).
     */
    STANDARD(12),

    /**
     * Minimum IV length as per GCM specification (not recommended for most cases).
     */
    MINIMUM(1),

    /**
     * Maximum IV length supported by GCM (recommended not to exceed this).
     */
    MAXIMUM(16);

    /**
     * -- GETTER --
     *  Retrieves the IV length in bytes.
     */
    private final int lengthInBytes;

    GCMIvLength(int lengthInBytes) {
        this.lengthInBytes = lengthInBytes;
    }

}

