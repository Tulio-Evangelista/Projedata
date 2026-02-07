import { useEffect, useState } from "react";
import { api } from "../service/Api";
import "./ProducibleProductsPage.css";

function ProducibleProductsPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [totalPages, setTotalPages] = useState(1);

  const loadProducts = async (pageNumber = 0) => {
    setLoading(true);
    try {
      const res = await api.get(`/production/calculate?page=${pageNumber}&size=${size}`);
      setProducts(res.data.content || []);
      setTotalPages(res.data.totalPages || 1);
    } catch (err) {
      console.error("Error fetching producible products:", err);
      setProducts([]);
      setTotalPages(1);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts(page);
  }, [page]);

  const handlePrev = () => { if (page > 0) setPage(page - 1); };
  const handleNext = () => { if (page < totalPages - 1) setPage(page + 1); };

  return (
    <div className="page-container">
      <h1>Producible Products</h1>

      {loading ? (
        <p>Loading producible products...</p>
      ) : products.length === 0 ? (
        <p>No producible products</p>
      ) : (
        <table className="styled-table">
          <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Quantity Producible</th>
              <th>Unit Price</th>
              <th>Total Price</th>
            </tr>
          </thead>
          <tbody>
            {products.map(p => (
              <tr key={p.productCode}>
                <td>{p.productCode}</td>
                <td>{p.productName}</td>
                <td>{p.quantity}</td>
                <td>{p.unitPrice.toFixed(2)}</td>
                <td>{(p.unitPrice * p.quantity).toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <div className="pagination">
        <button onClick={handlePrev} disabled={page === 0}>Previous</button>
        <span>Page {page + 1} of {totalPages}</span>
        <button onClick={handleNext} disabled={page >= totalPages - 1}>Next</button>
      </div>
    </div>
  );
}

export default ProducibleProductsPage;
