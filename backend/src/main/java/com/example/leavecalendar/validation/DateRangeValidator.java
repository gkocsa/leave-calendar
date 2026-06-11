package com.example.leavecalendar.validation;

import com.example.leavecalendar.exception.ValidationException;

import java.time.LocalDate;

public class DateRangeValidator {

    private DateRangeValidator() {
    }

    public static void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start date and end date are required");
        }

        if (endDate.isBefore(startDate)) {
            throw new ValidationException("End date cannot be before start date");
        }
    }
}