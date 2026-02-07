import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { fetchRawMaterials } from "../redux/RawMaterialSlice";

const ProductRawMaterialPage = () => {
  const dispatch = useDispatch();

  // Hooks dentro do componente
  const rawMaterials = useSelector(state => state.rawMaterials.items || []);
  const status = useSelector(state => state.rawMaterials.status);
  const error = useSelector(state => state.rawMaterials.error);

  useEffect(() => {
    dispatch(fetchRawMaterials());
  }, [dispatch]);

  if (status === "loading") return <p>Loading raw materials...</p>;
  if (status === "failed") return <p>Error: {error}</p>;

  return (
    <div>
      <h1>Raw Materials</h1>
      {Array.isArray(rawMaterials) && rawMaterials.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Stock</th>
            </tr>
          </thead>
          <tbody>
            {rawMaterials.map((rm) => (
              <tr key={rm.rawMaterialId}>
                <td>{rm.rawMaterialCode}</td>
                <td>{rm.rawMaterialName}</td>
                <td>{rm.currentStock}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No raw materials found</p>
      )}
    </div>
  );
};

export default ProductRawMaterialPage;
