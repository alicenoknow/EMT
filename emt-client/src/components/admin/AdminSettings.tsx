import React, { useRef, useState } from "react";
import { Button } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "./AdminSettings.scss";
import "react-datepicker/dist/react-datepicker.css";
import { getTemplate } from "../../services/forms.service";
import { sendDefaultPdf } from "../../services/admin.service";
import { TextField } from "@material-ui/core";
import { setParam } from "../../services/params.service";
import Loading from "../Loading";

interface AdminSettingsProps {
	config?: {
		startDate?: Date;
		endDate?: Date;
		defaultPDFLink?: string;
		formsNum?: number;
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
	const [formsNum, setFormsNum] = useState<number>(
		props?.config?.formsNum ?? 2,
	);
	const [confirm, setConfirmLoading] = useState<{
		date: boolean;
		forms: boolean;
		doc: boolean;
	}>({ date: false, forms: false, doc: false });

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
		setConfirmLoading({ ...confirm, doc: false });
		await sendDefaultPdf(formData);
		setTimeout(() => setConfirmLoading({ ...confirm, doc: true }), 500);
	};

	const convertDateToString = (date: Date): string => {
		return `${date.getFullYear()}-${String(date.getMonth()).padStart(
			2,
			"0",
		)}-${String(date.getDay()).padStart(2, "0")}`;
	};

	const handleDateChange = async () => {
		if (startDate && endDate) {
			setConfirmLoading({ ...confirm, date: false });
			await setParam("RECRUITMENT_START_DATE", convertDateToString(startDate));
			await setParam("RECRUITMENT_END_DATE", convertDateToString(endDate));
			setTimeout(() => setConfirmLoading({ ...confirm, date: true }), 500);
		}
	};

	const handleFormsNumChange = async () => {
		if (formsNum) {
			setConfirmLoading({ ...confirm, forms: false });
			await setParam("MAX_RECUITMENT_FORMS_PER_STUDENT", formsNum?.toString());
			setTimeout(() => setConfirmLoading({ ...confirm, forms: true }), 500);
		}
	};

	return (
		<div className="settings">
			<div className="forms_input">
				<h4>Zmień dostepną liczbę ankiet dla studenta:</h4>
				<TextField
					id="outlined-number"
					label="Liczba ankiet"
					type="number"
					value={formsNum}
					onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
						const val = parseInt(e.currentTarget.value);
						if (val >= 0) {
							setFormsNum(val);
						}
					}}
					InputLabelProps={{
						shrink: true,
					}}
				/>
				<Button
					onClick={handleFormsNumChange}
					className={"send__button"}
					disabled={!formsNum}>
					Zatwierdź
				</Button>
				{confirm.forms && <>✅</>}
			</div>
			<div className="document__line" />
			<div className="data__container">
				<h4>Zmień datę rozpoczęcia rekrutacji:</h4>
				<DatePicker onChange={setStartDate} selected={startDate} />
			</div>
			<div className="data__container">
				<h4>Zmień datę zakończenia rekrutacji:</h4>
				<DatePicker onChange={setEndDate} selected={endDate} />
			</div>
			<Button onClick={handleDateChange} className={"send__button"}>
				Zatwierdź
			</Button>
			{confirm.date && <>✅</>}
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
				{confirm.doc && <>✅</>}
			</div>
		</div>
	);
}
