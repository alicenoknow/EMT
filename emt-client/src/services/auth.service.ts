import axios from "axios";
import { LoginResponse } from "./types";

axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";
axios.defaults.headers.post["Content-Type"] = "application/json";

const ROOT_API = "http://localhost:8080/api";
const LOGIN_API = "/auth/login";
const REGISTER_API = "/auth/signup";

export const register = (email: string, password: string) => {
	return axios
		.post(ROOT_API + REGISTER_API, {
			email,
			password,
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data?.token) {
				localStorage.setItem("user", JSON.stringify(response.data));
			}
			return response?.status;
		});
};

export const login = (
	email: string,
	password: string,
): Promise<LoginResponse> => {
	return axios
		.post(ROOT_API + LOGIN_API, {
			email,
			password,
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data?.token) {
				localStorage.setItem("user", JSON.stringify(response.data));
			}
			return response?.data;
		});
};

export const logout = () => {
	localStorage.removeItem("user");
};

export const getAuthHeader = () => {
	const user = JSON.parse(localStorage.getItem("user") ?? "{}");
	if (user && user.token) {
		return { Authorization: "Bearer " + user.token };
	} else {
		return {};
	}
};

export const getUserCredentials = () => {
	const user = JSON.parse(localStorage.getItem("user") ?? "{}");
	return user?.email;
};
