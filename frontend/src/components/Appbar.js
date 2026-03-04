import React from "react";

export default function Appbar({ username, role, onLogout, page, onNavigate }) {
  return (
    <div style={{ display: "flex", gap: 12, padding: 12, borderBottom: "1px solid #ddd", alignItems: "center" }}>
      <b>CampusHub</b>

      <button onClick={() => onNavigate("students")} disabled={page === "students"}>
        Students
      </button>

      <button onClick={() => onNavigate("courses")} disabled={page === "courses"}>
        Courses
      </button>

      {role === "USER" && (
        <button onClick={() => onNavigate("enrollments")} disabled={page === "enrollments"}>
          My Enrollments
        </button>
      )}

      {role === "ADMIN" && (
        <button onClick={() => onNavigate("adminCourses")} disabled={page === "adminCourses"}>
          Admin Courses
        </button>
      )}

      <div style={{ marginLeft: "auto" }}>
        <span style={{ marginRight: 12 }}>
          {username} ({role})
        </span>
        <button onClick={onLogout}>Logout</button>
      </div>
    </div>
  );
}
