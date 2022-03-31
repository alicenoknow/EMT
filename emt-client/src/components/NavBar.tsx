import React from "react";
import { Container, Navbar, Nav } from "react-bootstrap";

export default function NavBar() {
	return (
		<Navbar bg="light" variant="light">
			<Container>
				<Navbar.Brand href="/">
					<img
						alt=""
						src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Znak_graficzny_AGH.svg/285px-Znak_graficzny_AGH.svg.png"
						width="80"
						height="80"
						className="d-inline-block align-top"
					/>
				</Navbar.Brand>
				<Nav className="me-auto">
					<Nav.Link href="/about">O programie</Nav.Link>
					<Nav.Link href="/faq">FAQ</Nav.Link>
					<Nav.Link href="/news">Aktualności</Nav.Link>
					<Nav.Link href="/login/user">Rekrutacja</Nav.Link>
				</Nav>
			</Container>
		</Navbar>
	);
}
