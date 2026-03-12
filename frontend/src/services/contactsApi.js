const DEFAULT_BASE_URL = ''

function joinUrl(baseUrl, path) {
  if (!baseUrl) return path
  return `${baseUrl.replace(/\/$/, '')}${path}`
}

async function parseResponseBody(response) {
  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) return await response.json()
  const text = await response.text()
  return text ? text : null
}

async function request(path, options) {
  const baseUrl = import.meta.env.VITE_API_BASE_URL ?? DEFAULT_BASE_URL
  const response = await fetch(joinUrl(baseUrl, path), {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })

  if (!response.ok) {
    const body = await parseResponseBody(response).catch(() => null)
    const suffix = typeof body === 'string' && body.trim() ? `: ${body}` : ''
    throw new Error(`Request failed (${response.status})${suffix}`)
  }

  return await parseResponseBody(response)
}

export function getContacts() {
  return request('/contacts', { method: 'GET' })
}

export function createContact(contactDto) {
  return request('/contacts', { method: 'POST', body: JSON.stringify(contactDto) })
}

export function updateContact(id, contactDto) {
  return request(`/contacts/${id}`, {
    method: 'PUT',
    body: JSON.stringify(contactDto),
  })
}

export function deleteContact(id) {
  return request(`/contacts/${id}`, { method: 'DELETE' })
}

