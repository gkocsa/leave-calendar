package com.example.leavecalendar.controller;

import com.example.leavecalendar.dto.response.OnCallWeekDto;
import com.example.leavecalendar.service.OnCallService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/on-call")
public class OnCallController {

    private final OnCallService onCallService;

    public OnCallController(OnCallService onCallService) {
        this.onCallService = onCallService;
    }

    @GetMapping
    public List<OnCallWeekDto> getSchedule(
            @RequestParam LocalDate from,
            @RequestParam(defaultValue = "8") int weeks
    ) {
        return onCallService.getSchedule(from, weeks);
    }
}