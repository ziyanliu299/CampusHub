// frontend/src/auth/token.js
const TOKEN_KEY = "campushub_token";

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function clearToken() {
  localStorage.removeItem(TOKEN_KEY);
}

// frontend/src/auth/jwt.js
export function parseJwt(token) {
  try {
    const payload = token.split(".")[1];
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    const json = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + c.charCodeAt(0).toString(16).padStart(2, "0"))
        .join("")
    );
    return JSON.parse(json);
  } catch {
    return null;
  }
}

export function getRoleFromToken(token) {
  return parseJwt(token)?.role || null; // "ADMIN" or "USER"
}
// frontend/src/auth/jwt.js
export function getUsernameFromToken(token) {
  // JWT subject = sub
  return parseJwt(token)?.sub || null;
}

export function getExpFromToken(token) {
  return parseJwt(token)?.exp || null; // seconds
}
