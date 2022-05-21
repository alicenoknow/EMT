import React, { useState } from "react";
import Box from "@mui/material/Box";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import TableSortLabel from "@mui/material/TableSortLabel";
import Paper from "@mui/material/Paper";
import { visuallyHidden } from "@mui/utils";
import { getComparator, Order } from "./utils/tableUtils";
import { Button } from "react-bootstrap";
import SearchBar from "material-ui-search-bar";

interface StudentFormRow {
	firstName: string;
	lastName: string;
	major: string;
	faculty: string;
	contractCoordinator: string;
	priority: number;
	link: string;
	formId: string;
}

const renderOneDriveLink = (destination: string) => (
	<Button className="table__button" variant="link" href={destination}>
		Ankieta Rekrutacyjna
	</Button>
);

function createData(
	firstName: string,
	lastName: string,
	major: string,
	faculty: string,
	contractCoordinator: string,
	priority: number,
	link: string,
	formId: string,
): StudentFormRow {
	return {
		firstName,
		lastName,
		major,
		faculty,
		contractCoordinator,
		priority,
		link,
		formId,
	};
}

interface HeadCell {
	disablePadding: boolean;
	id: keyof StudentFormRow;
	label: string;
	numeric: boolean;
}

const headCells: readonly HeadCell[] = [
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

interface EnhancedTableProps {
	onRequestSort: (
		event: React.MouseEvent<unknown>,
		property: keyof StudentFormRow,
	) => void;
	order: Order;
	orderBy: string;
}

function EnhancedTableHead(props: EnhancedTableProps) {
	const { order, orderBy, onRequestSort } = props;
	const createSortHandler =
		(property: keyof StudentFormRow) => (event: React.MouseEvent<unknown>) => {
			onRequestSort(event, property);
		};

	return (
		<TableHead>
			<TableRow>
				{headCells.map(headCell => (
					<TableCell
						key={headCell.id}
						padding={"normal"}
						sortDirection={orderBy === headCell.id ? order : false}>
						<TableSortLabel
							active={orderBy === headCell.id}
							direction={orderBy === headCell.id ? order : "asc"}
							onClick={createSortHandler(headCell.id)}>
							{headCell.label}
							{orderBy === headCell.id ? (
								<Box sx={visuallyHidden}>
									{order === "desc" ? "sorted descending" : "sorted ascending"}
								</Box>
							) : null}
						</TableSortLabel>
					</TableCell>
				))}
			</TableRow>
		</TableHead>
	);
}

const rows = [
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

export default function AdminTable() {
	const [order, setOrder] = useState<Order>("asc");
	const [orderBy, setOrderBy] = useState<keyof StudentFormRow>("lastName");
	const [page, setPage] = useState(0);
	const [rowsPerPage, setRowsPerPage] = useState(10);
	const [searched, setSearched] = useState<string>("");
	const [filteredRows, setRows] = useState<StudentFormRow[]>(rows);

	const handleRequestSort = (
		_event: React.MouseEvent<unknown>,
		property: keyof StudentFormRow,
	) => {
		const isAsc = orderBy === property && order === "asc";
		setOrder(isAsc ? "desc" : "asc");
		setOrderBy(property);
	};

	const handleChangePage = (_event: unknown, newPage: number) => {
		setPage(newPage);
	};

	const handleChangeRowsPerPage = (
		event: React.ChangeEvent<HTMLInputElement>,
	) => {
		setRowsPerPage(parseInt(event.target.value, 10));
		setPage(0);
	};

	const requestSearch = (searchedVal: string) => {
		const filteredRows = rows.filter(row => {
			return (
				row.firstName.toLowerCase().includes(searchedVal.toLowerCase()) ||
				row.lastName.toLowerCase().includes(searchedVal.toLowerCase()) ||
				row.major.toLowerCase().includes(searchedVal.toLowerCase()) ||
				row.faculty.toLowerCase().includes(searchedVal.toLowerCase()) ||
				row.contractCoordinator
					.toLowerCase()
					.includes(searchedVal.toLowerCase())
			);
		});
		setRows(filteredRows);
	};

	const cancelSearch = () => {
		setSearched("");
		requestSearch(searched);
	};

	const emptyRows =
		page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

	return (
		<Paper sx={{ width: "100%", mb: 2 }}>
			<SearchBar
				value={searched}
				onChange={(searchVal: string) => requestSearch(searchVal)}
				onCancelSearch={() => cancelSearch()}
				placeholder={"Wyszukaj"}
			/>
			<TableContainer component="span">
				<Table
					sx={{ minWidth: "100%" }}
					aria-labelledby="tableTitle"
					size="small">
					<EnhancedTableHead
						order={order}
						orderBy={orderBy}
						onRequestSort={handleRequestSort}
					/>
					<TableBody>
						{filteredRows
							.slice()
							.sort(getComparator(order, orderBy))
							.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
							.map((row: StudentFormRow) => {
								return (
									<TableRow hover tabIndex={-1} key={row.formId}>
										<TableCell align="left">{row.firstName}</TableCell>
										<TableCell align="left">{row.lastName}</TableCell>
										<TableCell align="left">{row.major}</TableCell>
										<TableCell align="left">{row.faculty}</TableCell>
										<TableCell align="left">
											{row.contractCoordinator}
										</TableCell>
										<TableCell align="left">{row.priority}</TableCell>
										<TableCell align="left">
											{renderOneDriveLink(row.link)}
										</TableCell>
									</TableRow>
								);
							})}
						{emptyRows > 0 && (
							<TableRow
								style={{
									height: 33 * emptyRows,
								}}>
								<TableCell colSpan={6} />
							</TableRow>
						)}
					</TableBody>
				</Table>
			</TableContainer>
			<TablePagination
				rowsPerPageOptions={[10, 25, 50]}
				component="div"
				count={rows.length}
				rowsPerPage={rowsPerPage}
				page={page}
				onPageChange={handleChangePage}
				onRowsPerPageChange={handleChangeRowsPerPage}
			/>
		</Paper>
	);
}
