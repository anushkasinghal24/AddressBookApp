import { useMemo, useState } from 'react'

const EMPTY = {
  firstName: '',
  lastName: '',
  address: '',
  city: '',
  state: '',
  zip: '',
  phoneNumber: '',
  email: '',
}

function normalize(value) {
  return typeof value === 'string' ? value : ''
}

function validate(form) {
  const errors = {}
  if (!form.firstName.trim()) errors.firstName = 'First name is required.'
  if (!form.lastName.trim()) errors.lastName = 'Last name is required.'
  if (!form.phoneNumber.trim()) errors.phoneNumber = 'Phone number is required.'

  if (form.email.trim()) {
    const ok = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email.trim())
    if (!ok) errors.email = 'Enter a valid email address.'
  }

  return errors
}

export default function ContactForm({ contact, onCancel, onSave, saving }) {
  const initialForm = useMemo(() => {
    if (!contact) return EMPTY
    return {
      firstName: normalize(contact.firstName),
      lastName: normalize(contact.lastName),
      address: normalize(contact.address),
      city: normalize(contact.city),
      state: normalize(contact.state),
      zip: normalize(contact.zip),
      phoneNumber: normalize(contact.phoneNumber),
      email: normalize(contact.email),
    }
  }, [contact])

  const [form, setForm] = useState(() => initialForm)
  const errors = useMemo(() => validate(form), [form])

  function updateField(field) {
    return (e) => setForm((prev) => ({ ...prev, [field]: e.target.value }))
  }

  async function onSubmit(e) {
    e.preventDefault()
    if (Object.keys(errors).length) return
    await onSave({ ...form })
  }

  const isEdit = Boolean(contact?.id)
  const title = isEdit ? 'Edit contact' : 'Add contact'

  return (
    <form className="form" onSubmit={onSubmit}>
      <div className="formTitle">{title}</div>

      <div className="grid2">
        <label className="field">
          <div className="label">First name</div>
          <input value={form.firstName} onChange={updateField('firstName')} autoFocus />
          {errors.firstName ? <div className="error">{errors.firstName}</div> : null}
        </label>

        <label className="field">
          <div className="label">Last name</div>
          <input value={form.lastName} onChange={updateField('lastName')} />
          {errors.lastName ? <div className="error">{errors.lastName}</div> : null}
        </label>
      </div>

      <label className="field">
        <div className="label">Address</div>
        <input value={form.address} onChange={updateField('address')} />
      </label>

      <div className="grid3">
        <label className="field">
          <div className="label">City</div>
          <input value={form.city} onChange={updateField('city')} />
        </label>
        <label className="field">
          <div className="label">State</div>
          <input value={form.state} onChange={updateField('state')} />
        </label>
        <label className="field">
          <div className="label">ZIP</div>
          <input value={form.zip} onChange={updateField('zip')} inputMode="numeric" />
        </label>
      </div>

      <div className="grid2">
        <label className="field">
          <div className="label">Phone</div>
          <input value={form.phoneNumber} onChange={updateField('phoneNumber')} inputMode="tel" />
          {errors.phoneNumber ? <div className="error">{errors.phoneNumber}</div> : null}
        </label>

        <label className="field">
          <div className="label">Email</div>
          <input value={form.email} onChange={updateField('email')} inputMode="email" />
          {errors.email ? <div className="error">{errors.email}</div> : null}
        </label>
      </div>

      <div className="actions">
        <button className="btn btnGhost" type="button" onClick={onCancel} disabled={saving}>
          Cancel
        </button>
        <button className="btn" type="submit" disabled={saving || Object.keys(errors).length}>
          {saving ? 'Saving…' : isEdit ? 'Save changes' : 'Add'}
        </button>
      </div>
    </form>
  )
}
