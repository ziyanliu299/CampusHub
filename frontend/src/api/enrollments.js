import { apiFetch } from "./client";

export function enroll(courseId) {
  return apiFetch("/api/me/enrollments", {
    method: "POST",
    body: JSON.stringify({ courseId }),
  });
}

export function drop(courseId) {
  return apiFetch(`/api/me/enrollments/${courseId}`, { method: "DELETE" });
}

export function listMyEnrollments({ page = 0, size = 20, sort = "id,desc" } = {}) {
  const params = new URLSearchParams({ page, size, sort });
  return apiFetch(`/api/me/enrollments?${params.toString()}`);
}