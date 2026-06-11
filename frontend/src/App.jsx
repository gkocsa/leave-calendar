import { useEffect, useState } from "react";
import "./App.css";

const API_BASE = "http://localhost:8080";

export default function App() {
  const [teamMembers, setTeamMembers] = useState([]);
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [onCallSchedule, setOnCallSchedule] = useState([]);
  const [showTeamMembers, setShowTeamMembers] = useState(false);

  const [form, setForm] = useState({
    teamMemberId: "",
    startDate: "",
    endDate: "",
    reason: ""
  });

  useEffect(() => {
    loadData();
  }, []);

  async function loadData() {
    const members = await fetch(`${API_BASE}/api/team-members`).then(r => r.json());
    const leaves = await fetch(`${API_BASE}/api/leave-requests`).then(r => r.json());
    const schedule = await fetch(`${API_BASE}/api/on-call?from=2026-07-01&weeks=5`).then(r => r.json());

    setTeamMembers(members);
    setLeaveRequests(leaves);
    setOnCallSchedule(schedule);
  }

  async function createLeaveRequest(e) {
    e.preventDefault();

    const response = await fetch(`${API_BASE}/api/leave-requests`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        ...form,
        teamMemberId: Number(form.teamMemberId)
      })
    });

    if (!response.ok) {
      const error = await response.json();
      alert(error.message || "Failed to create leave request");
      return;
    }

    setForm({
      teamMemberId: "",
      startDate: "",
      endDate: "",
      reason: ""
    });

    loadData();
  }

  async function updateStatus(id, status) {
    await fetch(`${API_BASE}/api/leave-requests/${id}/status`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ status })
    });

    loadData();
  }

  async function deleteLeaveRequest(id) {
    await fetch(`${API_BASE}/api/leave-requests/${id}`, {
      method: "DELETE"
    });

    loadData();
  }

  return (
    <main className="page">
      <h1>Team Leave Calendar</h1>

      <section className="card">
        <h2>Create Leave Request</h2>

        <form onSubmit={createLeaveRequest} className="form">
          <select
            value={form.teamMemberId}
            onChange={e => setForm({ ...form, teamMemberId: e.target.value })}
            required
          >
            <option value="">Select team member</option>
            {teamMembers.map(member => (
              <option key={member.id} value={member.id}>
                {member.name}
              </option>
            ))}
          </select>

          <input
            type="date"
            value={form.startDate}
            onChange={e => setForm({ ...form, startDate: e.target.value })}
            required
          />

          <input
            type="date"
            value={form.endDate}
            onChange={e => setForm({ ...form, endDate: e.target.value })}
            required
          />

          <input
            type="text"
            placeholder="Reason"
            value={form.reason}
            onChange={e => setForm({ ...form, reason: e.target.value })}
            required
          />

          <button type="submit">Create</button>
        </form>
      </section>

      <section className="card">
        <button
          className="toggle-button"
          onClick={() => setShowTeamMembers(!showTeamMembers)}
        >
          {showTeamMembers ? "Hide Team Members" : "View Team Members"}
        </button>

        {showTeamMembers && (
          <div className="team-list">
            <h2>Team Members</h2>
            <ul>
              {teamMembers.map(member => (
                <li key={member.id}>
                  {member.rotationPosition}. {member.name}
                </li>
              ))}
            </ul>
          </div>
        )}
      </section>

      <section className="card">
        <h2>Leave Requests</h2>

        {leaveRequests.length === 0 ? (
          <p>No leave requests yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Member</th>
                <th>Dates</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {leaveRequests.map(request => (
                <tr
                  key={request.id}
                  className={request.status === "PENDING" ? "pending-row" : ""}
                >
                  <td>{request.teamMemberName}</td>
                  <td>
                    {request.startDate} to {request.endDate}
                  </td>
                  <td>{request.reason}</td>
                  <td>{request.status}</td>
                  <td>
                    {request.status !== "APPROVED" && (
                      <button
                        className="btn approve"
                        onClick={() => updateStatus(request.id, "APPROVED")}
                      >
                        Approve
                      </button>
                    )}

                    {request.status !== "REJECTED" && (
                      <button
                        className="btn reject"
                        onClick={() => updateStatus(request.id, "REJECTED")}
                      >
                        Reject
                      </button>
                    )}

                    <button
                      className="btn delete"
                      onClick={() => deleteLeaveRequest(request.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>

      <section className="card">
        <h2>On-Call Schedule</h2>

        <table>
          <thead>
            <tr>
              <th>Week</th>
              <th>On Call</th>
              <th>Conflict</th>
            </tr>
          </thead>

          <tbody>
            {onCallSchedule.map(week => (
              <tr
                key={week.weekStart}
                className={week.hasConflict ? "conflict" : ""}
              >
                <td>
                  {week.weekStart} to {week.weekEnd}
                </td>
                <td>{week.teamMemberName}</td>
                <td>
                  {week.hasConflict
                    ? `On approved leave: ${week.conflicts
                        .map(c => `${c.leaveStart} to ${c.leaveEnd}`)
                        .join(", ")}`
                    : "No conflict"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </main>
  );
}