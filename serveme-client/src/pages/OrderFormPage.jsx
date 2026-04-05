import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createOrder } from "../services/api";
import { formatCurrency } from "../services/format";
import "./OrderFormPage.css";

export default function OrderFormPage({ cart, clearCart }) {
  const navigate = useNavigate();
  const [tableNumber, setTableNumber] = useState("");
  const [customerName, setCustomerName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const totalPrice = cart.reduce((sum, i) => sum + i.price * i.quantity, 0);

  if (cart.length === 0) {
    navigate("/");
    return null;
  }

  async function handleSubmit() {
    setError(null);

    if (
      !tableNumber ||
      isNaN(Number(tableNumber)) ||
      Number(tableNumber) <= 0
    ) {
      setError("Informe um número de mesa válido.");
      return;
    }
    if (!customerName.trim()) {
      setError("Informe seu nome.");
      return;
    }

    const payload = {
      tableNumber: Number(tableNumber),
      customerName: customerName.trim(),
      items: cart.map((i) => ({
        productId: i.productId,
        quantity: i.quantity,
        notes: i.notes ?? "",
      })),
    };

    setLoading(true);
    try {
      await createOrder(payload);
      clearCart();
      navigate("/sucesso");
    } catch (err) {
      setError(err.message ?? "Erro ao enviar pedido. Tente novamente.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      {/* Header */}
      <header className="form-header">
        <button
          className="form-back"
          onClick={() => navigate(-1)}
          aria-label="Voltar"
        >
          ←
        </button>
        <h1 className="form-header__title">Identificação</h1>
      </header>

      <main className="form-main">
        {/* Resumo compacto */}
        <div className="form-summary-card">
          <span className="form-summary-card__label">Total do pedido</span>
          <span className="form-summary-card__value">
            {formatCurrency(totalPrice)}
          </span>
        </div>

        {/* Campos */}
        <div className="form-fields">
          <div className="form-field">
            <label className="form-field__label" htmlFor="tableNumber">
              Número da Mesa
            </label>
            <input
              id="tableNumber"
              className="form-field__input"
              type="number"
              inputMode="numeric"
              placeholder="Ex: 10"
              value={tableNumber}
              onChange={(e) => setTableNumber(e.target.value)}
              min="1"
            />
          </div>

          <div className="form-field">
            <label className="form-field__label" htmlFor="customerName">
              Seu Nome
            </label>
            <input
              id="customerName"
              className="form-field__input"
              type="text"
              placeholder="Ex: João"
              value={customerName}
              onChange={(e) => setCustomerName(e.target.value)}
              maxLength={20}
            />
          </div>
        </div>

        {/* Erro */}
        {error && <div className="form-error">{error}</div>}
      </main>

      {/* Botão enviar */}
      <div className="form-footer">
        <button
          className="btn-primary btn-full"
          onClick={handleSubmit}
          disabled={loading}
        >
          {loading ? "ENVIANDO..." : "ENVIAR PEDIDO"}
        </button>
      </div>
    </div>
  );
}
