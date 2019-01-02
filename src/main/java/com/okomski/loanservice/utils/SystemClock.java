package com.okomski.loanservice.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface SystemClock {
    LocalDateTime localDateTimeNow();
    LocalDate localDateNow();
    LocalTime localTime();
}
