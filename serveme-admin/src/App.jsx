import { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { configApi } from "./lib/api";
import { Sidebar } from "./components/layout/Sidebar";
import { OrdersPage } from "./features/orders/OrdersPage";
import { MenuPage } from "./features/menu/MenuPage";
import { ProductsPage } from "./features/products/ProductsPage";
import { CategoriesPage } from "./features/categories/CategoriesPage";
import { SettingsPage } from "./features/settings/SettingsPage";

/*
  AUTH HOOK PLACEHOLDER
  When ready to add login, create useAuth() here and wrap routes
  with a <ProtectedRoute> that redirects to /login if !authenticated.
  The Sidebar can receive user data from that context.
*/

export default function App() {
  const [settings, setSettings] = useState({ name: "", logo: null });

  useEffect(() => {
    configApi
      .get()
      .then((data) => setSettings({ name: data.name, logo: data.iconUrl }))
      .catch(() => {}); // sidebar mantém fallback se API falhar
  }, []);

  return (
    <BrowserRouter>
      <div
        style={{
          display: "flex",
          height: "100vh",
          overflow: "hidden",
          background: "var(--bg-900)",
        }}
      >
        <Sidebar logo={settings.logo} companyName={settings.name} />

        <main
          style={{
            flex: 1,
            overflowY: "auto",
            padding: "28px 32px",
          }}
        >
          <Routes>
            <Route path="/" element={<Navigate to="/pedidos" replace />} />
            <Route path="/pedidos" element={<OrdersPage />} />
            <Route path="/cardapio" element={<MenuPage />} />
            <Route
              path="/cadastros"
              element={<Navigate to="/cadastros/produtos" replace />}
            />
            <Route path="/cadastros/produtos" element={<ProductsPage />} />
            <Route path="/cadastros/categorias" element={<CategoriesPage />} />
            <Route
              path="/configuracoes"
              element={<SettingsPage onSettingsChange={setSettings} />}
            />
            <Route path="*" element={<Navigate to="/pedidos" replace />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}
