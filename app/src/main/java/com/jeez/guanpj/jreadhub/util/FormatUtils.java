package com.jeez.guanpj.jreadhub.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

public final class FormatUtils {

    private FormatUtils() {}

    private static final long MINUTE = 60 * 1000;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final long MONTH = 31 * DAY;
    private static final long YEAR = 12 * MONTH;

    public static String getRelativeTimeSpanString(@NonNull OffsetDateTime offsetDateTime) {
        long offset = 0;
        try {
            offset = Duration.between(offsetDateTime, OffsetDateTime.now()).toMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (offset > YEAR) {
            return (offset / YEAR) + " 年前";
        } else if (offset > MONTH) {
            return (offset / MONTH) + " 个月前";
        } else if (offset > WEEK) {
            return (offset / WEEK) + " 周前";
        } else if (offset > DAY) {
            return (offset / DAY) + " 天前";
        } else if (offset > HOUR) {
            return (offset / HOUR) + " 小时前";
        } else if (offset > MINUTE) {
            return (offset / MINUTE) + " 分钟前";
        } else {
            return "刚刚";
        }
    }

    public static OffsetDateTime string2ODT(String timeStr) {
        if (!TextUtils.isEmpty(timeStr)) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime localDateTime = LocalDateTime.parse(timeStr, df);
            return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
        }
        return null;
    }
}
