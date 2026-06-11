package com.example.leavecalendar.dto.response;

import java.time.LocalDate;
import java.util.List;

public record OnCallWeekDto(
        LocalDate weekStart,
        LocalDate weekEnd,
        Long teamMemberId,
        String teamMemberName,
        boolean hasConflict,
        List<OnCallConflictDto> conflicts
) {
}