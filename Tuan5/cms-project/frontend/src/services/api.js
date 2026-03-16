import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL || '/api';

const api = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' },
});

export const postService = {
  getAll: () => api.get('/posts'),
  getById: (id) => api.get(`/posts/${id}`),
  getPublished: () => api.get('/posts/published'),
  search: (keyword) => api.get(`/posts/search?keyword=${keyword}`),
  create: (data) => api.post('/posts', data),
  update: (id, data) => api.put(`/posts/${id}`, data),
  publish: (id) => api.patch(`/posts/${id}/publish`),
  delete: (id) => api.delete(`/posts/${id}`),
};

export const pluginService = {
  getAll: () => api.get('/plugins'),
};
