import { useNavigate } from "react-router-dom";
import { formatCurrency, formatTitle } from "../services/format";
import QuantityControl from "./QuantityControl";
import "./ProductCard.css";

export default function ProductCard({
  product,
  quantity,
  onAdd,
  onSetQuantity,
}) {
  const navigate = useNavigate();

  function handleCardClick(e) {
    // Não navega se clicou nos botões de quantidade
    if (e.target.closest(".quantity-control")) return;
    navigate(`/produto/${product.id}`);
  }

  return (
    <div className="product-card" onClick={handleCardClick}>
      <div className="product-card__image img-placeholder">
        {product.imageUrl ? (
          <img
            src={product.imageUrl}
            alt={product.name}
            style={{ width: "100%", height: "100%", objectFit: "cover" }}
          />
        ) : (
          "🍽️"
        )}
      </div>
      <div className="product-card__body">
        <p className="product-card__name">{formatTitle(product.name)}</p>
        <div className="product-card__footer">
          <span className="product-card__price">
            {formatCurrency(product.price)}
          </span>
          <QuantityControl
            quantity={quantity}
            onAdd={() => onAdd(product)}
            onRemove={() => onSetQuantity(product.id, quantity - 1)}
          />
        </div>
      </div>
    </div>
  );
}
