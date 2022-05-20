import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { RootState } from "./store";

interface GlobalState {
	isLoading: boolean;
}

const initialState: GlobalState = {
	isLoading: false,
};

export const globalSlice = createSlice({
	name: "global",
	initialState,
	reducers: {
		setLoading: (state, action: PayloadAction<boolean>) => {
			state.isLoading = action.payload;
		},
	},
});

export const { setLoading } = globalSlice.actions;
export const isLoading = (state: RootState) => state.globalData.isLoading;

export default globalSlice.reducer;
