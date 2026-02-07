import { useEffect, useState } from "react";
import { api } from "../service/Api";

function ProductForm({ product, onSuccess, onCancel }) {
  const [form, setForm] = useState({
    code: "",
    name: "",
    price: ""
  });

  useEffect(() => {
    if (product) {
      setForm({
        code: product.code || "",
        name: product.name || "",
        price: product.price || ""
      });
    }
  }, [product]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (product?.id) {
        // EDIT
        await api.put(`/products/${product.id}`, form);
      } else {
        // CREATE
        await api.post("/products", form);
      }

      onSuccess();
      setForm({ code: "", name: "", price: "" });
    } catch (error) {
      console.error("Error saving product:", error);
      alert("Error saving product");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>{product ? "Edit Product" : "New Product"}</h3>

      <input
        name="code"
        placeholder="Code"
        value={form.code}
        onChange={handleChange}
        required
      />

      <input
        name="name"
        placeholder="Name"
        value={form.name}
        onChange={handleChange}
        required
      />

      <input
        name="price"
        type="number"
        step="0.01"
        placeholder="Price"
        value={form.price}
        onChange={handleChange}
        required
      />

      <button type="submit">
        {product ? "Update" : "Create"}
      </button>

      {product && (
        <button type="button" onClick={onCancel}>
          Cancel
        </button>
      )}
    </form>
  );
}

export default ProductForm;
