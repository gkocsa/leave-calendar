package com.example.leavecalendar.service;

import com.example.leavecalendar.dto.request.UpdateLeaveCommentDto;
import com.example.leavecalendar.dto.response.LeaveRequestDto;
import com.example.leavecalendar.entity.LeaveRequest;
import com.example.leavecalendar.entity.TeamMember;
import com.example.leavecalendar.exception.NotFoundException;
import com.example.leavecalendar.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LeaveRequestServiceTest {

    private final LeaveRequestRepository leaveRequestRepository = mock(LeaveRequestRepository.class);
    private final TeamMemberService teamMemberService = mock(TeamMemberService.class);

    private final LeaveRequestService leaveRequestService = new LeaveRequestService(
            leaveRequestRepository,
            teamMemberService
    );

    @Test
    void shouldUpdateLeaveRequestComment() {
        TeamMember teamMember = new TeamMember("Alice", 1);

        LeaveRequest leaveRequest = new LeaveRequest(
                teamMember,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 5),
                "Vacation"
        );

        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(leaveRequest));
        when(leaveRequestRepository.save(leaveRequest)).thenReturn(leaveRequest);

        LeaveRequestDto result = leaveRequestService.updateComment(
                1L,
                new UpdateLeaveCommentDto("Approved after discussion")
        );

        assertEquals("Approved after discussion", result.comment());
        verify(leaveRequestRepository).save(leaveRequest);
    }

    @Test
    void shouldThrowNotFoundWhenUpdatingCommentForMissingLeaveRequest() {
        when(leaveRequestRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                leaveRequestService.updateComment(
                        99L,
                        new UpdateLeaveCommentDto("Missing request")
                )
        );
    }
}