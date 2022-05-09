import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice";
import alertReducer from "./alertSlice";
import globalReducer from "./globalSlice";

export const store = configureStore({
	reducer: {
		authData: authReducer,
		alertData: alertReducer,
		globalData: globalReducer,
	},
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
