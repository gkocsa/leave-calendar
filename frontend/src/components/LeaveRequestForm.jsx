export default function LeaveRequestForm({ teamMembers, form, setForm, onSubmit }) {
  return (
    <section className="card">
      <h2>Create Leave Request</h2>

      <form onSubmit={onSubmit} className="form">
        <select
          value={form.teamMemberId}
          onChange={(e) => setForm({ ...form, teamMemberId: e.target.value })}
          required
        >
          <option value="">Select team member</option>
          {teamMembers.map((member) => (
            <option key={member.id} value={member.id}>
              {member.name}
            </option>
          ))}
        </select>

        <input
          type="date"
          value={form.startDate}
          onChange={(e) => setForm({ ...form, startDate: e.target.value })}
          required
        />

        <input
          type="date"
          value={form.endDate}
          onChange={(e) => setForm({ ...form, endDate: e.target.value })}
          required
        />

        <input
          type="text"
          placeholder="Reason"
          value={form.reason}
          onChange={(e) => setForm({ ...form, reason: e.target.value })}
          required
        />

        <button type="submit">Create</button>
      </form>
    </section>
  );
}