export default function TeamMembersPanel({
  teamMembers,
  showTeamMembers,
  setShowTeamMembers,
}) {
  return (
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
            {teamMembers.map((member) => (
              <li key={member.id}>
                {member.rotationPosition}. {member.name}
              </li>
            ))}
          </ul>
        </div>
      )}
    </section>
  );
}