package com.example.chatfirebase.common.utils;

public class UtilsCommon {

    /*Codificar un correp electronico */

    public static String getEmailEncoded(String email){
        String preKey  = email.replace("_","__");
        return  preKey.replace(".","_");
    }

}
