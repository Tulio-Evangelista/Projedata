import { useEffect, useState } from "react";
import { api } from "../service/Api";
import ProductForm from "../components/ProductForm";
import "./ProductPage.css";
import initialData from "../initialData.json";

function ProductPage() {
  const [products, setProducts] = useState([]);
  const [editingProduct, setEditingProduct] = useState(null);
  const [productMaterials, setProductMaterials] = useState([]);
  const [selectedRawMaterial, setSelectedRawMaterial] = useState("");
  const [requiredQuantity, setRequiredQuantity] = useState("");
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [rawMaterials, setRawMaterials] = useState([]);
  const [productionList, setProductionList] = useState([]);

  const loadProducts = () => {
    api.get("/products")
      .then(res => {
        const data = res.data.content || [];
        setProducts(data.length > 0 ? data : initialData.products);
      })
      .catch(() => setProducts(initialData.products));
  };

  const loadRawMaterials = () => {
    api.get("/raw-materials")
      .then(res => {
        const data = res.data.content || res.data;
        setRawMaterials(data.length > 0 ? data : initialData.rawMaterials);
      })
      .catch(() => setRawMaterials(initialData.rawMaterials));
  };

  const loadProductMaterials = (productId) => {
    api.get(`/product-raw-materials/${productId}/raw-materials`)
      .then(res => setProductMaterials(res.data))
      .catch(err => console.error(err));
  };

  const loadMaxProduction = () => {
    api.get("/production/calculate", { params: { page: 0, size: 100 } })
      .then(res => {
        const data = res.data.content || res.data;
        setProductionList(data);
      })
      .catch(err => console.error("Error fetching production:", err));
  };

  useEffect(() => {
    loadProducts();
    loadRawMaterials();
  }, []);

  useEffect(() => {
    if (products.length > 0) loadMaxProduction();
  }, [products, rawMaterials]);

  const handleAddRawMaterial = () => {
    if (!selectedProduct || !selectedRawMaterial || !requiredQuantity) {
      alert("Fill all fields");
      return;
    }

    api.post(`/product-raw-materials/${selectedProduct.id}/add`, {
      rawMaterialId: selectedRawMaterial,
      requiredQuantity: Number(requiredQuantity)
    })
      .then(() => {
        setSelectedRawMaterial("");
        setRequiredQuantity("");
        loadProductMaterials(selectedProduct.id);
        loadMaxProduction();
      })
      .catch(err => console.error(err));
  };

  const handleRemoveRawMaterial = (associationId) => {
    api.delete(`/product-raw-materials/${associationId}`)
      .then(() => {
        loadProductMaterials(selectedProduct.id);
        loadMaxProduction();
      })
      .catch(err => console.error("Error removing raw material:", err));
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this product?")) return;
    try {
      await api.delete(`/products/${id}`);
      loadProducts();
    } catch (error) {
      console.error("Error deleting product", error);
    }
  };

  return (
    <div className="product-page-container">
      <h1>Products</h1>

      <div className="product-form-container">
        <ProductForm
          product={editingProduct}
          onSuccess={() => {
            setEditingProduct(null);
            loadProducts();
          }}
          onCancel={() => setEditingProduct(null)}
        />
      </div>

      {selectedProduct && (
        <div className="raw-materials-card">
          <h3>Raw Materials for {selectedProduct.name}</h3>

          <div className="raw-materials-form">
            <select
              value={selectedRawMaterial}
              onChange={e => setSelectedRawMaterial(e.target.value)}
            >
              <option value="">Select raw material</option>
              {rawMaterials.map(rm => (
                <option key={rm.id} value={rm.id}>{rm.name}</option>
              ))}
            </select>

            <input
              type="number"
              placeholder="Required quantity"
              value={requiredQuantity}
              onChange={e => setRequiredQuantity(e.target.value)}
            />

            <button className="action-btn" onClick={handleAddRawMaterial}>Add</button>
          </div>

          <ul className="raw-materials-list">
            {productMaterials.map(pm => (
              <li key={pm.id}>
                {pm.rawMaterialName} – {pm.requiredQuantity}
                <button className="remove-btn" onClick={() => handleRemoveRawMaterial(pm.id)}>Remove</button>
              </li>
            ))}
          </ul>
        </div>
      )}

      <table className="products-table card">
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Price</th>
            <th>Max Quantity</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map(p => {
            const prodInfo = productionList.find(pr => pr.productCode === p.code) || {};
            return (
              <tr key={p.id}>
                <td>{p.code}</td>
                <td>{p.name}</td>
                <td>{p.price}</td>
                <td>{prodInfo.quantity || 0}</td>
                <td className="actions-cell">
                  <button className="action-btn" onClick={() => setEditingProduct(p)}>Edit</button>
                  <button className="delete-btn" onClick={() => handleDelete(p.id)}>Delete</button>
                  <button className="action-btn" onClick={() => {
                    setSelectedProduct(p);
                    loadProductMaterials(p.id);
                  }}>Raw Materials</button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default ProductPage;
