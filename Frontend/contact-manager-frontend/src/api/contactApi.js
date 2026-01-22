import axios from "axios";

//const api = axios.create({baseURL: "http://localhost:8080", headers:{"Content-Type": "application/json"},});

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;



export const createcontact = (contact) => { 
    return axios.post(`${API_BASE_URL}/contacts`, contact);
}

export const getAllContacts = () => {
    return axios.get(`${API_BASE_URL}/contacts`);
}

export const getContactById = (id) => {
    return axios.get(`${API_BASE_URL}/contacts/${id}`);
}

export const updateContact = (id, data) => {
    return axios.put(`${API_BASE_URL}/contacts/${id}`,data);
}

export const deleteContact = (id) => {
    return axios.delete(`${API_BASE_URL}/contacts/${id}`);
}