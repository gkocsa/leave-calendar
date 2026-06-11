package com.example.leavecalendar.dto.response;

public record TeamMemberDto(
        Long id,
        String name,
        Integer rotationPosition,
        boolean active
) {
}