const BASE_URL = import.meta.env.VITE_API_URL;

// Busca todas as categorias ativas
export async function fetchActiveCategories() {
  const res = await fetch(`${BASE_URL}/product-category?activeOnly=true`);
  if (!res.ok) throw new Error("Erro ao buscar categorias");
  return res.json();
}

// Busca produtos disponíveis de uma categoria
export async function fetchProductsByCategory(categoryId) {
  const res = await fetch(
    `${BASE_URL}/product?categoryId=${categoryId}&availableOnly=true`,
  );
  if (!res.ok) throw new Error("Erro ao buscar produtos");
  return res.json();
}

// Busca detalhes de um produto pelo ID
export async function fetchProductById(id) {
  const res = await fetch(`${BASE_URL}/product/${id}`);
  if (!res.ok) throw new Error("Produto não encontrado");
  return res.json();
}

// Envia o pedido para o backend
export async function createOrder(payload) {
  const res = await fetch(`${BASE_URL}/order`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || "Erro ao enviar pedido");
  }
  return res.json();
}
