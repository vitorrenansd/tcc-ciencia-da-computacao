// Formata número para moeda brasileira: R$ 1.234,56
export function formatCurrency(value) {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value ?? 0);
}

// Cada palavra com primeira letra maiúscula: "hamburguer simples" → "Hamburguer Simples"
export function formatTitle(text) {
  if (!text) return ''
  return text
    .toLowerCase()
    .split(' ')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
    .join(' ')
}

// Apenas primeira letra do texto maiúscula: "DESCRICAO TESTE" → "Descricao teste"
export function formatText(text) {
  if (!text) return ''
  return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase()
}