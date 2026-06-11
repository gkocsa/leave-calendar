export default function CommentModal({
  commentModal,
  setCommentModal,
  onSave,
}) {
  if (!commentModal.isOpen) {
    return null;
  }

  return (
    <div className="modal-backdrop">
      <div className="modal">
        <h2>Comment for {commentModal.teamMemberName}</h2>

        <textarea
          value={commentModal.comment}
          onChange={(e) =>
            setCommentModal({
              ...commentModal,
              comment: e.target.value,
            })
          }
          placeholder="Write a comment..."
          rows="6"
        />

        <div className="modal-actions">
          <button type="button" onClick={onSave}>
            Save
          </button>

          <button
            type="button"
            onClick={() =>
              setCommentModal({
                isOpen: false,
                leaveRequestId: null,
                teamMemberName: "",
                comment: "",
              })
            }
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}