import { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import MenuPage from "./pages/MenuPage";
import ProductDetailPage from "./pages/ProductDetailPage";
import OrderSummaryPage from "./pages/OrderSummaryPage";
import OrderFormPage from "./pages/OrderFormPage";
import OrderSuccessPage from "./pages/OrderSuccessPage";

export default function App() {
  // Carrinho: [{ productId, name, price, quantity, notes }]
  const [cart, setCart] = useState([]);

  // Adiciona ou incrementa item no carrinho
  function addToCart(product, quantity = 1, notes = "") {
    setCart((prev) => {
      const existing = prev.find((i) => i.productId === product.id);
      if (existing) {
        return prev.map((i) =>
          i.productId === product.id
            ? { ...i, quantity: i.quantity + quantity }
            : i,
        );
      }
      return [
        ...prev,
        {
          productId: product.id,
          name: product.name,
          price: product.price,
          quantity,
          notes,
          imageUrl: product.imageUrl ?? null,
        },
      ];
    });
  }

  // Define quantidade exata de um item (0 = remove)
  function setQuantity(productId, quantity) {
    if (quantity <= 0) {
      setCart((prev) => prev.filter((i) => i.productId !== productId));
    } else {
      setCart((prev) =>
        prev.map((i) => (i.productId === productId ? { ...i, quantity } : i)),
      );
    }
  }

  // Limpa o carrinho após pedido enviado
  function clearCart() {
    setCart([]);
  }

  const cartProps = { cart, addToCart, setQuantity, clearCart };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MenuPage {...cartProps} />} />
        <Route
          path="/produto/:id"
          element={<ProductDetailPage {...cartProps} />}
        />
        <Route path="/resumo" element={<OrderSummaryPage {...cartProps} />} />
        <Route path="/pedido" element={<OrderFormPage {...cartProps} />} />
        <Route
          path="/sucesso"
          element={<OrderSuccessPage clearCart={clearCart} />}
        />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}
