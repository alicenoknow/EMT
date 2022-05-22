import React, { useRef, useState } from "react";
import { Container, Button } from "react-bootstrap";
import { useLocation } from "react-router-dom";
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
	const inputRef = useRef<HTMLInputElement>(null);
	const location = useLocation();

	const { isAvailable, docName, docLink } = props;

	const handleUpload = () => {
		inputRef.current?.click();
	};
	const handleDisplayFileDetails = () => {
		inputRef.current?.files &&
			setUploadedFileName(inputRef.current.files[0].name);
	};
	const handleFileSend = () => {
		setFilled(true);
		// TODO: based on firstChoice and file, send form
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
		return <h3>Dokument został wypełniony 📄✅</h3>;
	}
	return (
		<div className="document">
			<Container fluid className="p-0">
				{renderHeader()}
				<div className="document__container">
					<h4>Pobierz dokument:</h4>
					<a className="document__link" href={docLink}>
						{docName}
					</a>
					<div className="document__line" />
					<h4>Prześlij wypełniony dokument:</h4>
					<div className="m-3">
						<label className="mx-3">Wgraj plik:</label>
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
					</div>
				</div>
			</Container>
		</div>
	);
}
