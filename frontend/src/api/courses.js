import { apiFetch } from "./client";

export function listCourses({ page = 0, size = 20, sort = "id,desc", q = "" } = {}) {
  const params = new URLSearchParams({ page, size, sort });
  if (q) params.set("q", q);
  return apiFetch(`/api/courses?${params.toString()}`);
}

export function adminCreateCourse(payload) {
  return apiFetch("/api/admin/courses", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}
