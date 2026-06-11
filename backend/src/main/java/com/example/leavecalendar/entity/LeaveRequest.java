package com.example.leavecalendar.entity;

import com.example.leavecalendar.enums.LeaveStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_member_id", nullable = false)
    private TeamMember teamMember;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String reason;

    @Column(length = 1000)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status = LeaveStatus.PENDING;

    protected LeaveRequest() {
    }

    public LeaveRequest(TeamMember teamMember, LocalDate startDate, LocalDate endDate, String reason) {
        this.teamMember = teamMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = LeaveStatus.PENDING;
    }

    public void updateDetails(TeamMember teamMember, LocalDate startDate, LocalDate endDate, String reason) {
        this.teamMember = teamMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }
    
    public Long getId() {
        return id;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public String getComment() {
        return comment;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
    
    public void updateStatus(LeaveStatus status) {
        this.status = status;
    }
}