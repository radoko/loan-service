package com.okomski.loanservice.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public static boolean lessThanOrEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean greaterThanOrEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0;
    }

    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) == 0;
    }

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(ONE_HUNDRED);
    }
}
