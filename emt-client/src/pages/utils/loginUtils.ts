export function validateEmail(email: string) {
	return /^[a-z].*@.*agh\.edu\.pl/.test(email);
}

export function validateForm(email: string, password: string) {
	return validateEmail(email) && password.length > 0;
}
