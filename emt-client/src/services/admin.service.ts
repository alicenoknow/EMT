import axios from "axios";
import FileDownload from "js-file-download";

axios.defaults.headers.get["Access-Control-Allow-Origin"] = "*";
axios.defaults.headers.get["Content-Type"] = "application/json";

const ROOT_API = "http://localhost:8080/api";
const RESULTS_API = "/excel-lists/results";
const RESULTS_API_DWZ = "/excel-lists/results-DWZ";
const RECRUITMENT_API = "/recruitment-form/form-list";
const RECRUITMENT_DEFAULT_API = "/recruitment-form/default";
const EDITIONS_API = "/parameter/editions";
const PARAMETERS_API = "/parameter";

export const getResults = () => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + RESULTS_API, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};

export const getFormList = () => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + RECRUITMENT_API, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};

export const getResultsDWZ = () => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + RESULTS_API_DWZ, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};

export const getCsvList = () => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + RESULTS_API, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};

export const downloadCsvList = (): Promise<void> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + RESULTS_API + "/download", {
			headers: {
				Authorization: `Bearer ${tokenStr}`,
			},
			responseType: "blob",
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data) {
				FileDownload(
					response.data,
					"TymczasowaListaRekrutacyjnaErasmus.csv",
				);
			}
		});
};

export const downloadResultsDWZ = (): Promise<void> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + RESULTS_API_DWZ + "/download", {
			headers: {
				Authorization: `Bearer ${tokenStr}`,
			},
			responseType: "blob",
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data) {
				FileDownload(
					response.data,
					"DWZ-list.xlsx",
				);
			}
		});
};

export const sendDefaultPdf = (pdf: FormData): Promise<number> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios({
		method: "post",
		url: ROOT_API + RECRUITMENT_DEFAULT_API,
		data: pdf,
		headers: {
			"Content-Type": "multipart/form-data",
			Authorization: `Bearer ${tokenStr}`,
		},
	})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data?.id) {
				return response?.data?.id;
			}
			return undefined;
		});
};

export const sendFinalExcel = (
	excel: FormData,
): Promise<{ oneDriveLink: string }> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios({
		method: "post",
		url: ROOT_API + RESULTS_API,
		data: excel,
		headers: {
			"Content-Type": "multipart/form-data",
			Authorization: `Bearer ${tokenStr}`,
		},
	})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data) {
				return response.data;
			}
			return undefined;
		});
};


export const getAllEditions = () => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + EDITIONS_API, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			console.log(response);
			console.log(response?.data);
			return response?.data
		});
};

export const deleteEdition = (edition:string) => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.delete(ROOT_API + EDITIONS_API + "/" + edition, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			console.log(response);
			console.log(response?.data);
			return response?.data
		});
};

export const createNewEdition = (edition:string) => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.post(ROOT_API + EDITIONS_API + "/" + edition, {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			console.log(response);
			console.log(response?.data);
			return response?.data
		});
};

export const setCurrentEdition = (edition:string) => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.post(ROOT_API + PARAMETERS_API, {
			headers: { Authorization: `Bearer ${tokenStr}` },
			body:{
				name:"editions",
				value:edition
			}
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			console.log(response);
			console.log(response?.data);
			return response?.data
		});
};
