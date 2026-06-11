package com.example.leavecalendar.dto.request;

import com.example.leavecalendar.enums.LeaveStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateLeaveStatusDto(
        @NotNull LeaveStatus status
) {
}