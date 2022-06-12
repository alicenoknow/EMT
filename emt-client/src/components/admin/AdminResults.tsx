import React, { useRef, useState } from "react";
import { Button, Alert } from "react-bootstrap";
import { downloadResultsDWZ, getResultsDWZ, sendFinalExcel } from "../../services/admin.service";
import "./AdminResults.scss";

interface AdminResultsProps {
	resultsLink: string;
}

export default function AdminResults(props: AdminResultsProps) {
	const [uploadedFileName, setUploadedFileName] = useState<string | null>(null);
	const [uploadedFile, setUploadedFile] = useState<File | null>(null);
	const [excelLink, setExcelLink] = useState<string | null>(null);
	const [resultsDWZLink, setResultsDWZLink] = useState<string | undefined>();
	const inputRef = useRef<HTMLInputElement>(null);

	const handleUpload = () => {
		inputRef.current?.click();
	};

	const handleDisplayFileDetails = () => {
		if (inputRef.current?.files) {
			setUploadedFileName(inputRef.current.files[0].name);
			setUploadedFile(inputRef.current.files[0]);
		}
	};

	const generateDoc = async () => {
		const result = await getResultsDWZ();
		setResultsDWZLink(result.oneDriveLink);
		downloadResultsDWZ();
	};

	const handleFileSend = async () => {
		const formData = new FormData();
		formData.append("excel", uploadedFile ?? "");
		const results = await sendFinalExcel(formData);
		if (results?.oneDriveLink) {
			setExcelLink(results.oneDriveLink);
		}
	};
	return (
		<div className="results">
			<h4>Wyniki rekrutacji:</h4>
			<a
				className="document__link"
				href={excelLink ?? props.resultsLink}
				target="_blank"
				rel="noreferrer">
				{"Pobierz plik (.xlsx)"}
			</a>
			<div className="document__line" />
			<h4>Prześlij końcowe wyniki rekrutacji:</h4>
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
				<Button
					onClick={handleFileSend}
					className={"send__button"}
					disabled={!uploadedFileName}>
					Prześlij
				</Button>
			</div>
			<div className="document__line" />
			<Alert variant="secondary">
				<Alert.Heading>Generowanie listy rankingowej</Alert.Heading>
				<div>
					Klikając poniższy przycisk możesz wygenerować listę rankingową.
					Domyślnie lista zostanie stworzona na podstawie danych zawartych w
					pliku z wynikami rekrutacji, który można pobrać powyżej.
					<hr />W przypadku konieczności wprowadzenia ręcznych zmian do listy,
					pobierz ją, zedytuj, a następnie załaduj w polu Wyniki końcowe, wtedy
					lista rankigowa zostanie wygenerowana na podstawie załadowanych
					danych.
				</div>
			</Alert>
			<Button onClick={generateDoc} className={"send__button"}>
				Generuj listę rankingową
			</Button>
		</div>
	);
}
