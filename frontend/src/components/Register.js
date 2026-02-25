import { useState } from "react";
import { authApi } from "../api/api";
import { setToken } from "../auth/token";

export default function Register({ onRegisterSuccess, onGoLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("USER");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");

    try {
      setLoading(true);
      const result = await authApi.register({ username, password, role });
      setToken(result.token);
      onRegisterSuccess?.();
    } catch (e) {
      setError(e.message || "Register failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: "60px auto", padding: 20, border: "1px solid #ddd", borderRadius: 8 }}>
      <h2>Register</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleRegister}>
        <div style={{ marginBottom: 12 }}>
          <label>Username</label>
          <input style={{ width: "100%", padding: 8 }} value={username} onChange={(e) => setUsername(e.target.value)} />
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Password</label>
          <input
            type="password"
            style={{ width: "100%", padding: 8 }}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Role</label>
          <select style={{ width: "100%", padding: 8 }} value={role} onChange={(e) => setRole(e.target.value)}>
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
          </select>
        </div>

        <button type="submit" disabled={loading} style={{ width: "100%", padding: 10 }}>
          {loading ? "Registering..." : "Register"}
        </button>
      </form>

      <p style={{ marginTop: 12 }}>
        Already have an account?{" "}
        <button type="button" onClick={onGoLogin} style={{ textDecoration: "underline", background: "none", border: "none", cursor: "pointer" }}>
          Login
        </button>
      </p>
    </div>
  );
}