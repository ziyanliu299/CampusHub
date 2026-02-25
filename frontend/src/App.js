import "./App.css";
import { useState } from "react";
import Student from "./components/Student";
import Login from "./components/Login";
import Appbar from "./components/Appbar";
import { getToken, clearToken } from "./auth/token";
import { getRoleFromToken, getUsernameFromToken } from "./auth/jwt";
import Register from "./components/Register";

function App() {
  const [authed, setAuthed] = useState(!!getToken());

  const token = getToken();
  const username = token ? getUsernameFromToken(token) : null;
  const role = token ? getRoleFromToken(token) : null;
  const [mode, setMode] = useState("login"); // "login" | "register"
  if (!authed) {
   if (mode === "register") {
       return <Register onRegisterSuccess={() => setAuthed(true)} onGoLogin={() => setMode("login")} />;
     }
     return <Login onLoginSuccess={() => setAuthed(true)} onGoRegister={() => setMode("register")} />;
  }

  const handleLogout = () => {
    clearToken();
    setAuthed(false);
  };
console.log("Appbar props:", username, role);
  return (
    <div className="App">
      <Appbar username={username} role={role} onLogout={handleLogout} />
      <Student />
    </div>
  );
}

export default App;