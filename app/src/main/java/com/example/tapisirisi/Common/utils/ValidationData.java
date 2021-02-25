package com.example.tapisirisi.Common.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class ValidationData {


    public static boolean fieldValidation(String field) {
        return !TextUtils.isEmpty(field) && field.length() >= 6;
    }

}
