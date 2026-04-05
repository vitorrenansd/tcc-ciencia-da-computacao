import { useNavigate } from "react-router-dom";
import { formatCurrency } from "../services/format";
import "./CartBar.css";

export default function CartBar({ cart }) {
  const navigate = useNavigate();

  const totalItems = cart.reduce((sum, i) => sum + i.quantity, 0);
  const totalPrice = cart.reduce((sum, i) => sum + i.price * i.quantity, 0);

  if (totalItems === 0) return null;

  return (
    <div className="cart-bar">
      <div className="cart-bar__total">
        <span className="cart-bar__label">TOTAL</span>
        <span className="cart-bar__value">{formatCurrency(totalPrice)}</span>
      </div>
      <button
        className="btn-primary cart-bar__btn"
        onClick={() => navigate("/resumo")}
      >
        CONFIRMAR ITENS
      </button>
    </div>
  );
}
