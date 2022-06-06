import axios from "axios";

axios.defaults.headers.get["Access-Control-Allow-Origin"] = "*";
axios.defaults.headers.get["Content-Type"] = "application/json";

const ROOT_API = "http://localhost:8080/api";
const PARAMS_API = "/parameter";

export const getParam = (key: string) => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + PARAMS_API + "/" + key, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};

export const setParam = (key: string, value: string, description?: string) => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;
	return axios
		.post(
			ROOT_API + PARAMS_API,
			{
				name: key,
				value: value,
				description: description ?? "",
			},
			{
				headers: { Authorization: `Bearer ${tokenStr}` },
			},
		)
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};
