import React, { useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import { validateForm } from "./utils/loginUtils";
import "./Login.scss";

interface LoginState {
	email: string;
	password: string;
}

export default function Login() {
	const [state, setState] = useState<LoginState>({
		email: "",
		password: "",
	});

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
	};

	const { email, password } = state;

	return (
		<Container fluid className="p-0">
			<div className="login">
				<Form className="login__form" onSubmit={handleSubmit}>
					<Form.Group className="login__input" controlId="email">
						<Form.Control
							autoFocus
							type="email"
							value={email}
							placeholder="Podaj adres email"
							onChange={e => setState({ ...state, email: e.target.value })}
						/>
						<Form.Text>Użyj adresu email w domenie AGH.</Form.Text>
					</Form.Group>
					<Form.Group className="login__input" controlId="password">
						<Form.Control
							type="password"
							value={password}
							placeholder="Podaj hasło"
							onChange={e => setState({ ...state, password: e.target.value })}
						/>
						<Form.Text>Hasło nie może być puste.</Form.Text>
					</Form.Group>
					<div className="login__buttons">
						<Button
							className="login__button"
							size="lg"
							type="submit"
							disabled={!validateForm(email, password)}>
							Zaloguj
						</Button>
						<Button
							className="login__button"
							size="lg"
							type="button"
							disabled={!validateForm(email, password)}>
							Zarejstruj
						</Button>
					</div>
					<div className="login__buttons--secondary">
						<Button className="login__button--secondary" variant="link">
							Nie pamiętasz hasła?
						</Button>
					</div>
				</Form>
			</div>
		</Container>
	);
}
