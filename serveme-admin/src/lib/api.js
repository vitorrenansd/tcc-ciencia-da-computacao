import axios from "axios";

const http = axios.create({
  baseURL: `${import.meta.env.VITE_API_BASE_URL}/api`,
  headers: { "Content-Type": "application/json" },
});

// ─── Orders ──────────────────────────────────────────────────────────────────
export const orderApi = {
  getByStatus: (status) =>
    http
      .get("/order", { params: status ? { status } : {} })
      .then((r) => r.data),

  getById: (id) => http.get(`/order/${id}`).then((r) => r.data),

  updateStatus: (id, status) => http.patch(`/order/${id}/status`, { status }),

  cancelItem: (orderId, itemId) =>
    http.patch(`/order/${orderId}/items/${itemId}/cancel`),
};

// ─── Products ────────────────────────────────────────────────────────────────
export const productApi = {
  getAll: () => http.get("/product").then((r) => r.data),

  getByCategory: (categoryId, availableOnly = false) =>
    http
      .get("/product", { params: { categoryId, availableOnly } })
      .then((r) => r.data),

  getByKeyword: (keyword) =>
    http.get("/product", { params: { keyword } }).then((r) => r.data),

  getById: (id) => http.get(`/product/${id}`).then((r) => r.data),

  create: (data) => http.post("/product", data),

  update: (id, data) => http.put(`/product/${id}`, data),

  delete: (id) => http.delete(`/product/${id}`),
};

// ─── Categories ───────────────────────────────────────────────────────────────
export const categoryApi = {
  getAll: (activeOnly = false) =>
    http
      .get("/product-category", {
        params: activeOnly ? { activeOnly: true } : {},
      })
      .then((r) => r.data),

  getByKeyword: (keyword) =>
    http.get("/product-category", { params: { keyword } }).then((r) => r.data),

  getById: (id) => http.get(`/product-category/${id}`).then((r) => r.data),

  create: (data) => http.post("/product-category", data),

  update: (id, data) => http.put(`/product-category/${id}`, data),

  delete: (id) => http.delete(`/product-category/${id}`),
};

// ─── Cash Shift ───────────────────────────────────────────────────────────────
export const cashApi = {
  getActive: () => http.get("/cash-shift/active").then((r) => r.data),

  open: () => http.post("/cash-shift/open").then((r) => r.data),

  close: () => http.put("/cash-shift/close"),

  getById: (id) => http.get(`/cash-shift/${id}`).then((r) => r.data),
};

// ─── Restaurant Config ────────────────────────────────────────────────────────
export const configApi = {
  get: () => http.get("/config").then((r) => r.data),

  update: (name) => http.put("/config", { name }),

  uploadIcon: (file) => {
    const formData = new FormData();
    formData.append("file", file);
    return http.post("/config/icon", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
};

export default http;
