package com.example.leavecalendar.controller;

import com.example.leavecalendar.dto.response.TeamMemberDto;
import com.example.leavecalendar.service.TeamMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping
    public List<TeamMemberDto> listActiveTeamMembers() {
        return teamMemberService.listActiveTeamMembers();
    }
}