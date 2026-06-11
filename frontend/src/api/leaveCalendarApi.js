const API_BASE = "http://localhost:8080";

export async function getTeamMembers() {
  return fetch(`${API_BASE}/api/team-members`).then((r) => r.json());
}

export async function getLeaveRequests(filters) {
  const params = new URLSearchParams();

  if (filters.teamMemberId) {
    params.append("teamMemberId", filters.teamMemberId);
  }

  if (filters.status) {
    params.append("status", filters.status);
  }

  const url = `${API_BASE}/api/leave-requests${
    params.toString() ? `?${params.toString()}` : ""
  }`;

  return fetch(url).then((r) => r.json());
}

export async function getOnCallSchedule() {
  return fetch(`${API_BASE}/api/on-call?from=2026-07-01&weeks=5`).then((r) =>
    r.json(),
  );
}

export async function createLeaveRequest(form) {
  const response = await fetch(`${API_BASE}/api/leave-requests`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      ...form,
      teamMemberId: Number(form.teamMemberId),
    }),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Failed to create leave request");
  }

  return response.json();
}

export async function updateLeaveStatus(id, status) {
  return fetch(`${API_BASE}/api/leave-requests/${id}/status`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ status }),
  });
}

export async function deleteLeaveRequest(id) {
  return fetch(`${API_BASE}/api/leave-requests/${id}`, {
    method: "DELETE",
  });
}

export async function updateLeaveComment(id, comment) {
  return fetch(`${API_BASE}/api/leave-requests/${id}/comment`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ comment }),
  });
}