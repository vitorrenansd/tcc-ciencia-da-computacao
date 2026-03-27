import { useNavigate } from "react-router-dom";
import "./OrderSuccessPage.css";

export default function OrderSuccessPage() {
  const navigate = useNavigate();

  return (
    <div className="page-container success-page">
      <div className="success-content">
        <div className="success-icon">✓</div>
        <h1 className="success-title">Pedido enviado!</h1>
        <p className="success-subtitle">
          Seu pedido foi recebido com sucesso.
          <br />
          Em breve estará sendo preparado.
        </p>
        <button
          className="btn-primary success-btn"
          onClick={() => navigate("/")}
        >
          FAZER NOVO PEDIDO
        </button>
      </div>
    </div>
  );
}
