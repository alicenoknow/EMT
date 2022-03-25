import React from "react";
import "./App.scss";
import Home from "./pages/Home";

export default function App() {
	return (
		<div className="app">
			<Home helloMessage="Elo" />
		</div>
	);
}
