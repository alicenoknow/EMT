import React, { useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';
import { validateForm } from "./utils/loginUtils";
import FormGroup from '../components/forms/FormGroup';
import "./Login.scss";

interface LoginState {
	email: string;
	password: string;
}

export default function Login() {
	const navigate = useNavigate();
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
					<FormGroup
						id="email"
						type="email"
						value={email}
						placeholder="Podaj adres email"
						onChange={e => setState({ ...state, email: e.target.value })}
						bottomText={'Użyj adresu email w domenie AGH.'}

					/>
					<FormGroup
						id="password"
						type="password"
						value={password}
						placeholder="Podaj hasło"
						onChange={e => setState({ ...state, password: e.target.value })}
						bottomText={'Hasło nie może być puste.'}

					/>
					<div className="login__buttons">
						<Button
							className="login__button"
							size="lg"
							type="submit"
							disabled={!validateForm(email, password)}
							onClick={() => {navigate("/user")}}>
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
