package com.cg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Date formatDate(String str){
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
