import "./App.css";
import { useState } from "react";
import Student from "./components/Student";
import Login from "./components/Login";
import Register from "./components/Register";
import Appbar from "./components/Appbar";

import CourseCatalog from "./components/CourseCatalog";
import MyEnrollments from "./components/MyEnrollments";
import AdminCourses from "./components/AdminCourses";

import { getToken, clearToken } from "./auth/token";
import { getRoleFromToken, getUsernameFromToken } from "./auth/jwt";

function App() {
  const [authed, setAuthed] = useState(!!getToken());
  const [mode, setMode] = useState("login"); // "login" | "register"

  const token = getToken();
  const username = token ? getUsernameFromToken(token) : null;
  const role = token ? getRoleFromToken(token) : null;

  //  page state
  const [page, setPage] = useState("students");
  // pages: "students" | "courses" | "enrollments" | "adminCourses"

  // Auth gate
  if (!authed) {
    if (mode === "register") {
      return (
        <Register
          onRegisterSuccess={() => {
            setAuthed(true);
            setPage("students");
          }}
          onGoLogin={() => setMode("login")}
        />
      );
    }
    return (
      <Login
        onLoginSuccess={() => {
          setAuthed(true);
          setPage("students");
        }}
        onGoRegister={() => setMode("register")}
      />
    );
  }

  const handleLogout = () => {
    clearToken();
    setAuthed(false);
    setMode("login");
  };

  //  render selected page
  let content = null;
  if (page === "students") content = <Student />;
  else if (page === "courses") content = <CourseCatalog />;
  else if (page === "enrollments") content = <MyEnrollments />;
  else if (page === "adminCourses") content = <AdminCourses />;

  return (
    <div className="App">
      <Appbar
        username={username}
        role={role}
        onLogout={handleLogout}
        page={page}
        onNavigate={(next) => setPage(next)}
      />

      {content}
    </div>
  );
}

export default App;
