# React + Vite

Simple Address Book UI (React + Vite) that talks to the Spring backend at `http://localhost:8080`.

## Run

- Backend (from repo root): `cd backend` then `mvnw spring-boot:run`
- Frontend (this folder): `npm install` then `npm run dev`

The dev server proxies `/contacts` to the backend to avoid CORS issues.

If you want to point to a different backend host/port, set `VITE_API_BASE_URL` (example: `VITE_API_BASE_URL=http://localhost:8080`).

On Windows PowerShell, if `npm` is blocked by execution policy, run `npm.cmd` instead (example: `npm.cmd run dev`).
