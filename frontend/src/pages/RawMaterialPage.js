import { useEffect, useState } from "react";
import { api } from "../service/Api";
import RawMaterialForm from "../components/RawMaterialForm";
import "./RawMaterialPage.css";

function RawMaterialPage() {
  const [materials, setMaterials] = useState([]);
  const [editing, setEditing] = useState(null);

  const loadMaterials = () => {
    api.get("/raw-materials")
      .then(res => setMaterials(res.data.content || []))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadMaterials();
  }, []);

  const handleDelete = async (id) => {
    if (!window.confirm("Delete raw material?")) return;
    try {
      await api.delete(`/raw-materials/${id}`);
      loadMaterials();
    } catch (err) {
      console.error("Error deleting raw material", err);
    }
  };

  return (
    <div className="page-container">
      <h1>Raw Materials</h1>

      <RawMaterialForm
        material={editing}
        onSuccess={() => {
          setEditing(null);
          loadMaterials();
        }}
        onCancel={() => setEditing(null)}
      />

      <table className="styled-table">
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Quantity</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {materials.map(m => (
            <tr key={m.id}>
              <td>{m.code}</td>
              <td>{m.name}</td>
              <td>{m.quantity}</td>
              <td>
                <button onClick={() => setEditing(m)}>Edit</button>
                <button onClick={() => handleDelete(m.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default RawMaterialPage;
