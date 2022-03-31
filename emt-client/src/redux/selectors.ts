import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { RootState } from "./store";

interface AuthState {
	authToken: string | undefined;
}

const initialState: AuthState = {
	authToken: undefined,
};

export const authSlice = createSlice({
	name: "auth",
	initialState,
	reducers: {
		setAuthToken: (state, action: PayloadAction<string>) => {
			state.authToken = action.payload;
		},
	},
});

export const { setAuthToken } = authSlice.actions;
export const getAuthToken = (state: RootState) => state.data.authToken;

export default authSlice.reducer;
