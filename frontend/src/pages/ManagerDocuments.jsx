import { useEffect, useState, useCallback } from "react";
import api from "../api/axios";

export default function ManagerDocuments() {
  const [docs, setDocs] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchDocs = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await api.get("/manager/documents", {
        params: { search: search || undefined, category: category || undefined, page, size: 10 },
      });
      setDocs(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      setError(err.response?.data?.message || "failed to load documents.");
    } finally {
      setLoading(false);
    }
  }, [search, category, page]);

  useEffect(() => {
    fetchDocs();
  }, [fetchDocs]);

  const handleDownload = async (doc) => {
    try {
      const res = await api.get(`/manager/documents/download/${doc.id}`, { responseType: "blob" });
      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", doc.originalFileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      setError("download failed.");
    }
  };

  return (
    <div style={{ maxWidth: 800, margin: "0 auto" }}>
      <h2>Team Documents</h2>

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
              <th>Title</th>
              <th>File</th>
              <th>Category</th>
              <th>Uploaded</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {docs.length === 0 && (
              <tr><td colSpan={5}>No documents found.</td></tr>
            )}
            {docs.map((doc) => (
              <tr key={doc.id} style={{ borderBottom: "1px solid #eee" }}>
                <td>{doc.documentTitle}</td>
                <td>{doc.originalFileName}</td>
                <td>{doc.category}</td>
                <td>{new Date(doc.uploadedAt).toLocaleDateString()}</td>
                <td><button onClick={() => handleDownload(doc)}>Download</button></td>
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