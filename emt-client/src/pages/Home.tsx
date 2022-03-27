import React, { useState } from "react";
import { Button } from "react-bootstrap";
import "./Home.scss";

interface HomeProps {
	helloMessage?: string;
}

interface HomeState {
	isMessageVisible: boolean;
}

export default function Home(props: HomeProps) {
	const [state, setState] = useState<HomeState>({
		isMessageVisible: false,
	});

	const { isMessageVisible } = state;

	const onButtonClick = () => {
		setState({ isMessageVisible: !isMessageVisible });
	};

	const messageText = "✨ Twoja stara to kopara, a twój stary ją odpala ✨";
	const buttonText = isMessageVisible ? "🥵 Hide" : "👀 Show";

	return (
		<div className="home">
			<div className="home__message">{messageText}</div>
			<Button className="home__button" onClick={onButtonClick}>
				{buttonText}
			</Button>
			{isMessageVisible && (
				<div className="home__hello">{props.helloMessage}</div>
			)}
		</div>
	);
}
