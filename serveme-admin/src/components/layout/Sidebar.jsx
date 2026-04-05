import { useState } from 'react'
import { NavLink, useLocation } from 'react-router-dom'
import {
  ClipboardList, UtensilsCrossed, LayoutGrid,
  Settings, ChevronDown, ChevronRight, Package, Tag,
} from 'lucide-react'
import { CashWidget } from '../../features/cash/CashWidget'

const NAV = [
  {
    key: 'orders',
    label: 'Pedidos',
    icon: ClipboardList,
    to: '/pedidos',
  },
  {
    key: 'menu',
    label: 'Cardápio',
    icon: UtensilsCrossed,
    to: '/cardapio',
  },
  {
    key: 'registrations',
    label: 'Cadastros',
    icon: LayoutGrid,
    children: [
      { key: 'products', label: 'Produtos', icon: Package, to: '/cadastros/produtos' },
      { key: 'categories', label: 'Categorias', icon: Tag, to: '/cadastros/categorias' },
    ],
  },
  {
    key: 'settings',
    label: 'Configurações',
    icon: Settings,
    to: '/configuracoes',
  },
]

export function Sidebar({ logo, companyName }) {
  const location = useLocation()
  const [expanded, setExpanded] = useState({ registrations: true })

  const toggle = (key) => setExpanded(p => ({ ...p, [key]: !p[key] }))

  const isActiveGroup = (children) =>
    children?.some(c => location.pathname.startsWith(c.to))

  return (
    <aside style={{
      width: 'var(--sidebar-width)',
      minWidth: 'var(--sidebar-width)',
      height: '100vh',
      background: 'var(--bg-800)',
      borderRight: '1px solid var(--border)',
      display: 'flex',
      flexDirection: 'column',
      position: 'sticky',
      top: 0,
      overflowY: 'auto',
    }}>
      {/* Logo */}
      <div style={{
        padding: '20px 16px 16px',
        borderBottom: '1px solid var(--border)',
        display: 'flex',
        alignItems: 'center',
        gap: '10px',
      }}>
        {logo
          ? <img src={logo} alt="logo" style={{ width: 36, height: 36, borderRadius: 8, objectFit: 'cover' }} />
          : (
            <div style={{
              width: 36, height: 36, borderRadius: 8,
              background: 'var(--blue-600)',
              display: 'flex', alignItems: 'center', justifyContent: 'center',
              fontSize: '16px', fontWeight: 700, color: '#fff', flexShrink: 0,
            }}>
              {(companyName || 'S')[0].toUpperCase()}
            </div>
          )
        }
        <div style={{ overflow: 'hidden' }}>
          <p style={{ fontSize: '13px', fontWeight: 600, color: 'var(--text-primary)', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
            {companyName || 'Serve-Me'}
          </p>
          <p style={{ fontSize: '10px', color: 'var(--text-muted)', marginTop: '1px' }}>Painel ADM</p>
        </div>
      </div>

      {/* Navigation */}
      <nav style={{ flex: 1, padding: '12px 8px' }}>
        {NAV.map(item => {
          if (item.children) {
            const open = expanded[item.key] || isActiveGroup(item.children)
            const active = isActiveGroup(item.children)
            return (
              <div key={item.key}>
                <button
                  onClick={() => toggle(item.key)}
                  style={{
                    width: '100%', display: 'flex', alignItems: 'center', gap: '10px',
                    padding: '9px 10px', borderRadius: 'var(--radius-sm)',
                    border: 'none', background: active ? 'var(--bg-500)' : 'transparent',
                    color: active ? 'var(--text-primary)' : 'var(--text-secondary)',
                    cursor: 'pointer', fontFamily: 'Poppins', fontSize: '13px', fontWeight: 500,
                    transition: 'var(--transition)', textAlign: 'left',
                  }}
                  onMouseEnter={e => { if (!active) e.currentTarget.style.background = 'var(--bg-600)' }}
                  onMouseLeave={e => { if (!active) e.currentTarget.style.background = 'transparent' }}
                >
                  <item.icon size={16} style={{ flexShrink: 0 }} />
                  <span style={{ flex: 1 }}>{item.label}</span>
                  {open ? <ChevronDown size={14} /> : <ChevronRight size={14} />}
                </button>
                {open && (
                  <div style={{ marginLeft: '10px', marginTop: '2px' }}>
                    {item.children.map(child => (
                      <NavLink
                        key={child.key}
                        to={child.to}
                        style={({ isActive }) => ({
                          display: 'flex', alignItems: 'center', gap: '10px',
                          padding: '8px 10px', borderRadius: 'var(--radius-sm)',
                          textDecoration: 'none', fontSize: '13px', fontWeight: isActive ? 500 : 400,
                          color: isActive ? 'var(--blue-300)' : 'var(--text-secondary)',
                          background: isActive ? 'var(--blue-glow)' : 'transparent',
                          transition: 'var(--transition)', marginBottom: '2px',
                        })}
                        onMouseEnter={e => e.currentTarget.style.background = 'var(--bg-600)'}
                        onMouseLeave={e => {
                          const isActive = location.pathname === child.to
                          e.currentTarget.style.background = isActive ? 'var(--blue-glow)' : 'transparent'
                        }}
                      >
                        <child.icon size={14} style={{ flexShrink: 0 }} />
                        {child.label}
                      </NavLink>
                    ))}
                  </div>
                )}
              </div>
            )
          }

          return (
            <NavLink
              key={item.key}
              to={item.to}
              style={({ isActive }) => ({
                display: 'flex', alignItems: 'center', gap: '10px',
                padding: '9px 10px', borderRadius: 'var(--radius-sm)',
                textDecoration: 'none', fontSize: '13px', fontWeight: isActive ? 500 : 400,
                color: isActive ? 'var(--blue-300)' : 'var(--text-secondary)',
                background: isActive ? 'var(--blue-glow)' : 'transparent',
                border: isActive ? '1px solid var(--border-blue)' : '1px solid transparent',
                transition: 'var(--transition)', marginBottom: '4px',
              })}
              onMouseEnter={e => {
                const isActive = location.pathname === item.to
                if (!isActive) e.currentTarget.style.background = 'var(--bg-600)'
              }}
              onMouseLeave={e => {
                const isActive = location.pathname === item.to
                e.currentTarget.style.background = isActive ? 'var(--blue-glow)' : 'transparent'
              }}
            >
              <item.icon size={16} style={{ flexShrink: 0 }} />
              {item.label}
            </NavLink>
          )
        })}
      </nav>

      {/* Cash Widget at bottom */}
      <CashWidget />
    </aside>
  )
}
