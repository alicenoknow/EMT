import React, { useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { validateForm } from "./utils/loginUtils";
import FormGroup from "../components/forms/FormGroup";
import "./Login.scss";
import Spacer from "../components/Spacer";
import SecondaryButton from "../components/buttons/SecondaryButton";

interface LoginState {
	email: string;
	password: string;
}

export default function UserLogin() {
	const navigate = useNavigate();
	const [state, setState] = useState<LoginState>({
		email: "",
		password: "",
	});

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		// const response = loginUser(state.email, state.password);
		navigate("/user");
	};

	const { email, password } = state;

	return (
		<div className="login">
			<Container fluid className="p-0">
				<Form className="login__form" onSubmit={handleSubmit}>
					<FormGroup
						id="email"
						type="email"
						value={email}
						label="Adres e-mail"
						placeholder="twoj_mail@agh.edu.pl"
						onChange={e => setState({ ...state, email: e.target.value })}
						bottomText={"Użyj adresu email w domenie AGH."}
					/>
					<FormGroup
						id="password"
						type="password"
						value={password}
						label="Hasło"
						onChange={e => setState({ ...state, password: e.target.value })}
						bottomText={"Hasło nie może być puste."}
					/>
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
					<SecondaryButton text="Nie pamiętasz hasła?" onClick={() => null} />
				</Form>
			</Container>
			<div className="login__footer">
				<SecondaryButton
					text="Zaloguj się jako administrator"
					onClick={() => navigate("/login/admin")}
				/>
				<Spacer />
			</div>
		</div>
	);
}
