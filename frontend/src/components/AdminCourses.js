import React, { useEffect, useState } from "react";
import { adminCreateCourse, listCourses } from "../api/courses";

export default function AdminCourses() {
  const [courses, setCourses] = useState([]);
  const [error, setError] = useState("");
  const [form, setForm] = useState({ code: "", title: "", description: "", capacity: 30 });

  const load = async () => {
    setError("");
    try {
      const page = await listCourses({ page: 0, size: 50, sort: "id,desc" });
      setCourses(page.content || []);
    } catch (e) {
      setError(e.message);
    }
  };

  useEffect(() => { load(); }, []);

  const onCreate = async () => {
    setError("");
    try {
      await adminCreateCourse({ ...form, capacity: Number(form.capacity) });
      setForm({ code: "", title: "", description: "", capacity: 30 });
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Admin: Courses</h2>
      {error && <div style={{ color: "red" }}>{error}</div>}

      <div style={{ border: "1px solid #ccc", padding: 12, marginBottom: 16 }}>
        <h3>Create Course</h3>
        <input placeholder="Code" value={form.code} onChange={(e) => setForm({ ...form, code: e.target.value })} /><br/>
        <input placeholder="Title" value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} /><br/>
        <input placeholder="Description" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} /><br/>
        <input type="number" placeholder="Capacity" value={form.capacity} onChange={(e) => setForm({ ...form, capacity: e.target.value })} /><br/>
        <button onClick={onCreate} style={{ marginTop: 8 }}>Create</button>
      </div>

      <h3>All Courses</h3>
      <ul>
        {courses.map((c) => (
          <li key={c.id}>
            <b>{c.code}</b> — {c.title} ({c.enrolledCount}/{c.capacity})
          </li>
        ))}
      </ul>
    </div>
  );
}
