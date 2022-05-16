import React from "react";
import { Container, Accordion, Button, Badge } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { getUserCredentials } from "../services/auth.service";
import "./UserPage.scss";

export default function UserPage() {
	const navigate = useNavigate();
	const userEmail = getUserCredentials();

	return (
		<Container fluid className="p-0">
			<div className="user">
				<div className="user__header">
					<h4>
						{userEmail} <Badge bg="secondary">Student</Badge>
					</h4>
				</div>
				<Accordion className="user__menu" defaultActiveKey="0">
					<Accordion.Item eventKey="0">
						<Accordion.Header>Studia zagraniczne</Accordion.Header>
						<Accordion.Body>
							<Button
								variant="link"
								onClick={() => {
									navigate("/form");
								}}>
								Ankieta rekrutacyjna
							</Button>
						</Accordion.Body>
					</Accordion.Item>
					<Accordion.Item eventKey="1">
						<Accordion.Header>Praktyki zagraniczne</Accordion.Header>
						<Accordion.Body></Accordion.Body>
					</Accordion.Item>
				</Accordion>
			</div>
		</Container>
	);
}
