import React, { useRef, useState } from "react";
import { Button } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "./AdminSettings.scss";
import "react-datepicker/dist/react-datepicker.css";
import { getTemplate } from "../../services/forms.service";
import { sendDefaultPdf } from "../../services/admin.service";

interface AdminSettingsProps {
	config?: {
		startDate?: Date;
		endDate?: Date;
		defaultPDFLink?: string;
	};
}

type MaybeDate = Date | undefined | null;

export default function AdminSettings(props: AdminSettingsProps) {
	const [startDate, setStartDate] = useState<MaybeDate>(
		props?.config?.startDate,
	);
	const [endDate, setEndDate] = useState<MaybeDate>(props?.config?.endDate);
	const [uploadedFileName, setUploadedFileName] = useState<string | null>(null);
	const [uploadedFile, setUploadedFile] = useState<File | null>(null);

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

	const handleFileSend = async () => {
		const formData = new FormData();
		formData.append("pdf", uploadedFile ?? "");
		await sendDefaultPdf(formData);
	};

	const handleDateChange = () => {
		// TODO handle date update
		return 0;
	};

	return (
		<div className="settings">
			<div className="data__container">
				<h4>Zmień datę rozpoczęcia rekrutacji:</h4>
				<DatePicker onChange={setStartDate} selected={startDate} />
			</div>
			<div className="data__container">
				<h4>Zmień datę zakończenia rekrutacji:</h4>
				<DatePicker onChange={setEndDate} selected={endDate} />
			</div>
			<Button
				onClick={handleDateChange}
				className={"send__button"}
				disabled={!!endDate && !!startDate}>
				Zatwierdź
			</Button>
			<div className="document__line" />
			<h4>Pobierz dokument:</h4>
			<Button onClick={getTemplate} className={"upload__button"}>
				Pobierz aktualny wzorzec
			</Button>
			<h4>Prześlij wzorzec ankiety rekrutacyjnej:</h4>
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
					Prześlij dokument
				</Button>
			</div>
		</div>
	);
}
