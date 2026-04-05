import { X, Loader2 } from 'lucide-react'

// ─── Button ───────────────────────────────────────────────────────────────────
export function Button({
  children, variant = 'primary', size = 'md',
  onClick, disabled, type = 'button', style, className = ''
}) {
  const base = {
    display: 'inline-flex', alignItems: 'center', gap: '6px',
    fontFamily: 'Poppins, sans-serif', fontWeight: 500,
    border: 'none', borderRadius: 'var(--radius-sm)',
    cursor: disabled ? 'not-allowed' : 'pointer',
    opacity: disabled ? 0.5 : 1,
    transition: 'var(--transition)',
    whiteSpace: 'nowrap',
    fontSize: size === 'sm' ? '12px' : '13px',
    padding: size === 'sm' ? '5px 10px' : size === 'lg' ? '10px 20px' : '7px 14px',
  }

  const variants = {
    primary:  { background: 'var(--blue-500)', color: '#fff' },
    secondary:{ background: 'var(--bg-500)', color: 'var(--text-primary)', border: '1px solid var(--border)' },
    danger:   { background: 'var(--danger-bg)', color: 'var(--danger)', border: '1px solid rgba(239,68,68,0.2)' },
    success:  { background: 'var(--success-bg)', color: 'var(--success)', border: '1px solid rgba(16,185,129,0.2)' },
    ghost:    { background: 'transparent', color: 'var(--text-secondary)' },
  }

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      style={{ ...base, ...variants[variant], ...style }}
      className={className}
      onMouseEnter={e => { if (!disabled) e.currentTarget.style.filter = 'brightness(1.15)' }}
      onMouseLeave={e => { e.currentTarget.style.filter = '' }}
    >
      {children}
    </button>
  )
}

// ─── Badge ───────────────────────────────────────────────────────────────────
export function Badge({ label, color = 'var(--text-secondary)', bg = 'var(--bg-500)' }) {
  return (
    <span style={{
      display: 'inline-flex', alignItems: 'center',
      padding: '2px 10px', borderRadius: '99px',
      fontSize: '11px', fontWeight: 600,
      color, background: bg, whiteSpace: 'nowrap',
    }}>
      {label}
    </span>
  )
}

// ─── Toggle ──────────────────────────────────────────────────────────────────
export function Toggle({ value, onChange, disabled }) {
  return (
    <button
      onClick={() => !disabled && onChange(!value)}
      style={{
        width: '42px', height: '23px', borderRadius: '99px', border: 'none',
        background: value ? 'var(--blue-500)' : 'var(--bg-500)',
        cursor: disabled ? 'not-allowed' : 'pointer',
        position: 'relative', transition: '0.2s',
        flexShrink: 0,
      }}
    >
      <span style={{
        position: 'absolute', top: '3px',
        left: value ? '22px' : '3px',
        width: '17px', height: '17px', borderRadius: '50%',
        background: '#fff', transition: '0.2s',
        boxShadow: '0 1px 3px rgba(0,0,0,0.3)',
      }} />
    </button>
  )
}

// ─── Spinner ─────────────────────────────────────────────────────────────────
export function Spinner({ size = 20 }) {
  return (
    <Loader2
      size={size}
      style={{ animation: 'spin 0.8s linear infinite', color: 'var(--blue-400)' }}
    />
  )
}

// ─── EmptyState ───────────────────────────────────────────────────────────────
export function EmptyState({ icon: Icon, title, description }) {
  return (
    <div style={{
      display: 'flex', flexDirection: 'column', alignItems: 'center',
      justifyContent: 'center', gap: '10px', padding: '48px 24px',
      color: 'var(--text-muted)',
    }}>
      {Icon && <Icon size={36} strokeWidth={1.2} />}
      <p style={{ fontSize: '15px', fontWeight: 500, color: 'var(--text-secondary)' }}>{title}</p>
      {description && <p style={{ fontSize: '13px' }}>{description}</p>}
    </div>
  )
}

