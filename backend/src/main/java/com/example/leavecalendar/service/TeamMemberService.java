package com.example.leavecalendar.service;

import com.example.leavecalendar.dto.response.TeamMemberDto;
import com.example.leavecalendar.entity.TeamMember;
import com.example.leavecalendar.exception.NotFoundException;
import com.example.leavecalendar.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public List<TeamMemberDto> listActiveTeamMembers() {
        return teamMemberRepository.listActiveTeamMembers()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public TeamMember getTeamMemberById(Long id) {
        return teamMemberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team member not found with id: " + id));
    }

    private TeamMemberDto toDto(TeamMember teamMember) {
        return new TeamMemberDto(
                teamMember.getId(),
                teamMember.getName(),
                teamMember.getRotationPosition(),
                teamMember.isActive()
        );
    }
}