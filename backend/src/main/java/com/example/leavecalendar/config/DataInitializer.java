package com.example.leavecalendar.config;

import com.example.leavecalendar.entity.TeamMember;
import com.example.leavecalendar.repository.TeamMemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TeamMemberRepository teamMemberRepository;

    public DataInitializer(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public void run(String... args) {
        if (teamMemberRepository.count() == 0) {
            teamMemberRepository.saveAll(List.of(
                    new TeamMember("Alice", 1),
                    new TeamMember("Bob", 2),
                    new TeamMember("Charlie", 3),
                    new TeamMember("Diana", 4)
            ));
        }
    }
}