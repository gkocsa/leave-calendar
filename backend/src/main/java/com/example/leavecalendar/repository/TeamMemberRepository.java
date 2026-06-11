package com.example.leavecalendar.repository;

import com.example.leavecalendar.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @Query("select t from TeamMember t where t.active = true order by t.rotationPosition")
    List<TeamMember> listActiveTeamMembers();
}