// ─── Modal ───────────────────────────────────────────────────────────────────
export function Modal({ open, onClose, title, children, width = 520 }) {
  if (!open) return null
  return (
    <div
      onClick={onClose}
      style={{
        position: 'fixed', inset: 0, zIndex: 1000,
        background: 'rgba(0,0,0,0.7)', backdropFilter: 'blur(4px)',
        display: 'flex', alignItems: 'center', justifyContent: 'center',
        padding: '16px',
      }}
    >
      <div
        onClick={e => e.stopPropagation()}
        className="animate-fade"
        style={{
          background: 'var(--bg-700)', borderRadius: 'var(--radius-lg)',
          border: '1px solid var(--border)', width: '100%', maxWidth: `${width}px`,
          boxShadow: 'var(--shadow)', overflow: 'hidden',
        }}
      >
        <div style={{
          display: 'flex', alignItems: 'center', justifyContent: 'space-between',
          padding: '16px 20px', borderBottom: '1px solid var(--border)',
        }}>
          <h3 style={{ fontSize: '15px', fontWeight: 600 }}>{title}</h3>
          <button onClick={onClose} style={{
            background: 'none', border: 'none', cursor: 'pointer',
            color: 'var(--text-muted)', display: 'flex', padding: '2px',
          }}>
            <X size={18} />
          </button>
        </div>
        <div style={{ padding: '20px' }}>{children}</div>
      </div>
    </div>
  )
}

// ─── Card ─────────────────────────────────────────────────────────────────────
export function Card({ children, style }) {
  return (
    <div style={{
      background: 'var(--bg-700)', border: '1px solid var(--border)',
      borderRadius: 'var(--radius)', padding: '20px',
      ...style,
    }}>
      {children}
    </div>
  )
}

// ─── Input ────────────────────────────────────────────────────────────────────
export function Input({ label, error, ...props }) {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}>
      {label && <label style={{ fontSize: '12px', fontWeight: 500, color: 'var(--text-secondary)' }}>{label}</label>}
      <input
        {...props}
        style={{
          background: 'var(--bg-600)', border: `1px solid ${error ? 'var(--danger)' : 'var(--border)'}`,
          borderRadius: 'var(--radius-sm)', padding: '8px 12px',
          color: 'var(--text-primary)', fontSize: '13px',
          fontFamily: 'Poppins, sans-serif', outline: 'none',
          transition: 'border-color 0.15s',
          width: '100%',
          ...props.style,
        }}
        onFocus={e => e.target.style.borderColor = 'var(--blue-400)'}
        onBlur={e => e.target.style.borderColor = error ? 'var(--danger)' : 'var(--border)'}
      />
      {error && <span style={{ fontSize: '11px', color: 'var(--danger)' }}>{error}</span>}
    </div>
  )
}

// ─── Select ───────────────────────────────────────────────────────────────────
export function Select({ label, error, children, ...props }) {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}>
      {label && <label style={{ fontSize: '12px', fontWeight: 500, color: 'var(--text-secondary)' }}>{label}</label>}
      <select
        {...props}
        style={{
          background: 'var(--bg-600)', border: `1px solid ${error ? 'var(--danger)' : 'var(--border)'}`,
          borderRadius: 'var(--radius-sm)', padding: '8px 12px',
          color: 'var(--text-primary)', fontSize: '13px',
          fontFamily: 'Poppins, sans-serif', outline: 'none', cursor: 'pointer',
          width: '100%',
          ...props.style,
        }}
      >
        {children}
      </select>
      {error && <span style={{ fontSize: '11px', color: 'var(--danger)' }}>{error}</span>}
    </div>
  )
}

// ─── Table ────────────────────────────────────────────────────────────────────
export function Table({ columns, data, renderRow, emptyState }) {
  return (
    <div style={{ overflowX: 'auto' }}>
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ borderBottom: '1px solid var(--border)' }}>
            {columns.map(col => (
              <th key={col.key} style={{
                padding: '10px 14px', textAlign: 'left',
                fontSize: '11px', fontWeight: 600,
                color: 'var(--text-muted)', textTransform: 'uppercase',
                letterSpacing: '0.05em', whiteSpace: 'nowrap',
                width: col.width,
              }}>
                {col.label}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.length === 0
            ? <tr><td colSpan={columns.length}>{emptyState}</td></tr>
            : data.map((row, i) => renderRow(row, i))
          }
        </tbody>
      </table>
    </div>
  )
}

export function Tr({ children, onClick }) {
  return (
    <tr
      onClick={onClick}
      style={{
        borderBottom: '1px solid var(--border)',
        cursor: onClick ? 'pointer' : 'default',
        transition: 'background 0.1s',
      }}
      onMouseEnter={e => { if (onClick) e.currentTarget.style.background = 'rgba(255,255,255,0.025)' }}
      onMouseLeave={e => { e.currentTarget.style.background = '' }}
    >
      {children}
    </tr>
  )
}

export function Td({ children, style }) {
  return (
    <td style={{ padding: '11px 14px', fontSize: '13px', color: 'var(--text-primary)', ...style }}>
      {children}
    </td>
  )
}
