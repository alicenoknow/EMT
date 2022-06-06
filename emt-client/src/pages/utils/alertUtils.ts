export const LoginFailedAlert = {
	type: "danger" as const,
	header: "Coś poszło nie tak 😭",
	body: `Szukaliśmy wszędzie 🔍, ale nie możemy znaleźć Twojego konta. Upewnij się, że podany e-mail i hasło są poprawne. Jeśli zapomniałeś hasła, kliknij "Nie pamiętasz hasła?", aby je zresetować.`,
};

export const RegisterSuccessAlert = {
	type: "success" as const,
	header: "Udało się 🙌",
	body: `Na podany adres e-mail przesłaliśmy link potwierdzający rejestrację. Potwierdź swój adres, a następnie zaloguj się`,
};

export const RegisterFailAlert = {
	type: "danger" as const,
	header: "Coś poszło nie tak 😭",
	body: `Nie można zarejestrować użytkownika z podanym adresem e-mail.`,
};

export const NotStarted = {
	type: "danger" as const,
	header: "Uwaga! Rekrutacja nie została jeszcze rozpoczęta!",
	body: `Przesłane formularze nie będą brane pod uwagę.`,
};

export const HasFinished = {
	type: "danger" as const,
	header: "Uwaga! Rekrutacja została już zakończona!",
	body: `Przesłane formularze nie będą brane pod uwagę.`,
};
