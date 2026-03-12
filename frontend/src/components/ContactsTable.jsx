function fullName(contact) {
  const first = contact.firstName || ''
  const last = contact.lastName || ''
  return `${first} ${last}`.trim() || '—'
}

export default function ContactsTable({
  contacts,
  onEdit,
  onDelete,
  deletingId,
}) {
  return (
    <div className="tableWrap">
      <table className="table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Phone</th>
            <th>Email</th>
            <th>City</th>
            <th>State</th>
            <th className="actionsCol">Actions</th>
          </tr>
        </thead>
        <tbody>
          {contacts.map((c) => (
            <tr key={c.id}>
              <td className="nameCell">{fullName(c)}</td>
              <td>{c.phoneNumber || '—'}</td>
              <td>{c.email || '—'}</td>
              <td>{c.city || '—'}</td>
              <td>{c.state || '—'}</td>
              <td className="rowActions">
                <button className="btn btnSmall btnGhost" type="button" onClick={() => onEdit(c)}>
                  Edit
                </button>
                <button
                  className="btn btnSmall btnDanger"
                  type="button"
                  onClick={() => onDelete(c)}
                  disabled={deletingId === c.id}
                >
                  {deletingId === c.id ? 'Deleting…' : 'Delete'}
                </button>
              </td>
            </tr>
          ))}
          {!contacts.length ? (
            <tr>
              <td colSpan={6} className="empty">
                No contacts found.
              </td>
            </tr>
          ) : null}
        </tbody>
      </table>
    </div>
  )
}

