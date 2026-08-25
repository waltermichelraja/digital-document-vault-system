import { useEffect, useState } from "react";
import api from "../api/axios";

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    api.get("/admin/dashboard")
      .then((res) => setStats(res.data))
      .catch((err) => setError(err.response?.data?.message || "failed to load dashboard."));
  }, []);

  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!stats) return <p>Loading...</p>;

  const cards = [
    { label: "Total Users", value: stats.totalUsers },
    { label: "Admins", value: stats.totalAdmins },
    { label: "Managers", value: stats.totalManagers },
    { label: "Employees", value: stats.totalEmployees },
    { label: "Total Documents", value: stats.totalDocuments },
  ];

  return (
    <div style={{ maxWidth: 800, margin: "0 auto" }}>
      <h2>Dashboard</h2>
      <div style={{ display: "flex", gap: 16, flexWrap: "wrap" }}>
        {cards.map((c) => (
          <div key={c.label} style={{ border: "1px solid #ccc", borderRadius: 6, padding: 16, minWidth: 140 }}>
            <div style={{ fontSize: 24, fontWeight: "bold" }}>{c.value}</div>
            <div>{c.label}</div>
          </div>
        ))}
      </div>
    </div>
  );
}