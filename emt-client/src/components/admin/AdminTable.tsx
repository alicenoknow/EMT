import React, { useEffect, useState } from "react";
import {
	Box,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TablePagination,
	TableRow,
	TableSortLabel,
	Paper,
} from "@mui/material";
import { Button } from "react-bootstrap";
import SearchBar from "material-ui-search-bar";
import { visuallyHidden } from "@mui/utils";
import {
	CustomTableHeadProps,
	getComparator,
	headCells,
	Order,
} from "./tableUtils";
import { StudentFormRecord } from "../../types/admin";

interface AdminTableProps {
	data?: StudentFormRecord[];
}

export const renderOneDriveLink = (destination: string) => {
	return (
		<Button
			className="table__button"
			variant="link"
			href={destination}
			target="_blank">
			Ankieta Rekrutacyjna
		</Button>
	);
};

export function CustomTableHead(props: CustomTableHeadProps) {
	const { order, orderBy, onRequestSort } = props;
	const createSortHandler =
		(property: keyof StudentFormRecord) =>
		(event: React.MouseEvent<unknown>) => {
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

export default function AdminTable(props: AdminTableProps) {
	const [order, setOrder] = useState<Order>("asc");
	const [orderBy, setOrderBy] = useState<keyof StudentFormRecord>("lastName");
	const [page, setPage] = useState(0);
	const [rowsPerPage, setRowsPerPage] = useState(10);
	const [searched, setSearched] = useState<string>("");
	const [filteredRows, setRows] = useState<StudentFormRecord[]>([]);

	const rows = props.data ?? [];

	useEffect(() => setRows(props.data ?? []), [props.data]);

	const handleRequestSort = (
		_event: React.MouseEvent<unknown>,
		property: keyof StudentFormRecord,
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
		if (!rows) {
			return setRows([]);
		}

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
		<Paper>
			<SearchBar
				value={searched}
				onChange={(searchVal: string) => requestSearch(searchVal)}
				onCancelSearch={() => cancelSearch()}
				placeholder={"Wyszukaj"}
			/>
			<TableContainer>
				<Table aria-labelledby="form-table" size="small">
					<CustomTableHead
						order={order}
						orderBy={orderBy}
						onRequestSort={handleRequestSort}
					/>
					<TableBody>
						{filteredRows
							.slice()
							.sort(getComparator(order, orderBy))
							.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
							.map((row: StudentFormRecord) => {
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
