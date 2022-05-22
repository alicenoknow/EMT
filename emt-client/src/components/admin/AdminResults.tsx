import React, { useRef, useState } from "react";
import { Button, Alert } from "react-bootstrap";
import "./AdminResults.scss";

export default function AdminResults() {
	const [uploadedFileName, setUploadedFileName] = useState<string | null>(null);
	const inputRef = useRef<HTMLInputElement>(null);

	const handleUpload = () => {
		inputRef.current?.click();
	};
	const handleDisplayFileDetails = () => {
		inputRef.current?.files &&
			setUploadedFileName(inputRef.current.files[0].name);
	};
	const handleFileSend = () => {
		// TODO final results upload
		return 0;
	};

	return (
		<div className="results">
			<h4>Wyniki rekrutacji:</h4>
			<a className="document__link" href={""}>
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
			</div>
			<div className="document__line" />
			<Alert variant="secondary ">
				<Alert.Heading>Generowanie listy rankingowej</Alert.Heading>
				<p>
					Klikając poniższy przycisk możesz wygenerować listę rankingową.
					Domyślnie lista zostanie stworzona na podstawie danych zawartych w
					pliku z wynikami rekrutacji, który można pobrać powyżej.
					<hr />W przypadku konieczności wprowadzenia ręcznych zmian do listy,
					pobierz ją, zedytuj, a następnie załaduj w polu Wyniki końcowe, wtedy
					lista rankigowa zostanie wygenerowana na podstawie załadowanych
					danych.
				</p>
			</Alert>
			<Button onClick={handleFileSend} className={"send__button"}>
				Generuj listę rankingową
			</Button>
		</div>
	);
}
