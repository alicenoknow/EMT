import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import UserLogin from "./pages/UserLogin";
import RecruitSurveyForm from "./pages/RecruitSurveyForm";
import UserPage from "./pages/UserPage";
import AdminLogin from "./pages/AdminLogin";

ReactDOM.render(
	<React.StrictMode>
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<App />} />
				<Route path="/login/user" element={<UserLogin />} />
				<Route path="/login/admin" element={<AdminLogin />} />
				<Route path="/user" element={<UserPage firstName="Anna" lastName="Kowalska"/>} />
				<Route path="/form" element={<RecruitSurveyForm />} />
			</Routes>
		</BrowserRouter>
	</React.StrictMode>,
	document.getElementById("root"),
);
