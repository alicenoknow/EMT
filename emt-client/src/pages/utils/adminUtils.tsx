import React, { ReactNode } from "react";
import { Box, Typography } from "@material-ui/core";
import "../AdminPage.scss";

interface TabPanelProps {
	index: number;
	value: number;
	children?: ReactNode;
}

export const TabBoxStyle = {
	flexGrow: 1,
	bgcolor: "background.paper",
	display: "flex",
	height: "80vh",
};

export const TabStyle = {
	borderRight: 1,
	borderColor: "divider",
	height: "100%",
	minWidth: "15vw",
};

const InnerBoxStyle = {
	p: 3,
	width: "100%",
};

export function TabPanel(props: TabPanelProps) {
	const { children, value, index, ...other } = props;

	return (
		<span
			className="tab__box"
			role="tabpanel"
			hidden={value !== index}
			id={`vertical-tabpanel-${index}`}
			aria-labelledby={`vertical-tab-${index}`}
			{...other}>
			{value === index && (
				<Box sx={InnerBoxStyle}>
					<Typography component="span">{children}</Typography>
				</Box>
			)}
		</span>
	);
}

export function getTabProps(index: number) {
	return {
		id: `vertical-tab-${index}`,
		"aria-controls": `vertical-tabpanel-${index}`,
	};
}
