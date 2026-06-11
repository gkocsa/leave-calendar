package com.example.leavecalendar.repository;

import com.example.leavecalendar.entity.LeaveRequest;
import com.example.leavecalendar.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("""
                select l from LeaveRequest l
                where l.teamMember.id = :teamMemberId
                and l.startDate <= :endDate
                and l.endDate >= :startDate
            """)
    List<LeaveRequest> findOverlaps(Long teamMemberId, LocalDate startDate, LocalDate endDate);

    @Query("""
                select l from LeaveRequest l
                where l.status = :status
                order by l.startDate
            """)
    List<LeaveRequest> listByStatus(LeaveStatus status);

    @Query("""
                select l from LeaveRequest l
                where l.teamMember.id = :teamMemberId
                and l.status = com.example.leavecalendar.enums.LeaveStatus.APPROVED
                and l.startDate <= :weekEnd
                and l.endDate >= :weekStart
            """)
    List<LeaveRequest> findApprovedConflicts(Long teamMemberId, LocalDate weekStart, LocalDate weekEnd);
}