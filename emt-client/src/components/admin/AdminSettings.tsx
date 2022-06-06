import React, { useEffect, useRef, useState } from "react";
import { Button, Form, FormControl } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "./AdminSettings.scss";
import "react-datepicker/dist/react-datepicker.css";
import { getTemplate } from "../../services/forms.service";
import { createNewEdition, deleteEdition, getAllEditions, sendDefaultPdf, setCurrentEdition } from "../../services/admin.service";

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
  const [newEditionName, setNewEditionName] = useState<string>("");
  const [toDelEditionName, setToDelEditionName] = useState<string>("");
  const [newCurrentEditionName, setNewCurrentEditionName] = useState<string>("");

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

	const [editions, setEditions] = useState<string[]>([]);
  const getEditions = async () => {
    // const editionsList = await getAllEditions();
    // return editionsList.g;
    return getAllEditions().then(result=>{
      setEditions(result);
    });
	};

  const handleDeleteEdition = () => {
		deleteEdition(toDelEditionName);
    getEditions();
    console.log("dupa");
		return 0;
	};
  const handleCurrentEditionChange = () => {
		setCurrentEdition(newCurrentEditionName);
    getEditions();
		return 0;
	};
  const handleNewEdition = () => {
		createNewEdition(newEditionName);
    getEditions();
		return 0;
	};


  useEffect(() => {
		getEditions();
	}, []);

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
      <div className="document__line" />
      <h4>Ustawienia edycji</h4>
      <div className="m-3">
				<label className="mx-3">Wybierz edycję do usunięcia:</label>
        <select id='template-select'
        onChange={(event) => { setToDelEditionName(event.target.value) }}>
          <option>----</option>
          {editions.map(option => <option key={option} value={option}>{option}</option>)}
        </select>
				<Button
          onClick={handleDeleteEdition}
          className={"upload__button"}>
          Usuń
       </Button>
			</div>
      <div className="m-3">
				<label className="mx-3">Wybierz edycję do ustawienia:</label>
        <select id='template-select'
          onChange={(event) => { setNewCurrentEditionName(event.target.value) }} >
          {editions.map(option => <option key={option} value={option}>{option}</option>)}
        </select>
				<Button
          onClick={handleCurrentEditionChange}
          className={"send__button"}>
          Ustaw jak aktualną
        </Button>
			</div>
      <div className="m-3">
				<label className="mx-3">Dodaj nową edycję:</label>
        <FormControl type='text' onChange={(event) => { setNewEditionName(event.target.value) }} />
				<Button
          onClick={handleNewEdition}
          className={"send__button"}>
         Dodaj edycję
        </Button>
			</div>
		</div>
	);
}
