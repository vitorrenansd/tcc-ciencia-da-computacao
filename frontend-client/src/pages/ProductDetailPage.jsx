import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchProductById } from "../services/api";
import { formatCurrency, formatTitle, formatText } from "../services/format";
import QuantityControl from "../components/QuantityControl";
import "./ProductDetailPage.css";

export default function ProductDetailPage({ cart, addToCart, setQuantity }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cartItem = cart.find((i) => i.productId === Number(id));
  const quantity = cartItem?.quantity ?? 0;

  useEffect(() => {
    fetchProductById(id)
      .then(setProduct)
      .catch(() => setError("Produto não encontrado."))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <div className="page-container detail-loading">
        <div className="spinner" />
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="page-container detail-error">
        <p>{error}</p>
        <button className="btn-secondary" onClick={() => navigate("/")}>
          Voltar
        </button>
      </div>
    );
  }

  return (
    <div className="page-container">
      {/* Imagem com botão voltar sobreposto */}
      <div className="detail-image img-placeholder">
        <button
          className="detail-back"
          onClick={() => navigate(-1)}
          aria-label="Voltar"
        >
          ←
        </button>
        {product.imageUrl ? (
          <img
            src={product.imageUrl}
            alt={product.name}
            className="detail-image__img"
          />
        ) : (
          <span className="detail-image__emoji">🍽️</span>
        )}
      </div>

      {/* Conteúdo */}
      <div className="detail-content">
        <h1 className="detail-name">{formatTitle(product.name)}</h1>

        {product.description && (
          <p className="detail-description">
            {formatText(product.description)}
          </p>
        )}

        <div className="detail-footer">
          <span className="detail-price">{formatCurrency(product.price)}</span>
          <QuantityControl
            quantity={quantity}
            size="lg"
            onAdd={() => addToCart(product)}
            onRemove={() => setQuantity(product.id, quantity - 1)}
          />
        </div>
      </div>
    </div>
  );
}
