import React from "react";
import { Button } from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";

import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";

const GoToButton = () => (
	<Button
		className="table__button"
		size="sm"
		type="button"
		onClick={() => {
			console.warn("Otwieram dokument");
		}}
	>
    Otwórz
  </Button>
);

const products = [
	{
		id: 1,
		firstName: "Anna",
		lastName: "Kowalska",
		doc: "Ankieta Rekrutacyjna",
		deptCoordinator: "Jan Nowak",
		contractCoordinator: "Adam Random",
		status: "Złożony",
		rank: 35,
		link: GoToButton(),
	},
  {
		id: 2,
		firstName: "Filip",
		lastName: "Nowak",
		doc: "Ankieta Rekrutacyjna",
		deptCoordinator: "Jan Nowak",
		contractCoordinator: "Adam Random",
		status: "Zatwierdzony",
		rank: 75,
		link: GoToButton(),
	},
];

const columns = [
	{
		dataField: "id",
		text: "Nr indeksu",
		sort: true,
	},
	{
		dataField: "firstName",
		text: "Imię",
		sort: true,
	},
	{
		dataField: "lastName",
		text: "Nazwisko",
		sort: true,
	},
	{
		dataField: "doc",
		text: "Dokument",
	},
	{
		dataField: "deptCoordinator",
		text: "Koordynator wydziału",
	},
	{
		dataField: "contractCoordinator",
		text: "Koordynator umowy",
	},
	{
		dataField: "status",
		text: "Status",
	},
	{
		dataField: "rank",
		text: "Ranking",
		sort: true,
	},
	{
		dataField: "link",
		text: "Link",
	},
];

export default function AdminTable() {
	return <BootstrapTable keyField="id" data={products} columns={columns} />;
}
