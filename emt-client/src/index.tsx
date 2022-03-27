import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import Login from "./pages/Login";
import RecruitSurveyForm from "./pages/RecruitSurveyForm";
import UserPage from "./pages/UserPage";

ReactDOM.render(
	<React.StrictMode>
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<App />} />
				<Route path="/login" element={<Login />} />
				<Route path="/user" element={<UserPage firstName="Anna" lastName="Kowalska"/>} />
				<Route path="/form" element={<RecruitSurveyForm />} />
			</Routes>
		</BrowserRouter>
	</React.StrictMode>,
	document.getElementById("root"),
);
