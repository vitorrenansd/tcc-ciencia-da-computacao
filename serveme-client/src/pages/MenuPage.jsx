import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import {
  fetchActiveCategories,
  fetchProductsByCategory,
  fetchRestaurantConfig,
} from "../services/api";
import CategoryTopbar from "../components/CategoryTopbar";
import ProductCard from "../components/ProductCard";
import CartBar from "../components/CartBar";
import "./MenuPage.css";

export default function MenuPage({ cart, addToCart, setQuantity }) {
  const location = useLocation();
  const [categories, setCategories] = useState([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);
  const [products, setProducts] = useState([]);
  const [loadingCategories, setLoadingCategories] = useState(true);
  const [loadingProducts, setLoadingProducts] = useState(false);
  const [error, setError] = useState(null);
  const [restaurantConfig, setRestaurantConfig] = useState(null);

  // Busca config do restaurante ao montar
  useEffect(() => {
    fetchRestaurantConfig()
      .then(setRestaurantConfig)
      .catch(() => {}); // falha silenciosa — mantém fallback no header
  }, []);

  // Carrega categorias ao montar
  useEffect(() => {
    fetchActiveCategories()
      .then((data) => {
        setCategories(data);
        if (data.length > 0) {
          const restored = location.state?.fromCategoryId;
          const validId =
            restored && data.find((c) => c.id === restored)
              ? restored
              : data[0].id;
          setSelectedCategoryId(validId);
        }
      })
      .catch(() => setError("Não foi possível carregar o cardápio."))
      .finally(() => setLoadingCategories(false));
  }, [location.state]);

  // Carrega produtos quando muda a categoria
  useEffect(() => {
    if (!selectedCategoryId) return;
    setLoadingProducts(true);
    fetchProductsByCategory(selectedCategoryId)
      .then(setProducts)
      .catch(() => setError("Erro ao carregar produtos."))
      .finally(() => setLoadingProducts(false));
  }, [selectedCategoryId]);

  function getCartQuantity(productId) {
    return cart.find((i) => i.productId === productId)?.quantity ?? 0;
  }

  if (loadingCategories) {
    return (
      <div className="page-container menu-loading">
        <div className="menu-loading__spinner" />
        <p>Carregando cardápio...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page-container menu-error">
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="page-container">
      {/* Header */}
      <header className="menu-header">
        <div className="menu-header__brand">
          {restaurantConfig?.iconUrl ? (
            <img
              src={restaurantConfig.iconUrl}
              alt="logo"
              className="menu-header__icon"
            />
          ) : (
            <span className="menu-header__logo">🍽️</span>
          )}
          <span className="menu-header__title">
            {restaurantConfig?.name ?? "Serve-me"}
          </span>
        </div>
      </header>

      <CategoryTopbar
        categories={categories}
        selectedId={selectedCategoryId}
        onSelect={setSelectedCategoryId}
      />

      <main className="menu-main">
        {loadingProducts ? (
          <div className="menu-products-loading">
            <div className="menu-loading__spinner" />
          </div>
        ) : products.length === 0 ? (
          <div className="menu-empty">
            <p>Nenhum produto disponível nesta categoria.</p>
          </div>
        ) : (
          <div className="product-grid">
            {products.map((product) => (
              <ProductCard
                key={product.id}
                product={product}
                quantity={getCartQuantity(product.id)}
                onAdd={() => addToCart(product)}
                onSetQuantity={setQuantity}
              />
            ))}
          </div>
        )}
      </main>

      <CartBar cart={cart} />
    </div>
  );
}
