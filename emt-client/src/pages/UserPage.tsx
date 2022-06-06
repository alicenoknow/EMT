import React, { SyntheticEvent, useEffect, useState } from "react";
import { Tabs, Tab, Box } from "@mui/material";
import { getUserCredentials } from "../services/auth.service";
import "./UserPage.scss";
import {
	TabBoxStyle,
	TabStyle,
	getTabProps,
	TabPanel,
} from "./utils/adminUtils";
import { Badge } from "react-bootstrap";
import UserFormList from "../components/UserFormList";
import Loading from "../components/Loading";
import { getParam } from "../services/params.service";
import { useAppDispatch } from "../redux/hooks";
import { setAlert, setAlertVisibility } from "../redux/alertSlice";
import { HasFinished, NotStarted } from "./utils/alertUtils";

export default function UserPage() {
	const [tabIndex, setTabIndex] = useState<number>(1);
	const [formsNum, setFormsNum] = useState<number | undefined>();
	const [startDate, setStartDate] = useState<Date | undefined>();
	const [endDate, setEndDate] = useState<Date | undefined>();
	const userEmail = getUserCredentials();
	const dispatch = useAppDispatch();

	useEffect(() => {
		fetchFormsNum();
		fetchStartDate();
		fetchEndDate();
	}, []);

	const fetchStartDate = async () => {
		const result = await getParam("RECRUITMENT_START_DATE");
		await setStartDate(new Date(result?.value));

		if (startDate && startDate.getTime() > Date.now()) {
			dispatch(setAlert(NotStarted));
			dispatch(setAlertVisibility(true));
		}
	};

	const fetchEndDate = async () => {
		const result = await getParam("RECRUITMENT_END_DATE");
		await setEndDate(new Date(result?.value));
		if (endDate && endDate.getTime() < Date.now()) {
			dispatch(setAlert(HasFinished));
			dispatch(setAlertVisibility(true));
		}
	};

	const fetchFormsNum = async () => {
		const result = await getParam("MAX_RECUITMENT_FORMS_PER_STUDENT");
		await setFormsNum(parseInt(result?.value));
	};

	const handleChange = (_event: SyntheticEvent, newValue: number) => {
		setTabIndex(newValue);
	};

	if (!formsNum) {
		return <Loading />;
	}

	return (
		<div className="user">
			<Box sx={TabBoxStyle}>
				<Tabs
					orientation="vertical"
					variant="scrollable"
					value={tabIndex}
					onChange={handleChange}
					aria-label="v-tabs"
					sx={TabStyle}>
					<Tab
						className="tab"
						disabled
						label={
							<div className="user__header">
								<span>{userEmail}</span>{" "}
								<Badge className="user__badge" bg="secondary">
									Student
								</Badge>
							</div>
						}
						{...getTabProps(0)}
					/>
					<Tab className="tab" label="Studia zagraniczne" {...getTabProps(0)} />
				</Tabs>
				<TabPanel value={tabIndex} index={1}>
					<UserFormList config={{ startDate, endDate, formsNum }} />
				</TabPanel>
			</Box>
		</div>
	);
}
