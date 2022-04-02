import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./selectors";

export const store = configureStore({
	reducer: {
		data: authReducer,
	},
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
