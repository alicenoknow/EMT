import axios from "axios";
import FileDownload from "js-file-download";

axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";
axios.defaults.headers.post["Content-Type"] = "application/json";

const ROOT_API = "http://localhost:8080/api/recruitment-form/";
const TEMPLATE_FORM_API = "default";
const SEND_FORM_API = "my-form";
const SEND_DOC_API = "other-docs";
const GET_PDF_API = "my-form/download/";
const GET_SCAN_API = "my-form/scan/download/";

export const getTemplate = (): Promise<void> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + TEMPLATE_FORM_API, {
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
				FileDownload(response.data, "AnkietaRekrutacyjnaErasmus2022-wzor.pdf");
			}
		});
};

export const sendFilledPdf = (pdf: FormData): Promise<number> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios({
		method: "post",
		url: ROOT_API + SEND_FORM_API,
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

export const getSentPdf = (priority: number): Promise<void> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + GET_PDF_API + priority.toString(), {
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
					"AnkietaRekrutacyjnaErasmus2022-pr-" + priority.toString() + ".pdf",
				);
			}
		});
};

export const getSentScan = (priority: number): Promise<void> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios
		.get(ROOT_API + GET_SCAN_API + priority.toString(), {
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
					"AnkietaRekrutacyjnaErasmus2022-skan-pr" +
						priority.toString() +
						".pdf",
				);
			}
		});
};

export const getUserForm = (priority: number) => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;
	return axios
		.get(ROOT_API + SEND_FORM_API + "/" + priority.toString(), {
			headers: { Authorization: `Bearer ${tokenStr}` },
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => response?.data);
};

export const sendOtherDoc = (pdf: FormData): Promise<number> => {
	const tokenStr = JSON.parse(String(localStorage.getItem("user")))?.token;

	return axios({
		method: "post",
		url: ROOT_API + SEND_DOC_API,
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
			if (response?.data) {
				return response?.data;
			}
			return undefined;
		});
};
