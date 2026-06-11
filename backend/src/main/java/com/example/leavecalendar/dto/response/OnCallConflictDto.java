package com.example.leavecalendar.dto.response;

import java.time.LocalDate;

public record OnCallConflictDto(
        Long leaveRequestId,
        LocalDate leaveStart,
        LocalDate leaveEnd,
        String reason
) {
}