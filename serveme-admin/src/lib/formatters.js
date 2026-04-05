export const formatCurrency = (value) =>
  new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(value ?? 0)

export const formatDate = (dateStr) => {
  if (!dateStr) return '—'
  const d = new Date(dateStr)
  return d.toLocaleDateString('pt-BR')
}

export const formatDateTime = (dateStr) => {
  if (!dateStr) return '—'
  const d = new Date(dateStr)
  return d.toLocaleString('pt-BR', {
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit',
  })
}

export const formatTime = (dateStr) => {
  if (!dateStr) return '—'
  const d = new Date(dateStr)
  return d.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })
}

export const ORDER_STATUS = {
  PENDING:     { label: 'Pendente',    color: '--warning',  bg: '--warning-bg' },
  IN_PROGRESS: { label: 'Em Preparo',  color: '--blue-400', bg: '--blue-glow'  },
  SERVED:      { label: 'Servido',     color: '--success',  bg: '--success-bg' },
  PAID:        { label: 'Pago',        color: '--purple',   bg: '--purple-bg'  },
  CANCELED:    { label: 'Cancelado',   color: '--danger',   bg: '--danger-bg'  },
}

export const STATUS_TABS = [
  { key: 'PENDING',     label: 'Pendentes'  },
  { key: 'IN_PROGRESS', label: 'Em Preparo' },
  { key: 'SERVED',      label: 'Servidos'   },
  { key: 'PAID',        label: 'Pagos'      },
  { key: 'CANCELED',    label: 'Cancelados' },
]

// Next valid status transitions
export const NEXT_STATUS = {
  PENDING:     'IN_PROGRESS',
  IN_PROGRESS: 'SERVED',
  SERVED:      'PAID',
}
