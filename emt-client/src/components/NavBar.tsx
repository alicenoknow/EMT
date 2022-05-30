import React from "react";
import { Navbar, Nav } from "react-bootstrap";

export default function NavBar() {
	return (
		<Navbar>
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
				<Nav.Link href="/tutorial">Krok po kroku</Nav.Link>
				<Nav.Link href="/login/user">Rekrutacja</Nav.Link>
			</Nav>
		</Navbar>
	);
}
