const API_BASE = process.env.REACT_APP_API_BASE || "http://localhost:8080";

async function request(path, options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
    ...options,
  });

  // Try to parse JSON in both success/failure cases
  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const data = isJson ? await res.json().catch(() => null) : await res.text().catch(() => null);

  if (!res.ok) {
    // Backend ApiError format: { status, error, message, path, ... }
    const message =
      (data && data.message) ||
      (typeof data === "string" && data) ||
      `Request failed with status ${res.status}`;

    const err = new Error(message);
    err.status = res.status;
    err.data = data;
    throw err;
  }

  return data;
}

export const studentApi = {
  //page
   list: ({ page = 0, size = 10, q = "", sort = "id,desc" } = {}) => {
    const params = new URLSearchParams();
    params.set("page", page);
    params.set("size", size);
    if (q && q.trim()) params.set("q", q.trim());
    if (sort) params.set("sort", sort);
    return request(`/students?${params.toString()}`);
  },
  create: (payload) => request("/students", { method: "POST", body: JSON.stringify(payload) }),
  update: (id, payload) => request(`/students/${id}`, { method: "PUT", body: JSON.stringify(payload) }),
  remove: (id) => request(`/students/${id}`, { method: "DELETE" }),
};
