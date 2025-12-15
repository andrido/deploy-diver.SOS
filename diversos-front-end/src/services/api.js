import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:5173/api";


const api = axios.create({
  baseURL: API_BASE_URL
});

const toTitleCase = (str) => str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();

const transformJob = (job) => ({
  title: job.titulo,
  company: job.empresa,
  location: job.cidade,
  work_mode: toTitleCase(job.modalidade),
  description: job.descricao,
  status: job.status,
  type: job.tipo,
  id: job.id,
  createdAt: job.dataCriacao,
  deadline: job.dataLimite,
  jobLink: job.linkDaVaga
});

export const getJobOpenings = async () => {
  const response = await api.get("/vagas");
  const transformedData = response.data.map(transformJob);
  return { ...response, data: transformedData };
}

export const searchJobOpenings = async (params) => {
  // ATUALIZADO DE "/vagas/buscar" para "/vagas/filtro"
  const response = await api.get("/vagas/filtro", { params });
  console.log(response)
  const transformedData = response.data.map(transformJob);
  return { ...response, data: transformedData };
}

export const getJobById = async (id) => {
  const response = await api.get(`/vagas/${id}`);
  return {
    ...response,
    data: transformJob(response.data)
  };
}