import React from "react";
import { Container } from "react-bootstrap";
import Lottie from "react-lottie";
import animationData from "../assets/animation.json";
import "./Home.scss";

export default function Home() {
	const defaultOptions = {
		loop: true,
		autoplay: true,
		animationData: animationData,
	};

	return (
		<Container fluid className="p-0">
			<div className="animation__container">
				<div className="title__container">
					<h3 className="animation__title">Witaj na stronie AGH Erasmus+</h3>
				</div>
				<div className="animation">
					<Lottie options={defaultOptions} />
				</div>
			</div>
		</Container>
	);
}
