import { useEffect, useState } from "react";
import { api } from "../service/Api";

function RawMaterialForm({ material, onSuccess, onCancel }) {
  const [form, setForm] = useState({
    name: "",
    quantity: "",
    code: ""
  });

  useEffect(() => {
    if (material) {
      setForm(material);
    }
  }, [material]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (material?.id) {
      await api.put(`/raw-materials/${material.id}`, form);
    } else {
      await api.post("/raw-materials", form);
    }

    onSuccess();
    setForm({ name: "", quantity: "", code: "" });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>{material ? "Edit Raw Material" : "New Raw Material"}</h3>


        <input
        name="code"
        placeholder="Code"
        value={form.code}
        onChange={handleChange}
      />


      <input
        name="name"
        placeholder="Name"
        value={form.name}
        onChange={handleChange}
      />

      <input
        name="quantity"
        type="number"
        placeholder="Quantity"
        value={form.quantity}
        onChange={handleChange}
      />

     

      <button type="submit">Save</button>

      {material && (
        <button type="button" onClick={onCancel}>
          Cancel
        </button>
      )}
    </form>
  );
}

export default RawMaterialForm;
