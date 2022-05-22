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
import { AdminQueryData } from "../types/admin";
import Loading from "../components/Loading";
import "./AdminPage.scss";

export default function AdminPage() {
	const [tabIndex, setTabIndex] = useState<number>(0);
	const [adminData, setAdminData] = useState<AdminQueryData>();

	useEffect(() => {
		//TODO fetch data about admin (see: AdminQueryData)
		//setAdminData(data);
	}, []);

	const handleChange = (_event: SyntheticEvent, newValue: number) => {
		setTabIndex(newValue);
	};

	if (!adminData) {
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
					<AdminTable data={adminData?.data?.formList} />
				</TabPanel>
				<TabPanel value={tabIndex} index={1}>
					<AdminResults />
				</TabPanel>
				<TabPanel value={tabIndex} index={2}>
					<AdminSettings config={adminData?.data?.config} />
				</TabPanel>
				<TabPanel value={tabIndex} index={3}>
					<AdminHelp />
				</TabPanel>
			</Box>
		</div>
	);
}
