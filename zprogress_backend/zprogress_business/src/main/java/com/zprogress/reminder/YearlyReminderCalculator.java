package com.zprogress.reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyReminderCalculator implements ReminderCalculator {
    @Override
    public LocalDateTime calculateNextReminderDateTime(LocalDate startDate) {
        return startDate.atTime(HOUR, MINUTE).plusYears(1);
    }
}
