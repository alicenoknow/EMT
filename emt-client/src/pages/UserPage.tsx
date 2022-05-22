import React, { SyntheticEvent, useState } from "react";
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

export default function UserPage() {
	const [tabIndex, setTabIndex] = useState<number>(1);
	const userEmail = getUserCredentials();

	const handleChange = (_event: SyntheticEvent, newValue: number) => {
		setTabIndex(newValue);
	};

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
					<UserFormList />
				</TabPanel>
			</Box>
		</div>
	);
}
