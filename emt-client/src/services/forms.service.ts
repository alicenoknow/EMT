import axios from "axios";
import  FileDownload from 'js-file-download';


axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";
axios.defaults.headers.post["Content-Type"] = "application/json";

const ROOT_API = "http://localhost:8080/api/recruitment-form/";
const TEMPLATE_FORM_API = "template-form";
const SEND_FORM_API = "my-form";
const SEND_FORM_SCAN_API = "my-form/scan/";

export const getTemplate = (
): Promise<void> => {
	const tokenStr = JSON.parse(String(localStorage.getItem('user')))?.token;
	
	return axios
		.get(ROOT_API + TEMPLATE_FORM_API, { 
			headers: {
				"Authorization" : `Bearer ${tokenStr}`
			},
			responseType: 'blob' 
		})
		.catch(function (error) {
			console.log(error.toJSON());
			return undefined;
		})
		.then(response => {
			if (response?.data) {
				FileDownload(response.data, 'AnkietaRekrutacyjnaErasmus2022-wzor.pdf');
			}
		});
};

export const sendFilledPdf = (
	pdf:FormData
	): Promise<number> => {
		const tokenStr = JSON.parse(String(localStorage.getItem('user')))?.token;
		
		return axios
			.post(ROOT_API + SEND_FORM_API, {
				"pdf":pdf
			},{ 
				headers: {
					"Authorization" : `Bearer ${tokenStr}`,
					"Content-Type" : 'application/json'
				}
			})
			.catch(function (error) {
				console.log(error.toJSON());
				return undefined;
			})
			.then(response => {
				if (response?.data?.id) {
					return response?.data?.id
				}
				return undefined;
			});
	};
