const API_BASE = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

import { getToken } from "../auth/token";

export async function apiFetch(path, options = {}) {
  const token = getToken?.() || localStorage.getItem("token"); // safe fallback

  const res = await fetch(`${API_BASE}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...(options.headers || {}),
    },
  });

  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    // backend sends ApiError {message,...}
    const err = new Error(data?.message || `Request failed (${res.status})`);
    err.status = res.status;
    err.body = data;
    throw err;
  }
  return data;
}
