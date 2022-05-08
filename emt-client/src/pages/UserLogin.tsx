import React, { useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { validateForm } from "./utils/loginUtils";
import FormGroup from "../components/forms/FormGroup";
import "./Login.scss";
import Spacer from "../components/Spacer";
import SecondaryButton from "../components/buttons/SecondaryButton";
import { login } from "../services/auth.service";
import { setAdmin, setAuthToken } from "../redux/authSlice";
import { useAppDispatch } from "../redux/hooks";
import { LoginResponse, Roles } from "../services/types";
import { LoginFailedAlert } from "./utils/alertUtils";
import { setAlert, setAlertVisibility } from "../redux/alertSlice";

interface LoginState {
	email: string;
	password: string;
}

export default function UserLogin() {
	const dispatch = useAppDispatch();
	const navigate = useNavigate();
	const [state, setState] = useState<LoginState>({
		email: "",
		password: "",
	});

	const navigateTo = (path: string, isAdmin: boolean) => {
		dispatch(setAdmin(isAdmin));
		dispatch(setAlertVisibility(false));
		navigate(path);
	};

	const handleLogin = async () => {
		const response: LoginResponse = await login(state.email, state.password);
		if (response && response.token && response.roles) {
			dispatch(setAuthToken(response.token));
			if (response.roles.includes(Roles.ADMIN)) {
				navigateTo("/admin", true);
			} else {
				navigateTo("/user", false);
			}
		} else {
			dispatch(setAlert(LoginFailedAlert));
		}
	};

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		handleLogin();
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
						placeholder="twojmail@agh.edu.pl"
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
		</div>
	);
}
