package com.example.leavecalendar.validation;

import com.example.leavecalendar.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateRangeValidatorTest {

    @Test
    void shouldAcceptValidDateRange() {
        assertDoesNotThrow(() ->
                DateRangeValidator.validate(
                        LocalDate.of(2026, 7, 1),
                        LocalDate.of(2026, 7, 5)
                )
        );
    }

    @Test
    void shouldRejectEndDateBeforeStartDate() {
        assertThrows(ValidationException.class, () ->
                DateRangeValidator.validate(
                        LocalDate.of(2026, 7, 5),
                        LocalDate.of(2026, 7, 1)
                )
        );
    }

    @Test
    void shouldRejectNullDates() {
        assertThrows(ValidationException.class, () ->
                DateRangeValidator.validate(null, LocalDate.of(2026, 7, 1))
        );

        assertThrows(ValidationException.class, () ->
                DateRangeValidator.validate(LocalDate.of(2026, 7, 1), null)
        );
    }
}