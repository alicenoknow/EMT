import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { RootState } from "./store";

interface AlertState {
	type: "success" | "danger" | "warning" | "info";
	header: string;
	body: string;
	shouldShowAlert: boolean;
}

const initialState: AlertState = {
	type: "info",
	header: "",
	body: "",
	shouldShowAlert: false,
};

export const alertSlice = createSlice({
	name: "alert",
	initialState,
	reducers: {
		setAlert: (
			state,
			action: PayloadAction<Omit<AlertState, "shouldShowAlert">>,
		) => {
			state.type = action.payload.type;
			state.header = action.payload.header;
			state.body = action.payload.body;
			state.shouldShowAlert = true;
		},

		setAlertVisibility: (state, action: PayloadAction<boolean>) => {
			state.shouldShowAlert = action.payload;
		},
	},
});

export const { setAlert, setAlertVisibility } = alertSlice.actions;
export const getAlertConfig = (state: RootState) => ({
	type: state.alertData.type,
	header: state.alertData.header,
	body: state.alertData.body,
});
export const shouldShowAlert = (state: RootState) =>
	state.alertData.shouldShowAlert;

export default alertSlice.reducer;
