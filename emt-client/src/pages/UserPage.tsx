import React from "react";
import { Container, Accordion, Button, Badge } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "./UserPage.scss";

interface UserPageProps {
    firstName: string;
    lastName: string;
}

export default function UserPage(props: UserPageProps) {
    const navigate = useNavigate();

    const {firstName, lastName} = props;

	return (
		<Container fluid className="p-0">
            <div className="user">
                <div className="user__header">
                    <h4>{firstName} {lastName} <Badge bg="secondary">Student</Badge></h4>
                </div>
                <Accordion className="user__menu" defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Profil</Accordion.Header>
                        <Accordion.Body>Informacje o profilu użytkownika, możliwość zmiany hasła, opcje powiadomień itp.</Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Dokumenty - studia</Accordion.Header>
                        <Accordion.Body>
                            <Button variant="link" onClick={() => { navigate("/form") }}>Ankieta rekrutacyjna</Button>
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="2">
                        <Accordion.Header>Dokumenty - praktyki</Accordion.Header>
                        <Accordion.Body>
                            Dokumenty związane z aplikacja na praktyki w ramach programu.
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
			</div>
		</Container>
	);
}
