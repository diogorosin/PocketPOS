package br.com.pocketpos.data.util;

import android.util.Log;

import java.security.MessageDigest;

public class StringUtils {

    public static String digestString(String password){

        String digestedString = "";

        try {

            MessageDigest alg = MessageDigest.getInstance("SHA-256");

            byte messageDigest[] = alg.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest)

                hexString.append(String.format("%02X", 0xFF & b));

            digestedString = hexString.toString();

        }catch(Exception e){

            Log.e(StringUtils.class.getSimpleName(), e.getMessage());

        }

        return digestedString;

    }

}