import React from "react";
import { Spinner } from "react-bootstrap";
import "./UtilsStyles.scss";

export default function Loading() {
	const styledSpinner = {
		height: 60,
		width: 60,
	};

	return (
		<div className="spinner__container">
			<Spinner
				style={styledSpinner}
				className={"mt-5"}
				animation="border"
				variant="dark"
			/>
		</div>
	);
}
