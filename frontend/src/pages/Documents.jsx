import { useEffect, useState, useCallback } from "react";
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";
import { Link } from "react-router-dom";

export default function Documents() {
  const { logout } = useAuth();
  const [docs, setDocs] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [file, setFile] = useState(null);
  const [uploadCategory, setUploadCategory] = useState("");
  const [uploading, setUploading] = useState(false);

  const fetchDocs = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await api.get("/documents", {
        params: { search: search || undefined, category: category || undefined, page, size: 10 },
      });
      setDocs(res.data.content ?? res.data);
      setTotalPages(res.data.totalPages ?? 1);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load documents");
    } finally {
      setLoading(false);
    }
  }, [search, category, page]);

  useEffect(() => {
    fetchDocs();
  }, [fetchDocs]);

  const handleUpload = async (e) => {
    e.preventDefault();
    if (!file) return;
    setUploading(true);
    setError(null);
    try {
      const formData = new FormData();
      formData.append("file", file);
      if (uploadCategory) formData.append("category", uploadCategory);

      await api.post("/documents/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      setFile(null);
      setUploadCategory("");
      fetchDocs();
    } catch (err) {
      setError(err.response?.data?.message || "Upload failed");
    } finally {
      setUploading(false);
    }
  };

  const handleDownload = async (doc) => {
    try {
      const res = await api.get(`/documents/${doc.id}`, { responseType: "blob" });
      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", doc.filename || doc.name || "document");
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      setError("Download failed");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this document?")) return;
    try {
      await api.delete(`/documents/${id}`);
      fetchDocs();
    } catch (err) {
      setError("Delete failed");
    }
  };

  return (
    <div style={{ maxWidth: 700, margin: "40px auto" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <h2>My Documents</h2>
        <div>
          <Link to="/" style={{ marginRight: 12 }}>Home</Link>
          <button onClick={logout}>Logout</button>
        </div>
      </div>

      <form onSubmit={handleUpload} style={{ margin: "20px 0", display: "flex", gap: 8 }}>
        <input
          type="file"
          onChange={(e) => setFile(e.target.files[0])}
          required
        />
        <input
          type="text"
          placeholder="Category (optional)"
          value={uploadCategory}
          onChange={(e) => setUploadCategory(e.target.value)}
        />
        <button type="submit" disabled={uploading}>
          {uploading ? "Uploading..." : "Upload"}
        </button>
      </form>

      <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
        <input
          type="text"
          placeholder="Search"
          value={search}
          onChange={(e) => { setPage(0); setSearch(e.target.value); }}
        />
        <input
          type="text"
          placeholder="Category filter"
          value={category}
          onChange={(e) => { setPage(0); setCategory(e.target.value); }}
        />
      </div>

      {error && <p style={{ color: "red" }}>{error}</p>}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <table width="100%" cellPadding="6" style={{ borderCollapse: "collapse" }}>
          <thead>
            <tr style={{ textAlign: "left", borderBottom: "1px solid #ccc" }}>
              <th>Name</th>
              <th>Category</th>
              <th>Uploaded</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {docs.length === 0 && (
              <tr><td colSpan={4}>No documents yet.</td></tr>
            )}
            {docs.map((doc) => (
              <tr key={doc.id} style={{ borderBottom: "1px solid #eee" }}>
                <td>{doc.filename || doc.name}</td>
                <td>{doc.category || "-"}</td>
                <td>{doc.uploadedAt ? new Date(doc.uploadedAt).toLocaleDateString() : "-"}</td>
                <td>
                  <button onClick={() => handleDownload(doc)} style={{ marginRight: 6 }}>
                    Download
                  </button>
                  <button onClick={() => handleDelete(doc.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {totalPages > 1 && (
        <div style={{ marginTop: 12 }}>
          <button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>Prev</button>
          <span style={{ margin: "0 10px" }}>Page {page + 1} of {totalPages}</span>
          <button disabled={page + 1 >= totalPages} onClick={() => setPage((p) => p + 1)}>Next</button>
        </div>
      )}
    </div>
  );
}