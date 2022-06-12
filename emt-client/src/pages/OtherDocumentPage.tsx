import React, { useRef, useState } from "react";
import { Container, Button } from "react-bootstrap";
import { sendOtherDoc } from "../services/forms.service";
import "./DocumentPage.scss";

export default function OtherDocumentPage() {
	const [uploadedFileName, setUploadedFileName] = useState<string | null>(null);
	const [uploadedFile, setUploadedFile] = useState<File | null>(null);
	const [isSent, setSent] = useState<boolean | undefined>(false);
	const inputRef = useRef<HTMLInputElement>(null);

	const handleUpload = () => {
		inputRef.current?.click();
	};

	const handleDisplayFileDetails = () => {
		if (!inputRef.current?.files) {
			return;
		}
		if (inputRef.current.files[0].name !== uploadedFileName) {
			setSent(false);
		}
		setUploadedFileName(inputRef.current.files[0].name);
		setUploadedFile(inputRef.current.files[0]);
	};

	const handleFileSend = async () => {
		const formData = new FormData();
		formData.append("name", uploadedFileName ?? "");
		formData.append("doc", uploadedFile ?? "");
		const result = await sendOtherDoc(formData);
		if (result) {
			setSent(true);
			return;
		}
		setSent(undefined);
	};

	const getSentConfirmation = () => {
		if (isSent === undefined) {
			return <> ❌Nie udało się przesłać dokumentu</>;
		} else if (isSent) {
			return <>✅</>;
		}
		return <></>;
	};

	return (
		<div className="document">
			<Container fluid className="p-0">
				<div className="document__container">
					<div className="document__line" />
					<h4>Prześlij dokument:</h4>
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
					</div>
					<div className="document__line" />
					<div className="send_container">
						<Button
							onClick={handleFileSend}
							className={"send__button"}
							disabled={!uploadedFileName}>
							Prześlij dokument
						</Button>
						{getSentConfirmation()}
					</div>
				</div>
			</Container>
		</div>
	);
}
