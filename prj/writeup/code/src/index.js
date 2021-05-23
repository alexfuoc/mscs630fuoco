import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import Input from "./input";
import Notes from "./Notes";

ReactDOM.render(
  <React.StrictMode>
    <App />
    <Input />
    <Notes />
  </React.StrictMode>,
  document.getElementById("root")
);
