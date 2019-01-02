package com.okomski.loanservice.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class SystemClockImpl implements SystemClock {

    private LocalDateTime fixedDate = LocalDateTime.now();

    @Override
    public LocalDateTime localDateTimeNow() {
        return fixedDate;
    }

    @Override
    public LocalDate localDateNow() {
        return fixedDate.toLocalDate();
    }

    @Override
    public LocalTime localTime() {
        return fixedDate.toLocalTime();
    }
}
