import React, { useRef, useState } from "react";
import { Container, Button } from "react-bootstrap";
import { getTemplate } from "../services/forms.service";
import "./DocumentPage.scss";

interface DocumentPageProps {
	isFilled: boolean;
	isAvailable: boolean;
	docName: string;
	docLink: string;
}

export default function DocumentPage(props: DocumentPageProps) {
	const [isFilled, setFilled] = useState<boolean>(props.isFilled);
	const [uploadedFileName, setUploadedFileName] = useState<string | null>(null);
	const [uploadedFileName2, setUploadedFileName2] = useState<string | null>(null);
	const inputRef = useRef<HTMLInputElement>(null);
	const inputRef2 = useRef<HTMLInputElement>(null);

	const { isAvailable, docName, docLink } = props;

	const handleUpload = () => {
		inputRef.current?.click();
	};
	const handleDisplayFileDetails = () => {
		inputRef.current?.files &&
			setUploadedFileName(inputRef.current.files[0].name);
	};

	// this is definitely not a copy paste
	const handleUpload2 = () => {
		inputRef2.current?.click();
	};
	const handleDisplayFileDetails2 = () => {
		inputRef2.current?.files &&
			setUploadedFileName2(inputRef2.current.files[0].name);
	};
	// -----------------------------------

	const handleFileSend = () => {
		setFilled(true);
	};

	if (!isAvailable) {
		return <h3>Przykro mi, nie masz dostępu do tego dokumentu 🚫</h3>;
	} else if (isFilled) {
		return <h3>Dokument został wypełniony 📄✅</h3>;
	}
	return (
		<div className="document">
			<Container fluid className="p-0">
				<h1>{docName}</h1>
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
						<Button
							onClick={handleUpload}
							className="upload__button">
							{uploadedFileName ? uploadedFileName : "Wybierz"}
						</Button>
						<label className="mx-3">Wgraj podpisany skan:</label>
						<input
							ref={inputRef2}
							onChange={handleDisplayFileDetails2}
							className="d-none"
							type="file"
						/>
						<Button
							onClick={handleUpload2}
							className="upload__button">
							{uploadedFileName2 ? uploadedFileName2 : "Wybierz"}
						</Button>
					</div>
                    <div className="document__line" />
                    <div className="send_container">
					<Button onClick={handleFileSend} className={"send__button"} disabled={!uploadedFileName || !uploadedFileName2}>
						Prześlij dokumenty
					</Button>
                    </div>
				</div>
			</Container>
		</div>
	);
}
