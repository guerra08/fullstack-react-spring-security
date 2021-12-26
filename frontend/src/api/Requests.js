import axios from "axios";

const API_URL = "http://localhost:8080/api"

export async function Login(payload){
    const response = await axios.post(`${API_URL}/login`, payload);
    return response;
}

export async function SignUp(payload){
    const response = await axios.post(`${API_URL}/register`, payload);
    return response;
}