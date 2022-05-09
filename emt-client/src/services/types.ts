export enum Roles {
	ADMIN = "ROLE_ADMIN",
	USER = "ROLE_STUDENT",
}

export interface LoginResponse {
	readonly token: string;
	readonly email: string;
	readonly roles: ReadonlyArray<string>;
}
