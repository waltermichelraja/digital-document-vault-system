import { Outlet, Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Layout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div>
      <nav style={{ display: "flex", gap: 16, padding: 16, borderBottom: "1px solid #ccc", alignItems: "center" }}>
        <Link to="/documents">My Documents</Link>
        {(user?.role === "MANAGER" || user?.role === "ADMIN") && (
          <Link to="/manager/documents">Team Documents</Link>
        )}
        {user?.role === "ADMIN" && (
          <>
            <Link to="/admin/documents">All Documents</Link>
            <Link to="/admin/users">Users</Link>
            <Link to="/admin/dashboard">Dashboard</Link>
          </>
        )}
        <span style={{ marginLeft: "auto" }}>{user?.fullName} ({user?.role})</span>
        <button onClick={handleLogout}>Logout</button>
      </nav>
      <div style={{ padding: 24 }}>
        <Outlet />
      </div>
    </div>
  );
}