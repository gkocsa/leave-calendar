package com.example.leavecalendar.controller;

import com.example.leavecalendar.dto.request.CreateLeaveRequestDto;
import com.example.leavecalendar.dto.request.UpdateLeaveRequestDto;
import com.example.leavecalendar.dto.request.UpdateLeaveStatusDto;
import com.example.leavecalendar.dto.response.LeaveRequestDto;
import com.example.leavecalendar.enums.LeaveStatus;
import com.example.leavecalendar.service.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    public List<LeaveRequestDto> listLeaveRequests(
            @RequestParam(required = false) LeaveStatus status) {
        if (status != null) {
            return leaveRequestService.listByStatus(status);
        }

        return leaveRequestService.listLeaveRequests();
    }

    @PostMapping
    public LeaveRequestDto createLeaveRequest(
            @Valid @RequestBody CreateLeaveRequestDto dto) {
        return leaveRequestService.createLeaveRequest(dto);
    }

    @PutMapping("/{id}")
    public LeaveRequestDto updateLeaveRequest(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLeaveRequestDto dto) {
        return leaveRequestService.updateLeaveRequest(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
    }

    @PatchMapping("/{id}/status")
    public LeaveRequestDto updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLeaveStatusDto dto) {
        return leaveRequestService.updateStatus(id, dto);
    }
}