import axios from "axios";

const API_URL = (process.env.NODE_ENV === 'production') ?
    "http://backend:8080/api" : "http://localhost:8080/api";

const api = axios.create({baseURL: API_URL});

export default api;