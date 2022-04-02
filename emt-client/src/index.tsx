import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import Login from "./pages/Login";
import ScreenWrapper from './components/ScreenWrapper';
import RecruitSurveyForm from "./pages/RecruitSurveyForm";
import UserPage from "./pages/UserPage";

ReactDOM.render(
	<React.StrictMode>
		<ScreenWrapper>
			<BrowserRouter>
				<Routes>
					<Route path="/" element={<App />} />
					<Route path="/login" element={<Login />} />
					<Route path="/user" element={<UserPage firstName="Anna" lastName="Kowalska"/>} />
					<Route path="/form" element={<RecruitSurveyForm />} />
				</Routes>
			</BrowserRouter>
		</ScreenWrapper>
	</React.StrictMode>,
	document.getElementById("root"),
);
