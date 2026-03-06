// frontend/src/components/Login.js
import { useState } from "react";
import { authApi } from "../api/api";
import { setToken } from "../auth/token";

export default function Login({onLoginSuccess, onGoRegister}) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      setLoading(true);
      const result = await authApi.login({ username, password });
      // backend send { token: "..." }
      setToken(result.token);
      onLoginSuccess?.();
    } catch (e) {
      setError(e.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: "60px auto", padding: 20, border: "1px solid #ddd", borderRadius: 8 }}>
      <h2>Login</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleLogin}>
        <div style={{ marginBottom: 12 }}>
          <label>Username</label>
          <input
            style={{ width: "100%", padding: 8 }}
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            disabled={loading}
          />
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Password</label>
          <input
            type="password"
            style={{ width: "100%", padding: 8 }}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            disabled={loading}
          />
        </div>

        <button type="submit" disabled={loading} style={{ width: "100%", padding: 10 }}>
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>

      <p style={{ marginTop: 12, fontSize: 12, color: "#666" }}>

      </p>
      <p style={{ marginTop: 12 }}>
        No account?{" "}
        <button type="button" onClick={onGoRegister} style={{ textDecoration: "underline", background: "none", border: "none", cursor: "pointer" }}>
          Register
        </button>
      </p>
    </div>
  );
}