import React from "react";
import { Container, Button, ButtonGroup } from "react-bootstrap";
import ReactMarkdown from "react-markdown";
import { Link } from "react-router-dom";
import "./DocumentPage.scss";

const markdown = `Tutaj znajdziesz najważniejsze informacje dotyczące programu Erasmus+ realizowanego na naszej uczelni.`;

const links: { url: string; title: string }[] = [
	{
		url: "http://www.erasmusplus.agh.edu.pl/program-erasmus-w-agh/",
		title: "Ogólne informacje o programie",
	},
	{
		url: "http://www.erasmusplus.agh.edu.pl/erasmus-student/studia-krok-po-kroku/",
		title: "Podstawowe informacje o studiach zagranicznych",
	},
	{
		url: "http://www.erasmusplus.agh.edu.pl/umowy-miedzyinstytucjonalne-erasmus/wykaz-umow-erasmus/",
		title: "Wykaz umów",
	},
	{
		url: "http://www.erasmusplus.agh.edu.pl/erasmus-koordynator/erasmus-koordynator-wydzialowy/",
		title: "Koordynatorzy wydziałowi",
	},
	{
		url: "http://www.erasmusplus.agh.edu.pl/erasmus-student/zasady-ogolne-i-regulamin-erasmus-studiapraktyki/",
		title: "Regulamin programu",
	},
	{
		url: "http://www.erasmusplus.agh.edu.pl/erasmus-student/dokumenty/",
		title: "Wymagane dokumenty",
	},
];

export default function About() {
	return (
		<div className="document">
			<h2
				style={{
					marginBottom: "5vh",
					alignSelf: "center",
					justifyContent: "center",
				}}>
				Program Erasmus+
			</h2>
			<ReactMarkdown>{markdown}</ReactMarkdown>
			<Container fluid className="p-0">
				<div
					style={{
						marginTop: "10vh",
						marginBottom: "10vh",
						justifyContent: "center",
						alignContent: "center",
						alignItems: "center",
						display: "flex",
						flex: 1,
						flexDirection: "column",
					}}>
					<ButtonGroup
						vertical
						style={{
							width: "40hw",
							flex: 1,
							display: "flex",
						}}>
						{links.map(item => (
							<div
								key={item.url}
								style={{
									marginBottom: 20,
									width: "100%",
								}}>
								<Button
									onClick={() => window.open(item.url, "_blank")}
									variant="outline-dark"
									size="lg"
									style={{ width: "100%" }}>
									{item.title}
								</Button>
							</div>
						))}
					</ButtonGroup>
				</div>
			</Container>
		</div>
	);
}
