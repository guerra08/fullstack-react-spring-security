import api from "../api/api";

export async function signUp(payload) {
  try {
    const response = await api.post("/register", payload);
    if (response.status === 201) {
      return [null, true];
    }
    else {
      return ["Unable to SignUp", null];
    }
  } catch (err) {
    return ["Unable to SignUp", null];
  }
}
