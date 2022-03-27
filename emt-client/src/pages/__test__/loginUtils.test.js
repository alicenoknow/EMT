/* eslint-disable no-undef */
import { validateEmail, validateForm } from "../utils/loginUtils";

describe("test validateEmail", () => {
	test.each([
		["marlon123@agh.edu.pl"],
		["brando420@student.agh.edu.pl"],
		["c00l_addr3$$@idk.agh.edu.pl"],
	])("returns true on valid email", email => {
		expect(validateEmail(email)).toBeTruthy();
	});

	test.each([
		["123marlon123@agh.edu.pl"],
		["brando420@gmail.com"],
		["lol"],
		["email@.edu.agh.pl"],
		["@brando420.student.agh.edu.pl"],
	])("returns false on invalid email", email => {
		expect(validateEmail(email)).toBeFalsy();
	});
});

describe("test validateForm", () => {
	test.each([
		["marlon123@agh.edu.pl", "haslo"],
		["brando420@student.agh.edu.pl", "papa123"],
		["c00l_addr3$$@idk.agh.edu.pl", "xd"],
	])("returns true on valid form data", (email, password) => {
		expect(validateForm(email, password)).toBeTruthy();
	});

	test.each([
		["123marlon123@agh.edu.pl", "123jbc_psy"],
		["brando420@gmail.com", "legitne!Haslo"],
		["marlon123@agh.edu.pl", ""],
	])("returns false on invalid form data", (email, password) => {
		expect(validateForm(email, password)).toBeFalsy;
	});
});
