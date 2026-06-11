package com.example.leavecalendar.service;

import com.example.leavecalendar.dto.response.OnCallConflictDto;
import com.example.leavecalendar.dto.response.OnCallWeekDto;
import com.example.leavecalendar.entity.LeaveRequest;
import com.example.leavecalendar.entity.TeamMember;
import com.example.leavecalendar.repository.LeaveRequestRepository;
import com.example.leavecalendar.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class OnCallService {

    private static final LocalDate ROTATION_START_DATE = LocalDate.of(2026, 1, 5);

    private final TeamMemberRepository teamMemberRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public OnCallService(
            TeamMemberRepository teamMemberRepository,
            LeaveRequestRepository leaveRequestRepository
    ) {
        this.teamMemberRepository = teamMemberRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public List<OnCallWeekDto> getSchedule(LocalDate from, int weeks) {
        LocalDate firstWeekStart = from.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        return java.util.stream.IntStream.range(0, weeks)
                .mapToObj(i -> buildWeek(firstWeekStart.plusWeeks(i)))
                .toList();
    }

    private OnCallWeekDto buildWeek(LocalDate weekStart) {
        List<TeamMember> teamMembers = teamMemberRepository.listActiveTeamMembers();

        LocalDate weekEnd = weekStart.plusDays(6);

        long weeksPassed = ChronoUnit.WEEKS.between(ROTATION_START_DATE, weekStart);
        int index = Math.floorMod(weeksPassed, teamMembers.size());

        TeamMember onCallPerson = teamMembers.get(index);

        List<LeaveRequest> conflicts = leaveRequestRepository.findApprovedConflicts(
                onCallPerson.getId(),
                weekStart,
                weekEnd
        );

        return new OnCallWeekDto(
                weekStart,
                weekEnd,
                onCallPerson.getId(),
                onCallPerson.getName(),
                !conflicts.isEmpty(),
                conflicts.stream()
                        .map(this::toConflictDto)
                        .toList()
        );
    }

    private OnCallConflictDto toConflictDto(LeaveRequest leaveRequest) {
        return new OnCallConflictDto(
                leaveRequest.getId(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason()
        );
    }
}