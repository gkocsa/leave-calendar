package com.example.leavecalendar.service;

import com.example.leavecalendar.dto.request.CreateLeaveRequestDto;
import com.example.leavecalendar.dto.request.UpdateLeaveRequestDto;
import com.example.leavecalendar.dto.request.UpdateLeaveStatusDto;
import com.example.leavecalendar.dto.response.LeaveRequestDto;
import com.example.leavecalendar.entity.LeaveRequest;
import com.example.leavecalendar.entity.TeamMember;
import com.example.leavecalendar.enums.LeaveStatus;
import com.example.leavecalendar.exception.NotFoundException;
import com.example.leavecalendar.exception.ValidationException;
import com.example.leavecalendar.repository.LeaveRequestRepository;
import com.example.leavecalendar.validation.DateRangeValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final TeamMemberService teamMemberService;

    public LeaveRequestService(
            LeaveRequestRepository leaveRequestRepository,
            TeamMemberService teamMemberService) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.teamMemberService = teamMemberService;
    }

    public List<LeaveRequestDto> listLeaveRequests() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<LeaveRequestDto> listByStatus(LeaveStatus status) {
        return leaveRequestRepository.listByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public LeaveRequestDto createLeaveRequest(CreateLeaveRequestDto dto) {
        DateRangeValidator.validate(dto.startDate(), dto.endDate());

        TeamMember teamMember = teamMemberService.getTeamMemberById(dto.teamMemberId());

        boolean hasOverlap = !leaveRequestRepository.findOverlaps(
                dto.teamMemberId(),
                dto.startDate(),
                dto.endDate()).isEmpty();

        if (hasOverlap) {
            throw new ValidationException("Leave request overlaps with an existing leave request");
        }

        LeaveRequest leaveRequest = new LeaveRequest(
                teamMember,
                dto.startDate(),
                dto.endDate(),
                dto.reason());

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        return toDto(saved);
    }

    public LeaveRequestDto updateLeaveRequest(Long id, UpdateLeaveRequestDto dto) {
        DateRangeValidator.validate(dto.startDate(), dto.endDate());

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Leave request not found with id: " + id));

        TeamMember teamMember = teamMemberService.getTeamMemberById(dto.teamMemberId());

        boolean hasOverlap = leaveRequestRepository.findOverlaps(
                dto.teamMemberId(),
                dto.startDate(),
                dto.endDate())
                .stream()
                .anyMatch(existing -> !existing.getId().equals(id));

        if (hasOverlap) {
            throw new ValidationException("Leave request overlaps with an existing leave request");
        }

        leaveRequest.updateDetails(
                teamMember,
                dto.startDate(),
                dto.endDate(),
                dto.reason());

        return toDto(leaveRequestRepository.save(leaveRequest));
    }

    public void deleteLeaveRequest(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Leave request not found with id: " + id));

        leaveRequestRepository.delete(leaveRequest);
    }

    public LeaveRequestDto updateStatus(Long id, UpdateLeaveStatusDto dto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Leave request not found with id: " + id));

        leaveRequest.updateStatus(dto.status());

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        return toDto(saved);
    }

    public List<LeaveRequestDto> listLeaveRequests(Long teamMemberId, LeaveStatus status) {
        return leaveRequestRepository.findByFilters(teamMemberId, status)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private LeaveRequestDto toDto(LeaveRequest leaveRequest) {
        return new LeaveRequestDto(
                leaveRequest.getId(),
                leaveRequest.getTeamMember().getId(),
                leaveRequest.getTeamMember().getName(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus());
    }
}