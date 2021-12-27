import api from "../api/api";

export async function authUser(payload){
  try {
    const response = await api.post("/auth", payload);
    if(response.status !== 200)
      return ["Unable to Login.", null];
    return [response.statusText, response.data];
  } catch(err){
    return [err.message, null];
  }
}

export async function forgotPassword(payload){
  try {
    const response = await api.post("/auth/forgot-password", payload);
    if(response.status !== 200)
      return ["Unable to Login.", null];
    return [response.statusText, response.data];
  } catch(err){
    return [err.message, null];
  }
}

export async function verifyToken(token){
  try {
    const response = await api.get(`/auth/reset-password/validate?token=${token}`);
    if(response.status !== 200)
      return ["Invalid token.", null];
    return [null, response.data];
  } catch(err){
    return [err.message, null];
  }
}

export async function resetPassword(payload){
  try {
    const response = await api.post('/auth/reset-password', payload);
    if(response.status !== 200)
      return [response.statusText, null];
    return [null, response.data];
  } catch(err){
    return [err.message, null];
  }
}