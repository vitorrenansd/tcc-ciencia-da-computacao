import { useNavigate } from "react-router-dom";
import { formatCurrency, formatTitle } from "../services/format";
import "./OrderSummaryPage.css";

export default function OrderSummaryPage({ cart, setQuantity }) {
  const navigate = useNavigate();

  const totalPrice = cart.reduce((sum, i) => sum + i.price * i.quantity, 0);

  if (cart.length === 0) {
    navigate("/");
    return null;
  }

  return (
    <div className="page-container">
      {/* Header */}
      <header className="summary-header">
        <button
          className="summary-back"
          onClick={() => navigate(-1)}
          aria-label="Voltar"
        >
          ←
        </button>
        <h1 className="summary-header__title">Resumo do Pedido</h1>
      </header>

      {/* Lista de itens */}
      <main className="summary-main">
        <div className="summary-items">
          {cart.map((item) => (
            <div key={item.productId} className="summary-item">
              <div className="summary-item__image img-placeholder">🍽️</div>
              <div className="summary-item__info">
                <p className="summary-item__name">{formatTitle(item.name)}</p>
                <p className="summary-item__unit">
                  {formatCurrency(item.price)} cada
                </p>
              </div>
              <div className="summary-item__right">
                <p className="summary-item__total">
                  {formatCurrency(item.price * item.quantity)}
                </p>
                <div className="summary-item__qty">
                  <button
                    className="summary-qty-btn"
                    onClick={() =>
                      setQuantity(item.productId, item.quantity - 1)
                    }
                  >
                    −
                  </button>
                  <span>{item.quantity}</span>
                  <button
                    className="summary-qty-btn summary-qty-btn--add"
                    onClick={() =>
                      setQuantity(item.productId, item.quantity + 1)
                    }
                  >
                    +
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Total */}
        <div className="summary-total">
          <span className="summary-total__label">Total</span>
          <span className="summary-total__value">
            {formatCurrency(totalPrice)}
          </span>
        </div>
      </main>

      {/* Botão finalizar */}
      <div className="summary-footer">
        <button
          className="btn-primary btn-full"
          onClick={() => navigate("/pedido")}
        >
          FINALIZAR PEDIDO
        </button>
      </div>
    </div>
  );
}
