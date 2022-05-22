import React, { useRef, useState } from "react";
import { Container, Button } from "react-bootstrap";
import { useLocation } from "react-router-dom";
import { getTemplate, sendFilledPdf } from "../services/forms.service";
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
							disabled={!uploadedFileName}>
							Prześlij dokument
						</Button>
					</div>
				</div>
			</Container>
		</div>
	);
}
