import React, { useEffect, useState } from "react";
import { listCourses } from "../api/courses";
import { enroll } from "../api/enrollments";
import { getRoleFromToken } from "../auth/jwt";
import { getToken } from "../auth/token";

export default function CourseCatalog() {
  const [q, setQ] = useState("");
  const [courses, setCourses] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const role = getRoleFromToken(getToken?.() || localStorage.getItem("token"));

  const load = async () => {
    setLoading(true);
    setError("");
    try {
      const page = await listCourses({ q, page: 0, size: 20, sort: "id,desc" });
      setCourses(page.content || []);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, []);

  const onEnroll = async (courseId) => {
    try {
      await enroll(courseId);
      alert("Enrolled!");
      load();
    } catch (e) {
      alert(e.message);
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Course Catalog</h2>

      <div style={{ marginBottom: 12 }}>
        <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="Search code/title..." />
        <button onClick={load} style={{ marginLeft: 8 }}>Search</button>
      </div>

      {loading && <div>Loading...</div>}
      {error && <div style={{ color: "red" }}>{error}</div>}

      <ul>
        {courses.map((c) => {
          const full = (c.enrolledCount ?? 0) >= (c.capacity ?? 0);
          return (
            <li key={c.id} style={{ marginBottom: 12 }}>
              <b>{c.code}</b> — {c.title}<br />
              Capacity: {c.enrolledCount}/{c.capacity}<br />

              {role === "USER" && (
                <button disabled={full} onClick={() => onEnroll(c.id)}>
                  {full ? "Full" : "Enroll"}
                </button>
              )}
            </li>
          );
        })}
      </ul>
    </div>
  );
}
