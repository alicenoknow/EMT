import React, { useRef, useState, useEffect } from "react";
import { Container, Button } from "react-bootstrap";
import { useLocation } from "react-router-dom";
import { getTemplate, sendFilledPdf, getSentPdf, getUserForm, getSentScan } from "../services/forms.service";
import "./DocumentPage.scss";

interface DocumentPageProps {
	isFilled: boolean;
	isAvailable: boolean;
	docName: string;
	docLink: string;
}

interface LocationParams {
	firstChoice: boolean;
}

export default function DocumentPage(props: DocumentPageProps) {
	const [isFilled, setFilled] = useState<boolean>(props.isFilled);
	const [isDownloadablePdf1, setDownloadablePdf1] = useState<boolean>(false);
	const [isDownloadablePdf2, setDownloadablePdf2] = useState<boolean>(false);
	const [isDownloadableScan1, setDownloadableScan1] = useState<boolean>(false);
	const [isDownloadableScan2, setDownloadableScan2] = useState<boolean>(false);
	const [uploadedFileName, setUploadedFileName] = useState<string | null>(null);
	const [uploadedFileNameScan, setUploadedFileNameScan] = useState<
		string | null
	>(null);
	const [uploadedFile, setUploadedFile] = useState<File | null>(null);
	const [uploadedFileScan, setUploadedFileScan] = useState<File | null>(null);
	const inputRef = useRef<HTMLInputElement>(null);
	const inputRef2 = useRef<HTMLInputElement>(null);
	const location = useLocation();

	const { isAvailable, docName } = props;

	useEffect(() => {
		fetchForm(1);
		fetchForm(2);
	}, []);

	const fetchForm = async (priority: number) => {
		const result = await getUserForm(priority);
		if(result && priority===1) {
			setDownloadablePdf1(true);
			if(result.oneDriveScanLink != "") {
				setDownloadableScan1(true);
			}
		} 
		else if(result && priority===2) {
			setDownloadablePdf2(true);
			if(result.oneDriveScanLink !== "") {
				setDownloadableScan1(true);
			}
		}

		if(!result && priority===1) {
			setDownloadablePdf1(false);
			setDownloadableScan1(false);
		} 
		else if(!result && priority===2){
			setDownloadablePdf2(false);
			setDownloadableScan2(false);
		}
	};

	const handleUpload = () => {
		inputRef.current?.click();
	};

	const handleDisplayFileDetails = () => {
		inputRef.current?.files &&
			setUploadedFileName(inputRef.current.files[0].name);
		inputRef.current?.files && setUploadedFile(inputRef.current.files[0]);
	};

	const handleUploadScan = () => {
		inputRef2.current?.click();
	};
	const handleDisplayFileDetails2 = () => {
		inputRef2.current?.files &&
			setUploadedFileNameScan(inputRef2.current.files[0].name);
		inputRef.current?.files && setUploadedFileScan(inputRef.current.files[0]);
	};

	const handleFileSend = async () => {
		const formData = new FormData();
		formData.append("pdf", uploadedFile ?? "");
		formData.append("isScan", "false");
		formData.append("id", "");
		formData.append(
			"priority",
			(location.state as LocationParams).firstChoice ? "1" : "2",
		);
		const id = await sendFilledPdf(formData);
		if (id) {
			const formData2 = new FormData();
			formData2.append("pdf", uploadedFile ?? "");
			formData2.append("isScan", "true");
			formData2.append("id", id.toString());
			formData2.append(
				"priority",
				(location.state as LocationParams).firstChoice ? "1" : "2",
			);
			sendFilledPdf(formData2);
		}
		setFilled(true);
	};

	const renderHeader = () => {
		return (
			<h1>{`${docName} - ${
				(location.state as LocationParams).firstChoice
					? "pierwszy wybór"
					: "drugi wybór"
			}`}</h1>
		);
	};

	const getPdfAvailabilityText = (priority: number) => {
		return ((isDownloadablePdf1 && priority===1) || (isDownloadablePdf2 && priority===2) ) ? (<text>✅</text>) : (<text>🚫</text>)	
	}

	const getScanAvailabilityText = (priority: number) => {
		return ((isDownloadableScan1 && priority===1) || (isDownloadableScan2 && priority===2) ) ? (<text>✅</text>) : (<text>🚫</text>)	
	}

	if (!isAvailable) {
		return <h3>Przykro mi, nie masz dostępu do tego dokumentu 🚫</h3>;
	} else if (isFilled) {
		return <h3>Dokument został wysłany 📄✅</h3>;
	}
	return (
		<div className="document">
			<Container fluid className="p-0">
				{renderHeader()}
				<div className="document__container">
					<h4>Pobierz dokument:</h4>
					<Button onClick={getTemplate} className={"download__button"}>
						Pobierz wzór
					</Button>

					<Button disabled={!((location.state as LocationParams).firstChoice ? true : false && isDownloadablePdf1) && !(
								(location.state as LocationParams).firstChoice ? false : true && isDownloadablePdf2)} onClick={() => getSentPdf((location.state as LocationParams).firstChoice ? 1 : 2)} className={"download__button"}>
						Pobierz wysłany pdf
					</Button>
					{getPdfAvailabilityText((location.state as LocationParams).firstChoice ? 1 : 2)}

					<Button disabled={!((location.state as LocationParams).firstChoice ? true : false && isDownloadableScan1) && !(
								(location.state as LocationParams).firstChoice ? false : true && isDownloadableScan2)} onClick={() => getSentScan((location.state as LocationParams).firstChoice ? 1 : 2)} className={"download__button"}>
						Pobierz wysłany skan
					</Button>
					{getScanAvailabilityText((location.state as LocationParams).firstChoice ? 1 : 2)}

					<div className="document__line" />
					<h4>Prześlij wypełniony dokument:</h4>
					<div className="m-3">
						<label className="mx-3">Wgraj wypełniony pdf:</label>
						<input
							ref={inputRef}
							onChange={handleDisplayFileDetails}
							className="d-none"
							type="file"
						/>
						<Button onClick={handleUpload} className="upload__button">
							{uploadedFileName ? uploadedFileName : "Wybierz"}
						</Button>
						<label className="mx-3">Wgraj podpisany skan:</label>
						<input
							ref={inputRef2}
							onChange={handleDisplayFileDetails2}
							className="d-none"
							type="file"
						/>
						<Button onClick={handleUploadScan} className="upload__button">
							{uploadedFileNameScan ? uploadedFileNameScan : "Wybierz"}
						</Button>
					</div>
					<div className="document__line" />
					<div className="send_container">
						<Button
							onClick={handleFileSend}
							className={"send__button"}
							disabled={(!uploadedFileName && ((location.state as LocationParams).firstChoice ? true : false || isDownloadablePdf1))}>
							Prześlij dokument
						</Button>
					</div>
				</div>
			</Container>
		</div>
	);
}
