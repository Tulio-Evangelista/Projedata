import { configureStore } from "@reduxjs/toolkit";
import productReducer from "./ProduceSlice";
import rawMaterialReducer from "./RawMaterialSlice";

export const store = configureStore({
  reducer: {
    products: productReducer,
    rawMaterials: rawMaterialReducer
  }
});
