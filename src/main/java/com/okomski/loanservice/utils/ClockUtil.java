package com.okomski.loanservice.utils;

import java.time.LocalTime;

public class ClockUtil {
    public static final boolean isBeforeOrEqual(LocalTime a, LocalTime b) {
        return a.isBefore(b) || a.equals(b);
    }

    public static final boolean isAfterOrEqual(LocalTime a, LocalTime b) {
        return a.isAfter(b) || a.equals(b);
    }
}
