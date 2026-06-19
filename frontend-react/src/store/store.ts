import { configureStore, createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { Role } from "../api/client";

type AuthState = {
  token: string | null;
  name: string | null;
  role: Role | null;
};

const initialState: AuthState = {
  token: localStorage.getItem("job_portal_token"),
  name: localStorage.getItem("job_portal_name"),
  role: localStorage.getItem("job_portal_role") as Role | null
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    signedIn(state, action: PayloadAction<AuthState>) {
      state.token = action.payload.token;
      state.name = action.payload.name;
      state.role = action.payload.role;
      if (action.payload.token) localStorage.setItem("job_portal_token", action.payload.token);
      if (action.payload.name) localStorage.setItem("job_portal_name", action.payload.name);
      if (action.payload.role) localStorage.setItem("job_portal_role", action.payload.role);
    },
    signedOut(state) {
      state.token = null;
      state.name = null;
      state.role = null;
      localStorage.removeItem("job_portal_token");
      localStorage.removeItem("job_portal_name");
      localStorage.removeItem("job_portal_role");
    }
  }
});

export const { signedIn, signedOut } = authSlice.actions;

export const store = configureStore({
  reducer: {
    auth: authSlice.reducer
  }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
