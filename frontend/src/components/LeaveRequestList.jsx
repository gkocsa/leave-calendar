export default function LeaveRequestList({
  teamMembers,
  leaveRequests,
  filters,
  setFilters,
  onUpdateStatus,
  onDelete,
  onOpenComment,
}) {
  return (
    <section className="card">
      <h2>Leave Requests</h2>

      <div className="filters">
        <select
          value={filters.teamMemberId}
          onChange={(e) =>
            setFilters({ ...filters, teamMemberId: e.target.value })
          }
        >
          <option value="">All team members</option>
          {teamMembers.map((member) => (
            <option key={member.id} value={member.id}>
              {member.name}
            </option>
          ))}
        </select>

        <select
          value={filters.status}
          onChange={(e) => setFilters({ ...filters, status: e.target.value })}
        >
          <option value="">All statuses</option>
          <option value="PENDING">Pending</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>

        <button
          type="button"
          onClick={() => setFilters({ teamMemberId: "", status: "" })}
        >
          Clear filters
        </button>
      </div>

      {leaveRequests.length === 0 ? (
        <p>No leave requests found.</p>
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
            {leaveRequests.map((request) => (
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
                      onClick={() => onUpdateStatus(request.id, "APPROVED")}
                    >
                      Approve
                    </button>
                  )}

                  {request.status !== "REJECTED" && (
                    <button
                      className="btn reject"
                      onClick={() => onUpdateStatus(request.id, "REJECTED")}
                    >
                      Reject
                    </button>
                  )}

                  <button
                    className="btn comment"
                    onClick={() => onOpenComment(request)}
                  >
                    Comment
                  </button>

                  <button
                    className="btn delete"
                    onClick={() => onDelete(request.id)}
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
  );
}