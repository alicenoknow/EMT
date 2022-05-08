import React from "react";
import { getAlertConfig, shouldShowAlert } from "../redux/alertSlice";
import { useAppSelector } from "../redux/hooks";
import NavBar from "./NavBar";
import TopAlert from "./TopAlert";
import "./UtilsStyles.scss";

interface EmptyProps {
	nothing?: never;
}

export default function ScreenWrapper(
	props: React.PropsWithChildren<EmptyProps>,
) {
	const alertConfig = useAppSelector(getAlertConfig);
    const isAlertVisible = useAppSelector(shouldShowAlert);

	return (
		<div className="container">
			<NavBar />
			{isAlertVisible && <TopAlert {...alertConfig} />}
			{props.children}
		</div>
	);
}
