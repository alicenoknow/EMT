import React from "react";
import { Container } from "react-bootstrap";
import AdminTable from "../components/AdminTable";
import "./AdminPage.scss";

export default function AdminPage() {
	return (
		<div className="admin">
			<Container fluid className="p-0">
				<AdminTable/>
			</Container>
		</div>
	);
}
