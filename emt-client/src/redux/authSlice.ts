import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { RootState } from "./store";

interface AuthState {
	authToken: string | undefined;
	isAdmin: boolean;
}

const initialState: AuthState = {
	authToken: undefined,
	isAdmin: false,
};

export const authSlice = createSlice({
	name: "auth",
	initialState,
	reducers: {
		setAuthToken: (state, action: PayloadAction<string>) => {
			state.authToken = action.payload;
		},
		setAdmin: (state, action: PayloadAction<boolean>) => {
			state.isAdmin = action.payload;
		},
	},
});

export const { setAuthToken, setAdmin } = authSlice.actions;
export const getAuthToken = (state: RootState) => state.authData.authToken;
export const isAdmin = (state: RootState) => state.authData.isAdmin;

export default authSlice.reducer;
