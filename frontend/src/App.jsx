import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Documents from "./pages/Documents";
import ProtectedRoute from "./components/ProtectedRoute";
import { useAuth } from "./context/AuthContext";
import { Link } from "react-router-dom";

function Dashboard() {
  const { user, logout } = useAuth();
  return (
    <div style={{ maxWidth: 600, margin: "80px auto" }}>
      <h2>Welcome{user?.name ? `, ${user.name}` : ""}</h2>
      <p><Link to="/documents">Go to my documents</Link></p>
      <button onClick={logout}>Logout</button>
    </div>
  );
}

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/documents"
        element={
          <ProtectedRoute>
            <Documents />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}