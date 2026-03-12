import './App.css'
import { useEffect, useMemo, useState } from 'react'
import ContactForm from './components/ContactForm'
import ContactsTable from './components/ContactsTable'
import Modal from './components/Modal'
import * as contactsApi from './services/contactsApi'

function App() {
  const [contacts, setContacts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [query, setQuery] = useState('')

  const [modalOpen, setModalOpen] = useState(false)
  const [editingContact, setEditingContact] = useState(null)
  const [saving, setSaving] = useState(false)
  const [deletingId, setDeletingId] = useState(null)

  async function loadContacts() {
    setError('')
    setLoading(true)
    try {
      const list = await contactsApi.getContacts()
      setContacts(Array.isArray(list) ? list : [])
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Failed to load contacts.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    void loadContacts()
  }, [])

  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase()
    if (!q) return contacts

    return contacts.filter((c) => {
      const haystack = [
        c.firstName,
        c.lastName,
        c.address,
        c.city,
        c.state,
        c.zip,
        c.phoneNumber,
        c.email,
      ]
        .filter(Boolean)
        .join(' ')
        .toLowerCase()
      return haystack.includes(q)
    })
  }, [contacts, query])

  function openAdd() {
    setEditingContact(null)
    setModalOpen(true)
  }

  function openEdit(contact) {
    setEditingContact(contact)
    setModalOpen(true)
  }

  function closeModal() {
    if (saving) return
    setModalOpen(false)
    setEditingContact(null)
  }

  async function saveContact(dto) {
    setError('')
    setSaving(true)

    try {
      if (editingContact?.id) {
        const updated = await contactsApi.updateContact(editingContact.id, dto)
        setContacts((prev) => prev.map((c) => (c.id === updated.id ? updated : c)))
      } else {
        const created = await contactsApi.createContact(dto)
        setContacts((prev) => [...prev, created])
      }

      setModalOpen(false)
      setEditingContact(null)
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Save failed.')
    } finally {
      setSaving(false)
    }
  }

  async function deleteContact(contact) {
    const name = [contact.firstName, contact.lastName].filter(Boolean).join(' ').trim()
    const ok = window.confirm(`Delete ${name || 'this contact'}?`)
    if (!ok) return

    setError('')
    setDeletingId(contact.id)
    try {
      await contactsApi.deleteContact(contact.id)
      setContacts((prev) => prev.filter((c) => c.id !== contact.id))
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Delete failed.')
    } finally {
      setDeletingId(null)
    }
  }

  return (
    <div className="page">
      <header className="header">
        <div>
          <div className="title">Address Book</div>
          <div className="subtitle">Manage your contacts</div>
        </div>
        <button className="btn" type="button" onClick={openAdd}>
          Add contact
        </button>
      </header>

      <section className="toolbar">
        <input
          className="search"
          placeholder="Search by name, phone, email, city…"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <div className="meta">
          {loading ? 'Loading…' : `${filtered.length} / ${contacts.length}`}
        </div>
      </section>

      {error ? <div className="banner bannerError">{error}</div> : null}

      <section className="card">
        <ContactsTable
          contacts={filtered}
          onEdit={openEdit}
          onDelete={deleteContact}
          deletingId={deletingId}
        />
      </section>

      {modalOpen ? (
        <Modal title={editingContact?.id ? 'Edit contact' : 'Add contact'} onClose={closeModal}>
          <ContactForm
            key={editingContact?.id ?? 'new'}
            contact={editingContact}
            onCancel={closeModal}
            onSave={saveContact}
            saving={saving}
          />
        </Modal>
      ) : null}
    </div>
  )
}

export default App
