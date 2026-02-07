import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

export const deleteProduct = createAsyncThunk(
  "products/deleteProduct",
  async (id, { dispatch }) => {
    await axios.delete(`http://localhost:8080/products/${id}`);
    dispatch(fetchProducts()); // 🔥 atualiza o front
  }
);
export const updateProduct = createAsyncThunk(
  "products/updateProduct",
  async ({ id, data }, { dispatch }) => {
    await axios.put(`http://localhost:8080/products/${id}`, data);
    dispatch(fetchProducts()); // 🔥 atualiza o front
  }
);


export const fetchProducts = createAsyncThunk(
  "products/fetchProducts",
  async (page = 0) => {
    const response = await axios.get("http://localhost:8080/products", {
      params: { page }
    });

    return response.data; // 👈 CORRETO
  }
);

const productSlice = createSlice({
  name: "products",
  initialState: {
    items: [], // array de produtos
    status: "idle", // idle | loading | succeeded | failed
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        console.log("API RESPONSE:", action.payload);
        state.status = "succeeded";
        state.items = action.payload.content;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default productSlice.reducer;
