import { useState, useEffect, useCallback } from "react";
import { orderApi } from "../../lib/api";
import {
  formatDateTime,
  formatCurrency,
  STATUS_TABS,
  ORDER_STATUS,
  NEXT_STATUS,
} from "../../lib/formatters";
import {
  Button,
  Badge,
  Spinner,
  EmptyState,
  Modal,
  Table,
  Tr,
  Td,
} from "../../components/ui";
import {
  RefreshCw,
  ClipboardList,
  Eye,
  CheckCircle,
  XCircle,
} from "lucide-react";

export function OrdersPage() {
  const [activeStatus, setActiveStatus] = useState("PENDING");
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [acting, setActing] = useState(null);
  const [counts, setCounts] = useState({});
  const [selected, setSelected] = useState(null);
  const [detail, setDetail] = useState(null);
  const [detailLoading, setDetailLoading] = useState(false);

  const loadOrders = useCallback(async (status) => {
    setLoading(true);
    try {
      const data = await orderApi.getByStatus(status);
      setOrders(data);
    } finally {
      setLoading(false);
    }
  }, []);

  const loadAllCounts = useCallback(async () => {
    const statuses = STATUS_TABS.map((t) => t.key);
    const results = await Promise.allSettled(
      statuses.map((s) => orderApi.getByStatus(s)),
    );
    const c = {};
    results.forEach((r, i) => {
      c[statuses[i]] = r.status === "fulfilled" ? r.value.length : 0;
    });
    setCounts(c);
  }, []);

  useEffect(() => {
    loadOrders(activeStatus);
    loadAllCounts();
    // Auto-refresh every 30s
    const interval = setInterval(() => {
      loadOrders(activeStatus);
      loadAllCounts();
    }, 30000);
    return () => clearInterval(interval);
  }, [activeStatus, loadOrders, loadAllCounts]);

  const handleStatusChange = async (orderId, newStatus) => {
    setActing(orderId);
    try {
      await orderApi.updateStatus(orderId, newStatus);
      await loadOrders(activeStatus);
      await loadAllCounts();
    } finally {
      setActing(null);
    }
  };

  const openDetail = async (order) => {
    setSelected(order);
    setDetailLoading(true);
    try {
      const data = await orderApi.getById(order.id);
      console.log(data);
      setDetail(data);
    } finally {
      setDetailLoading(false);
    }
  };

  const statusInfo = (key) => ORDER_STATUS[key] || {};

  const columns = [
    { key: "id", label: "Pedido", width: 80 },
    { key: "tableNum", label: "Mesa", width: 80 },
    { key: "client", label: "Cliente", width: 160 },
    { key: "total", label: "Valor", width: 110 },
    { key: "date", label: "Horário", width: 140 },
    { key: "actions", label: "Ações", width: 180 },
  ];

  const nextStatus = NEXT_STATUS[activeStatus];
  const nextLabel = nextStatus ? ORDER_STATUS[nextStatus]?.label : null;

  return (
    <div
      className="animate-fade"
      style={{ display: "flex", flexDirection: "column", gap: "20px" }}
    >
      {/* Header */}
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          flexWrap: "wrap",
          gap: "10px",
        }}
      >
        <div>
          <h1 style={{ fontSize: "20px", fontWeight: 700 }}>Pedidos</h1>
          <p
            style={{
              fontSize: "12px",
              color: "var(--text-muted)",
              marginTop: "2px",
            }}
          >
            Pedidos do dia em tempo real
          </p>
        </div>
        <Button
          variant="ghost"
          size="sm"
          onClick={() => {
            loadOrders(activeStatus);
            loadAllCounts();
          }}
        >
          <RefreshCw size={14} /> Atualizar
        </Button>
      </div>

      {/* Status Tabs */}
      <div style={{ display: "flex", gap: "8px", flexWrap: "wrap" }}>
        {STATUS_TABS.map((tab) => {
          const info = statusInfo(tab.key);
          const active = activeStatus === tab.key;
          const count = counts[tab.key] ?? 0;
          return (
            <button
              key={tab.key}
              onClick={() => setActiveStatus(tab.key)}
              style={{
                display: "flex",
                alignItems: "center",
                gap: "7px",
                padding: "8px 14px",
                borderRadius: "var(--radius)",
                border: `1px solid ${active ? "var(--border-blue)" : "var(--border)"}`,
                background: active ? "var(--blue-glow)" : "var(--bg-700)",
                color: active ? "var(--blue-300)" : "var(--text-secondary)",
                cursor: "pointer",
                fontFamily: "Poppins",
                fontSize: "13px",
                fontWeight: active ? 600 : 400,
                transition: "var(--transition)",
              }}
            >
              {tab.label}
              <span
                style={{
                  background: active ? "var(--blue-500)" : "var(--bg-500)",
                  color: active ? "#fff" : "var(--text-muted)",
                  borderRadius: "99px",
                  fontSize: "11px",
                  fontWeight: 700,
                  padding: "1px 7px",
                  minWidth: "22px",
                  textAlign: "center",
                }}
              >
                {count}
              </span>
            </button>
          );
        })}
      </div>

      {/* Table */}
      <div
        style={{
          background: "var(--bg-700)",
          border: "1px solid var(--border)",
          borderRadius: "var(--radius)",
          overflow: "hidden",
        }}
      >
        {loading ? (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              padding: "48px",
            }}
          >
            <Spinner size={28} />
          </div>
        ) : (
          <Table
            columns={columns}
            data={orders}
            emptyState={
              <EmptyState
                icon={ClipboardList}
                title="Nenhum pedido"
                description={`Sem pedidos com status "${ORDER_STATUS[activeStatus]?.label}"`}
              />
            }
            renderRow={(order, i) => {
              const isActing = acting === order.id;
              return (
                <Tr key={order.id}>
                  <Td style={{ fontWeight: 600, color: "var(--blue-300)" }}>
                    #{order.id}
                  </Td>
                  <Td>{order.tableNumber ?? order.table ?? "—"}</Td>
                  <Td style={{ color: "var(--text-secondary)" }}>
                    {order.customerName ?? order.client ?? "—"}
                  </Td>
                  <Td style={{ fontWeight: 500 }}>
                    {formatCurrency(order.totalPrice ?? order.total)}
                  </Td>
                  <Td
                    style={{ color: "var(--text-secondary)", fontSize: "12px" }}
                  >
                    {formatDateTime(order.createdAt ?? order.dateTime)}
                  </Td>
                  <Td>
                    <div
                      style={{
                        display: "flex",
                        gap: "6px",
                        alignItems: "center",
                        flexWrap: "wrap",
                      }}
                    >
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => openDetail(order)}
                      >
                        <Eye size={13} />
                      </Button>
                      {nextStatus && (
                        <Button
                          variant="primary"
                          size="sm"
                          onClick={() =>
                            handleStatusChange(order.id, nextStatus)
                          }
                          disabled={isActing}
                        >
                          {isActing ? (
                            <Spinner size={12} />
                          ) : (
                            <CheckCircle size={13} />
                          )}
                          {nextLabel}
                        </Button>
                      )}
                      {activeStatus !== "CANCELED" &&
                        activeStatus !== "PAID" && (
                          <Button
                            variant="danger"
                            size="sm"
                            onClick={() =>
                              handleStatusChange(order.id, "CANCELED")
                            }
                            disabled={isActing}
                          >
                            <XCircle size={13} />
                          </Button>
                        )}
                    </div>
                  </Td>
                </Tr>
              );
            }}
          />
        )}
      </div>

      {/* Order Detail Modal */}
      <Modal
        open={!!selected}
        onClose={() => {
          setSelected(null);
          setDetail(null);
        }}
        title={`Pedido #${selected?.id}`}
        width={560}
      >
        {detailLoading ? (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              padding: "32px",
            }}
          >
            <Spinner size={28} />
          </div>
        ) : (
          detail && (
            <div
              style={{ display: "flex", flexDirection: "column", gap: "14px" }}
            >
              <div
                style={{
                  display: "grid",
                  gridTemplateColumns: "1fr 1fr",
                  gap: "10px",
                }}
              >
                <InfoRow label="Mesa" value={detail.tableNumber ?? "—"} />
                <InfoRow label="Cliente" value={detail.customerName ?? "—"} />
                <InfoRow
                  label="Horário"
                  value={formatDateTime(detail.createdAt)}
                />
                <InfoRow
                  label="Total"
                  value={formatCurrency(detail.totalPrice)}
                  highlight
                />
              </div>

              <div
                style={{
                  borderTop: "1px solid var(--border)",
                  paddingTop: "14px",
                }}
              >
                <p
                  style={{
                    fontSize: "12px",
                    fontWeight: 600,
                    color: "var(--text-muted)",
                    marginBottom: "10px",
                    textTransform: "uppercase",
                    letterSpacing: "0.04em",
                  }}
                >
                  Itens
                </p>
                {(detail.items ?? []).map((item) => (
                  <div
                    key={item.id}
                    style={{
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "space-between",
                      padding: "8px 0",
                      borderBottom: "1px solid var(--border)",
                    }}
                  >
                    <div>
                      <p style={{ fontSize: "13px", fontWeight: 500 }}>
                        {item.productName ?? item.name}
                      </p>
                      {item.observation && (
                        <p
                          style={{
                            fontSize: "11px",
                            color: "var(--text-muted)",
                          }}
                        >
                          Obs: {item.observation}
                        </p>
                      )}
                    </div>
                    <div
                      style={{
                        textAlign: "right",
                        display: "flex",
                        alignItems: "center",
                        gap: "12px",
                      }}
                    >
                      <span
                        style={{ fontSize: "12px", color: "var(--text-muted)" }}
                      >
                        x{item.quantity}
                      </span>
                      <span style={{ fontSize: "13px", fontWeight: 500 }}>
                        {formatCurrency(item.subtotal ?? item.price)}
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )
        )}
      </Modal>
    </div>
  );
}

function InfoRow({ label, value, highlight }) {
  return (
    <div
      style={{
        background: "var(--bg-600)",
        borderRadius: "var(--radius-sm)",
        padding: "10px 12px",
      }}
    >
      <p
        style={{
          fontSize: "11px",
          color: "var(--text-muted)",
          marginBottom: "3px",
        }}
      >
        {label}
      </p>
      <p
        style={{
          fontSize: "14px",
          fontWeight: highlight ? 600 : 500,
          color: highlight ? "var(--success)" : "var(--text-primary)",
        }}
      >
        {value}
      </p>
    </div>
  );
}
