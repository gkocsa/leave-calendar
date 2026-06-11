import { useEffect, useState } from "react";
import "./App.css";

import {
  createLeaveRequest,
  deleteLeaveRequest,
  getLeaveRequests,
  getOnCallSchedule,
  getTeamMembers,
  updateLeaveComment,
  updateLeaveStatus,
} from "./api/leaveCalendarApi";

import CommentModal from "./components/CommentModal";
import LeaveRequestForm from "./components/LeaveRequestForm";
import LeaveRequestList from "./components/LeaveRequestList";
import OnCallSchedule from "./components/OnCallSchedule";
import TeamMembersPanel from "./components/TeamMembersPanel";

export default function App() {
  const [teamMembers, setTeamMembers] = useState([]);
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [onCallSchedule, setOnCallSchedule] = useState([]);
  const [showTeamMembers, setShowTeamMembers] = useState(false);

  const [filters, setFilters] = useState({
    teamMemberId: "",
    status: "",
  });

  const [commentModal, setCommentModal] = useState({
    isOpen: false,
    leaveRequestId: null,
    teamMemberName: "",
    comment: "",
  });

  const [form, setForm] = useState({
    teamMemberId: "",
    startDate: "",
    endDate: "",
    reason: "",
  });

  useEffect(() => {
    loadData();
  }, [filters.teamMemberId, filters.status]);

  async function loadData() {
    const [members, leaves, schedule] = await Promise.all([
      getTeamMembers(),
      getLeaveRequests(filters),
      getOnCallSchedule(),
    ]);

    setTeamMembers(members);
    setLeaveRequests(leaves);
    setOnCallSchedule(schedule);
  }

  async function handleCreateLeaveRequest(e) {
    e.preventDefault();

    try {
      await createLeaveRequest(form);

      setForm({
        teamMemberId: "",
        startDate: "",
        endDate: "",
        reason: "",
      });

      loadData();
    } catch (error) {
      alert(error.message);
    }
  }

  async function handleUpdateStatus(id, status) {
    await updateLeaveStatus(id, status);
    loadData();
  }

  async function handleDeleteLeaveRequest(id) {
    await deleteLeaveRequest(id);
    loadData();
  }

  function openCommentModal(request) {
    setCommentModal({
      isOpen: true,
      leaveRequestId: request.id,
      teamMemberName: request.teamMemberName,
      comment: request.comment || "",
    });
  }

  async function saveComment() {
    await updateLeaveComment(commentModal.leaveRequestId, commentModal.comment);

    setCommentModal({
      isOpen: false,
      leaveRequestId: null,
      teamMemberName: "",
      comment: "",
    });

    loadData();
  }

  return (
    <main className="page">
      <h1>Team Leave Calendar</h1>

      <LeaveRequestForm
        teamMembers={teamMembers}
        form={form}
        setForm={setForm}
        onSubmit={handleCreateLeaveRequest}
      />

      <TeamMembersPanel
        teamMembers={teamMembers}
        showTeamMembers={showTeamMembers}
        setShowTeamMembers={setShowTeamMembers}
      />

      <LeaveRequestList
        teamMembers={teamMembers}
        leaveRequests={leaveRequests}
        filters={filters}
        setFilters={setFilters}
        onUpdateStatus={handleUpdateStatus}
        onDelete={handleDeleteLeaveRequest}
        onOpenComment={openCommentModal}
      />

      <OnCallSchedule onCallSchedule={onCallSchedule} />

      <CommentModal
        commentModal={commentModal}
        setCommentModal={setCommentModal}
        onSave={saveComment}
      />
    </main>
  );
}