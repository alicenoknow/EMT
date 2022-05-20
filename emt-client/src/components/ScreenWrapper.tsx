import React from "react";
import { getAlertConfig, shouldShowAlert } from "../redux/alertSlice";
import { isLoading } from "../redux/globalSlice";
import { useAppSelector } from "../redux/hooks";
import Loading from "./Loading";
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
	const showLoading = useAppSelector(isLoading);

	return (
		<div className="container">
			<NavBar />
			{showLoading && <Loading />}
			{isAlertVisible && <TopAlert {...alertConfig} />}
			{props.children}
		</div>
	);
}
