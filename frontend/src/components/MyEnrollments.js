import React, { useEffect, useState } from "react";
import { drop, listMyEnrollments } from "../api/enrollments";

export default function MyEnrollments() {
  const [items, setItems] = useState([]);
  const [error, setError] = useState("");

  const load = async () => {
    setError("");
    try {
      const page = await listMyEnrollments({ page: 0, size: 20, sort: "id,desc" });
      setItems(page.content || []);
    } catch (e) {
      setError(e.message);
    }
  };

  useEffect(() => { load(); }, []);

  const onDrop = async (courseId) => {
    try {
      await drop(courseId);
      alert("Dropped!");
      load();
    } catch (e) {
      alert(e.message);
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>My Enrollments</h2>
      {error && <div style={{ color: "red" }}>{error}</div>}
      {items.length === 0 && !error && <div>No enrollments yet.</div>}

      <ul>
        {items.map((e) => (
          <li key={e.id} style={{ marginBottom: 12 }}>
            <b>{e.courseCode}</b> — {e.courseTitle}<br />
            <button onClick={() => onDrop(e.courseId)}>Drop</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
