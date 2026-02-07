import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import ProductPage from "./pages/ProductPage";
import RawMaterialPage from "./pages/RawMaterialPage";
import ProducibleProductsPage from "./pages/ProducibleProductsPage";
import "./App.css";

function App() {
  return (
    <Router>
      <div className="app-menu">
        <Link to="/products">Products</Link>
        <Link to="/raw-materials">Raw Materials</Link>
        <Link to="/producible-products">Producible Products</Link>
      </div>

      <Routes>
        <Route path="/products" element={<ProductPage />} />
        <Route path="/raw-materials" element={<RawMaterialPage />} />
        <Route path="/producible-products" element={<ProducibleProductsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
