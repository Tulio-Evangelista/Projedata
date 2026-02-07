import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

// Estado inicial
const initialState = {
  items: [],      // lista de matérias-primas
  status: 'idle', // idle | loading | succeeded | failed
  error: null
};

// Thunk para buscar matérias-primas do backend
export const fetchRawMaterials = createAsyncThunk(
  'rawMaterials/fetchRawMaterials',
  async () => {
    const response = await fetch("http://localhost:8080/raw-materials"); // seu endpoint
    if (!response.ok) throw new Error("Failed to fetch raw materials");
    return await response.json();
  }
);

const rawMaterialSlice = createSlice({
  name: 'rawMaterials',
  initialState,
  reducers: {
    adjustStock(state, action) {
      // action.payload = { id, quantity }
      const { id, quantity } = action.payload;
      const rm = state.items.find(r => r.rawMaterialId === id);
      if (rm) rm.currentStock = quantity;
    }
  },
  extraReducers(builder) {
    builder
      .addCase(fetchRawMaterials.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchRawMaterials.fulfilled, (state, action) => {
        state.status = 'succeeded';
        // Garantindo que items sempre seja array
        state.items = Array.isArray(action.payload) ? action.payload : [];
      })
      .addCase(fetchRawMaterials.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message;
      });
  }
});

export const { adjustStock } = rawMaterialSlice.actions;

export default rawMaterialSlice.reducer;
