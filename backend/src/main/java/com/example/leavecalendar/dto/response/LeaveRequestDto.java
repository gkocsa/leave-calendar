package com.example.leavecalendar.dto.response;

import com.example.leavecalendar.enums.LeaveStatus;

import java.time.LocalDate;

public record LeaveRequestDto(
        Long id,
        Long teamMemberId,
        String teamMemberName,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        LeaveStatus status
) {
}