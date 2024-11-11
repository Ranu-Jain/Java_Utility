package com.sbi.epay.encryptdecrypt.util;

import com.sbi.epay.encryptdecrypt.dto.ErrorInfoDto;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.service.KeyGeneratorService;

public class TestMain {
    public static void main(String[] args) {
        KeyGeneratorService keyGeneratorService=new KeyGeneratorService();
        try {
            String str = keyGeneratorService.generateKey(123);
        }
        catch (EncryptionDecryptionException e){
            ErrorInfoDto errorInfoDtoe= e.getErrorInfo();
            System.out.println(errorInfoDtoe.getErrorCode()+" "+errorInfoDtoe.getErrorMessage());
        }
    }
}
