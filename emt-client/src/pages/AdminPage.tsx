import React, { useEffect, useState, SyntheticEvent } from "react";
import { Tabs, Tab, Box } from "@mui/material";
import {
	AdminTable,
	AdminResults,
	AdminHelp,
	AdminSettings,
} from "../components/admin";
import {
	getTabProps,
	TabBoxStyle,
	TabPanel,
	TabStyle,
} from "./utils/adminUtils";
import { FormApiListRecord, StudentFormRecord } from "../types/admin";
import Loading from "../components/Loading";
import "./AdminPage.scss";
import { getFormList, getResults } from "../services/admin.service";
import { getParam } from "../services/params.service";

export default function AdminPage() {
	const [tabIndex, setTabIndex] = useState<number>(0);
	const [resultsLink, setResultsLink] = useState<string | undefined>();
	const [formList, setFormList] = useState<StudentFormRecord[] | undefined>();
	const [startDate, setStartDate] = useState<Date | undefined>();
	const [endDate, setEndDate] = useState<Date | undefined>();
	const [formsNum, setFormsNum] = useState<number | undefined>();

	useEffect(() => {
		fetchResultsLink();
		fetchFormList();
		fetchStartDate();
		fetchEndDate();
		fetchFormsNum();
	}, []);

	const fetchResultsLink = async () => {
		const result = await getResults();
		setResultsLink(result.oneDriveLink);
	};

	const fetchStartDate = async () => {
		const result = await getParam("RECRUITMENT_START_DATE");
		setStartDate(new Date(result?.value));
	};

	const fetchEndDate = async () => {
		const result = await getParam("RECRUITMENT_END_DATE");
		setEndDate(new Date(result?.value));
	};

	const fetchFormsNum = async () => {
		const result = await getParam("MAX_RECUITMENT_FORMS_PER_STUDENT");
		setFormsNum(parseInt(result?.value));
	};

	const fetchFormList = async () => {
		const result = await getFormList();
		if (result) {
			const formRecords = result.map((form: FormApiListRecord) => ({
				firstName: form.firstName ?? "Jan",
				lastName: form.lastName ?? "Kowalski",
				major: form.major ?? "Informatyka",
				faculty: form.faculty ?? "WIEiT",
				contractCoordinator: form.contractCoordinator ?? "Anna Nowak",
				priority: form.priority ?? "1",
				link: form.oneDriveFormLink ?? "",
				formId: form.id ?? "",
			}));
			setFormList(formRecords);
		}
	};

	const handleChange = (_event: SyntheticEvent, newValue: number) => {
		setTabIndex(newValue);
	};

	if (!resultsLink || !formList) {
		return <Loading />;
	}

	return (
		<div className="admin">
			<Box sx={TabBoxStyle}>
				<Tabs
					orientation="vertical"
					variant="scrollable"
					value={tabIndex}
					onChange={handleChange}
					aria-label="v-tabs"
					sx={TabStyle}>
					<Tab className="tab" label="Lista ankiet" {...getTabProps(0)} />
					<Tab className="tab" label="Wyniki" {...getTabProps(1)} />
					<Tab className="tab" label="Ustawienia" {...getTabProps(2)} />
					<Tab className="tab" label="Pomoc" {...getTabProps(3)} />
				</Tabs>
				<TabPanel value={tabIndex} index={0}>
					<AdminTable data={formList} />
				</TabPanel>
				<TabPanel value={tabIndex} index={1}>
					<AdminResults resultsLink={resultsLink} />
				</TabPanel>
				<TabPanel value={tabIndex} index={2}>
					<AdminSettings config={{ startDate, endDate, formsNum }} />
				</TabPanel>
				<TabPanel value={tabIndex} index={3}>
					<AdminHelp />
				</TabPanel>
			</Box>
		</div>
	);
}
