import axios from "axios";

const ROOT_API = "nasze-api.pl";
const USER_LOGIN_API = "/user_login";
const USER_REGISTER_API = "/user_register";

const ADMIN_LOGIN_API = "/admin_login";
const ADMIN_REGISTER_API = "/admin_register";

export const registerUser = (email: string, password: string) => {
	return axios.post(ROOT_API + USER_REGISTER_API, {
		email,
		password,
	});
};

export const registerAdmin = (email: string, password: string) => {
	return axios.post(ROOT_API + ADMIN_REGISTER_API, {
		email,
		password,
	});
};

export const loginUser = (email: string, password: string) => {
	return axios
		.post(ROOT_API + USER_LOGIN_API, {
			email,
			password,
		})
		.then(response => {
			if (response.data.accessToken) {
				localStorage.setItem("user", JSON.stringify(response.data));
			}
			return response.data;
		});
};

export const loginAdmin = (email: string, password: string) => {
	return axios
		.post(ROOT_API + ADMIN_LOGIN_API, {
			email,
			password,
		})
		.then(response => {
			if (response.data.accessToken) {
				localStorage.setItem("user", JSON.stringify(response.data));
			}
			return response.data;
		});
};

export const logoutUser = () => {
	localStorage.removeItem("user");
};

export const logoutAdmin = () => {
	localStorage.removeItem("user");
};
