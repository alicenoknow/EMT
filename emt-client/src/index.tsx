import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Provider } from "react-redux";
import App from "./App";
import ScreenWrapper from './components/ScreenWrapper';
import RecruitSurveyForm from "./pages/RecruitSurveyForm";
import UserPage from "./pages/UserPage";
import { store } from "./redux/store";

ReactDOM.render(
	<React.StrictMode>
		<Provider store={store}>
			<ScreenWrapper>
				<BrowserRouter>
					<Routes>
					<Route path="/" element={<App />} />
					<Route path="/login/user" element={<UserLogin />} />
					<Route path="/login/admin" element={<AdminLogin />} />
					<Route path="/user" element={<UserPage firstName="Anna" lastName="Kowalska"/>} />
					<Route path="/form" element={<RecruitSurveyForm />} />
					</Routes>
				</BrowserRouter>
			</ScreenWrapper>
		</Provider>
	</React.StrictMode>,
	document.getElementById("root"),
);
