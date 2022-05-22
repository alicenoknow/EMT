import React, { useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { validateForm } from "./utils/loginUtils";
import FormGroup from "../components/forms/FormGroup";
import "./Login.scss";
import SecondaryButton from "../components/buttons/SecondaryButton";
import { login, register } from "../services/auth.service";
import { setAdmin, setAuthToken } from "../redux/authSlice";
import { setLoading } from "../redux/globalSlice";
import { useAppDispatch } from "../redux/hooks";
import { LoginResponse, Roles } from "../services/types";
import {
	LoginFailedAlert,
	RegisterSuccessAlert,
	RegisterFailAlert,
} from "./utils/alertUtils";
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
		setTimeout(() => navigate(path), 1500);
	};

	const handleLogin = async () => {
		dispatch(setLoading(true));
		const response: LoginResponse = await login(state.email, state.password);
		dispatch(setLoading(false));

		if (response && response.token && response.roles) {
			dispatch(setAuthToken(response.token));
			if (response.roles.includes(Roles.ADMIN) || response.roles.includes(Roles.FACULTY_COORDINATOR) ||
				response.roles.includes(Roles.CONTRACT_COORDINATOR) || response.roles.includes(Roles.DEAN_OFFICE_WORKER) ||
				response.roles.includes(Roles.FOREIGN_COUNTRIES_DEPARTMENT_REP) || response.roles.includes(Roles.OTHER_ADMIN)) {
				navigateTo("/admin", true);
			} else {
				navigateTo("/user", false);
			}
		} else {
			dispatch(setAlert(LoginFailedAlert));
		}
	};

	const handleRegister = async () => {
		dispatch(setLoading(true));
		const response: number | undefined = await register(
			state.email,
			state.password,
		);
		dispatch(setLoading(false));

		if (response === 200) {
			dispatch(setAlert(RegisterSuccessAlert));
		} else {
			dispatch(setAlert(RegisterFailAlert));
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
							onClick={() => handleRegister()}
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
