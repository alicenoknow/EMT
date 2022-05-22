import { StudentFormRecord } from "../../types/admin";

export type Order = "asc" | "desc";

export interface HeadCell {
	disablePadding: boolean;
	id: keyof StudentFormRecord;
	label: string;
	numeric: boolean;
}

export interface CustomTableHeadProps {
	onRequestSort: (
		event: React.MouseEvent<unknown>,
		property: keyof StudentFormRecord,
	) => void;
	order: Order;
	orderBy: string;
}

export function descendingComparator<T>(a: T, b: T, orderBy: keyof T) {
	if (b[orderBy] < a[orderBy]) {
		return -1;
	}
	if (b[orderBy] > a[orderBy]) {
		return 1;
	}
	return 0;
}

export function getComparator<Key extends keyof never>(
	order: Order,
	orderBy: Key,
): (
	a: { [key in Key]: number | string },
	b: { [key in Key]: number | string },
) => number {
	return order === "desc"
		? (a, b) => descendingComparator(a, b, orderBy)
		: (a, b) => -descendingComparator(a, b, orderBy);
}

export const rows = [
	{
		firstName: "Jan",
		lastName: "Kowalski",
		major: "Informatyka",
		faculty: "WIEiT",
		contractCoordinator: "Anna Nowak",
		priority: 420,
		link: "https://www.youtube.com/watch?v=BRiaWEHjUM0&ab_channel=Momentprawdy",
		formId: "xd",
	},
	{
		firstName: "John",
		lastName: "Doe",
		major: "Cyberbezpieczeństwo",
		faculty: "WIEiT",
		contractCoordinator: "Bartosz Nowak",
		priority: 69,
		link: "https://www.youtube.com/watch?v=BRiaWEHjUM0&ab_channel=Momentprawdy",
		formId: "lol",
	},
];

export const headCells: readonly HeadCell[] = [
	{
		id: "firstName",
		numeric: false,
		disablePadding: true,
		label: "Imię",
	},
	{
		id: "lastName",
		numeric: false,
		disablePadding: false,
		label: "Nazwisko",
	},
	{
		id: "major",
		numeric: false,
		disablePadding: false,
		label: "Kierunek",
	},
	{
		id: "faculty",
		numeric: false,
		disablePadding: false,
		label: "Wydział",
	},
	{
		id: "contractCoordinator",
		numeric: false,
		disablePadding: false,
		label: "Koordynator umowy",
	},
	{
		id: "priority",
		numeric: true,
		disablePadding: false,
		label: "Priorytet",
	},
	{
		id: "link",
		numeric: false,
		disablePadding: false,
		label: "Dokumenty",
	},
];
