import { useState, useEffect } from "react";
import { cashApi } from "../../lib/api";
import { formatDateTime, formatCurrency } from "../../lib/formatters";
import { Button, Spinner } from "../../components/ui";
import { DollarSign, ChevronUp, ChevronDown } from "lucide-react";

export function CashWidget() {
  const [shift, setShift] = useState(null);
  const [loading, setLoading] = useState(true);
  const [acting, setActing] = useState(false);
  const [expanded, setExpanded] = useState(false);

  const load = async () => {
    try {
      setLoading(true);
      const data = await cashApi.getActive();
      setShift(data);
    } catch {
      setShift(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const handleOpen = async () => {
    setActing(true);
    try {
      await cashApi.open();
      await load();
    } finally {
      setActing(false);
    }
  };

  const handleClose = async () => {
    if (!confirm("Fechar o caixa do dia?")) return;
    setActing(true);
    try {
      await cashApi.close();
      setShift(null);
    } finally {
      setActing(false);
    }
  };

  const isOpen = !!shift;

  return (
    <div
      style={{
        margin: "8px",
        borderRadius: "var(--radius)",
        border: `1px solid ${isOpen ? "rgba(16,185,129,0.25)" : "var(--border)"}`,
        background: isOpen ? "rgba(16,185,129,0.05)" : "var(--bg-600)",
        overflow: "hidden",
        transition: "all 0.2s",
      }}
    >
      {/* Header */}
      <button
        onClick={() => setExpanded((p) => !p)}
        style={{
          width: "100%",
          display: "flex",
          alignItems: "center",
          gap: "8px",
          padding: "10px 12px",
          background: "none",
          border: "none",
          cursor: "pointer",
          color: "var(--text-primary)",
        }}
      >
        <div
          style={{
            width: 28,
            height: 28,
            borderRadius: 6,
            background: isOpen ? "var(--success-bg)" : "var(--bg-500)",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            flexShrink: 0,
          }}
        >
          {loading ? (
            <Spinner size={14} />
          ) : (
            <DollarSign
              size={14}
              color={isOpen ? "var(--success)" : "var(--text-muted)"}
            />
          )}
        </div>
        <div style={{ flex: 1, textAlign: "left" }}>
          <p
            style={{
              fontSize: "11px",
              fontWeight: 600,
              color: isOpen ? "var(--success)" : "var(--text-muted)",
            }}
          >
            {loading
              ? "Verificando..."
              : isOpen
                ? "Caixa Aberto"
                : "Caixa Fechado"}
          </p>
        </div>
        {expanded ? (
          <ChevronDown size={14} color="var(--text-muted)" />
        ) : (
          <ChevronUp size={14} color="var(--text-muted)" />
        )}
      </button>

      {/* Expandable content */}
      {expanded && (
        <div
          style={{
            padding: "0 12px 12px",
            display: "flex",
            flexDirection: "column",
            gap: "8px",
          }}
        >
          {isOpen && shift && (
            <div style={{ fontSize: "11px", color: "var(--text-secondary)" }}>
              <p>Abertura: {formatDateTime(shift.openedAt)}</p>
              {shift.totalRevenue != null && (
                <p
                  style={{
                    marginTop: "3px",
                    color: "var(--success)",
                    fontWeight: 600,
                  }}
                >
                  Total: {formatCurrency(shift.totalRevenue)}
                </p>
              )}
            </div>
          )}

          {!isOpen ? (
            <Button
              variant="success"
              size="sm"
              onClick={handleOpen}
              disabled={acting || loading}
              style={{ width: "100%", justifyContent: "center" }}
            >
              {acting ? <Spinner size={13} /> : "Abrir Caixa"}
            </Button>
          ) : (
            <Button
              variant="danger"
              size="sm"
              onClick={handleClose}
              disabled={acting}
              style={{ width: "100%", justifyContent: "center" }}
            >
              {acting ? <Spinner size={13} /> : "Fechar Caixa"}
            </Button>
          )}
        </div>
      )}
    </div>
  );
}
