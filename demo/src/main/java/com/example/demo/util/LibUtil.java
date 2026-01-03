package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class LibUtil {
    public static String FORMAT_YYMMDD = "yyMMdd";
    public static String FORMAT_YYMMDD_TIME = "yyMMdd HH:mm:ss";
    public static String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static String FORMAT_DDMMYYYY = "ddMMyyyy";

    public static String formatyymmdd() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYMMDD);
        return sdf.format(new Date());
    }

    public static String formatyymmddTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYMMDD_TIME);
        return sdf.format(new Date());
    }

    public static String formatyyyymmdd() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDD);
        return sdf.format(new Date());
    }

    public static String formatDDMMYYYY() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DDMMYYYY);
        return sdf.format(new Date());
    }

    public static String getUserId() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        // 将时分秒组合成一个整数（总秒数）
        int totalSeconds = hour * 3600 + minute * 60 + second;
        final String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUV";

        // 计算数字在基数为 32 下的 4 位表示
        StringBuilder base32 = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int remainder = totalSeconds % 32;
            base32.insert(0, digits.charAt(remainder));
            totalSeconds /= 32;
        }

        // 如果数字不足 4 位，前面补零
        while (base32.length() < 4) {
            base32.insert(0, '0');
        }
        String dateTime = formatyymmdd();
        return "CD" + dateTime + base32;
    }

    public static String getBookId() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        // 将时分秒组合成一个整数（总秒数）
        int totalSeconds = hour * 3600 + minute * 60 + second;
        final String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUV";

        // 计算数字在基数为 32 下的 4 位表示
        StringBuilder base32 = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int remainder = totalSeconds % 32;
            base32.insert(0, digits.charAt(remainder));
            totalSeconds /= 32;
        }

        // 如果数字不足 4 位，前面补零
        while (base32.length() < 4) {
            base32.insert(0, '0');
        }
        String dateTime = formatyymmdd();
        return "BK" + dateTime + base32;
    }

    public static String getBookDetailsId() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        // 将时分秒组合成一个整数（总秒数）
        int totalSeconds = hour * 3600 + minute * 60 + second;
        String dateTime = formatyymmdd();
        return "DT" + dateTime + totalSeconds;
    }
}
