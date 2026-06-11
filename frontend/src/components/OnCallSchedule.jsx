export default function OnCallSchedule({ onCallSchedule }) {
  return (
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
          {onCallSchedule.map((week) => (
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
                      .map((c) => `${c.leaveStart} to ${c.leaveEnd}`)
                      .join(", ")}`
                  : "No conflict"}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}