import "./QuantityControl.css";

export default function QuantityControl({
  quantity,
  onAdd,
  onRemove,
  size = "sm",
}) {
  return (
    <div className={`quantity-control quantity-control--${size}`}>
      {quantity > 0 && (
        <button
          className="quantity-control__btn quantity-control__btn--remove"
          onClick={(e) => {
            e.stopPropagation();
            onRemove();
          }}
          aria-label="Remover"
        >
          −
        </button>
      )}
      {quantity > 0 && (
        <span className="quantity-control__value">{quantity}</span>
      )}
      <button
        className="quantity-control__btn quantity-control__btn--add"
        onClick={(e) => {
          e.stopPropagation();
          onAdd();
        }}
        aria-label="Adicionar"
      >
        +
      </button>
    </div>
  );
}
