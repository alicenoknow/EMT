import React from "react";
import AdminTable from "../components/AdminTable";
import "./AdminPage.scss";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import AdminResults from "../components/AdminResults";
import AdminSettings from "../components/AdminSettings";
import HelpMarkdown from "../components/AdminHelp";

interface TabPanelProps {
	children?: React.ReactNode;
	index: number;
	value: number;
}

function TabPanel(props: TabPanelProps) {
	const { children, value, index, ...other } = props;

	return (
		<span
			role="tabpanel"
			hidden={value !== index}
			id={`vertical-tabpanel-${index}`}
			aria-labelledby={`vertical-tab-${index}`}
			{...other}>
			{value === index && (
				<Box sx={{ p: 3 }}>
					<Typography component="span">{children}</Typography>
				</Box>
			)}
		</span>
	);
}

function a11yProps(index: number) {
	return {
		id: `vertical-tab-${index}`,
		"aria-controls": `vertical-tabpanel-${index}`,
	};
}

export default function AdminPage() {
	const [value, setValue] = React.useState(0);

	const handleChange = (_event: React.SyntheticEvent, newValue: number) => {
		setValue(newValue);
	};

	return (
		<div className="admin">
			<Box
				sx={{
					flexGrow: 1,
					bgcolor: "background.paper",
					display: "flex",
					height: "80vh",
				}}>
				<Tabs
					orientation="vertical"
					variant="scrollable"
					value={value}
					onChange={handleChange}
					aria-label="Vertical tabs example"
					sx={{
						borderRight: 1,
						borderColor: "divider",
						height: "100%",
						width: "15%",
					}}>
					<Tab className="tab" label="Lista ankiet" {...a11yProps(0)} />
					<Tab className="tab" label="Wyniki" {...a11yProps(1)} />
					<Tab className="tab" label="Ustawienia" {...a11yProps(2)} />
					<Tab className="tab" label="Pomoc" {...a11yProps(3)} />
				</Tabs>
				<TabPanel value={value} index={0}>
					<AdminTable />
				</TabPanel>
				<TabPanel value={value} index={1}>
					<AdminResults />
				</TabPanel>
				<TabPanel value={value} index={2}>
					<AdminSettings />
				</TabPanel>
				<TabPanel value={value} index={3}>
					<HelpMarkdown />
				</TabPanel>
			</Box>
		</div>
	);
}
