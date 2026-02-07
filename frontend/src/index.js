import React from "react";
import ReactDOM from "react-dom/client"; // se estiver usando React 18+
import { Provider } from "react-redux";
import { store } from "./redux/Store";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <App />
  </Provider>
);
