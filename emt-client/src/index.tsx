import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Provider } from "react-redux";
import App from "./App";
import ScreenWrapper from "./components/ScreenWrapper";
import DocumentPage from "./pages/DocumentPage";
import UserPage from "./pages/UserPage";
import { store } from "./redux/store";
import UserLogin from "./pages/UserLogin";
import AdminPage from "./pages/AdminPage";

ReactDOM.render(
	<React.StrictMode>
		<Provider store={store}>
			<ScreenWrapper>
				<BrowserRouter>
					<Routes>
						<Route path="/" element={<App />} />
						<Route path="/login/user" element={<UserLogin />} />
						<Route path="/user" element={<UserPage />} />
						<Route path="/admin" element={<AdminPage />} />
						<Route
							path="/form"
							element={
								<DocumentPage
									isFilled={false}
									isAvailable={true}
									docLink="Link do Ankiety"
									docName="Ankieta Rekrutacyjna"
								/>
							}
						/>
					</Routes>
				</BrowserRouter>
			</ScreenWrapper>
		</Provider>
	</React.StrictMode>,
	document.getElementById("root"),
);
