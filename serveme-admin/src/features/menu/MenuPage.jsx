import { useState, useEffect } from "react";
import { categoryApi, productApi } from "../../lib/api";
import { formatCurrency } from "../../lib/formatters";
import { Spinner, EmptyState, Toggle } from "../../components/ui";
import { UtensilsCrossed, Tag } from "lucide-react";

export function MenuPage() {
  const [categories, setCategories] = useState([]);
  const [selectedCat, setSelectedCat] = useState(null);
  const [products, setProducts] = useState([]);
  const [catLoading, setCatLoading] = useState(true);
  const [prodLoading, setProdLoading] = useState(false);
  const [toggling, setToggling] = useState(null);

  useEffect(() => {
    categoryApi
      .getAll(true)
      .then((data) => {
        setCategories(data);
        if (data.length > 0) setSelectedCat(data[0]);
      })
      .finally(() => setCatLoading(false));
  }, []);

  useEffect(() => {
    if (!selectedCat) return;
    setProdLoading(true);
    productApi
      .getByCategory(selectedCat.id)
      .then(setProducts)
      .finally(() => setProdLoading(false));
  }, [selectedCat]);

  const handleToggleAvailable = async (product) => {
    setToggling(product.id);
    try {
      await productApi.update(product.id, {
        categoryId: product.categoryId ?? selectedCat.id,
        name: product.name,
        description: product.description,
        price: product.price,
        active: product.active ?? true,
        available: !product.available,
      });
      setProducts((prev) =>
        prev.map((p) =>
          p.id === product.id ? { ...p, available: !p.available } : p,
        ),
      );
    } finally {
      setToggling(null);
    }
  };

  return (
    <div
      className="animate-fade"
      style={{ display: "flex", flexDirection: "column", gap: "20px" }}
    >
      {/* Header */}
      <div>
        <h1 style={{ fontSize: "20px", fontWeight: 700 }}>Cardápio</h1>
        <p
          style={{
            fontSize: "12px",
            color: "var(--text-muted)",
            marginTop: "2px",
          }}
        >
          Gerencie a disponibilidade dos produtos por categoria
        </p>
      </div>

      <div
        style={{
          display: "flex",
          gap: "16px",
          alignItems: "flex-start",
          flexWrap: "wrap",
        }}
      >
        {/* Categories panel */}
        <div
          style={{
            background: "var(--bg-700)",
            border: "1px solid var(--border)",
            borderRadius: "var(--radius)",
            overflow: "hidden",
            width: "200px",
            minWidth: "160px",
            flexShrink: 0,
          }}
        >
          <div
            style={{
              padding: "12px 16px",
              borderBottom: "1px solid var(--border)",
            }}
          >
            <p
              style={{
                fontSize: "11px",
                fontWeight: 600,
                color: "var(--text-muted)",
                textTransform: "uppercase",
                letterSpacing: "0.05em",
              }}
            >
              Categorias
            </p>
          </div>

          {catLoading ? (
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                padding: "24px",
              }}
            >
              <Spinner />
            </div>
          ) : categories.length === 0 ? (
            <EmptyState icon={Tag} title="Sem categorias" />
          ) : (
            categories.map((cat) => {
              const active = selectedCat?.id === cat.id;
              return (
                <button
                  key={cat.id}
                  onClick={() => setSelectedCat(cat)}
                  style={{
                    width: "100%",
                    padding: "11px 16px",
                    border: "none",
                    background: active ? "var(--blue-glow)" : "transparent",
                    color: active ? "var(--blue-300)" : "var(--text-secondary)",
                    fontFamily: "Poppins",
                    fontSize: "13px",
                    fontWeight: active ? 600 : 400,
                    cursor: "pointer",
                    textAlign: "left",
                    transition: "var(--transition)",
                    borderLeft: `2px solid ${active ? "var(--blue-400)" : "transparent"}`,
                  }}
                  onMouseEnter={(e) => {
                    if (!active)
                      e.currentTarget.style.background = "var(--bg-600)";
                  }}
                  onMouseLeave={(e) => {
                    if (!active)
                      e.currentTarget.style.background = "transparent";
                  }}
                >
                  {cat.name}
                </button>
              );
            })
          )}
        </div>

        {/* Products panel */}
        <div
          style={{
            flex: 1,
            minWidth: 0,
            background: "var(--bg-700)",
            border: "1px solid var(--border)",
            borderRadius: "var(--radius)",
            overflow: "hidden",
          }}
        >
          <div
            style={{
              padding: "12px 16px",
              borderBottom: "1px solid var(--border)",
              display: "flex",
              alignItems: "center",
              justifyContent: "space-between",
            }}
          >
            <p
              style={{
                fontSize: "11px",
                fontWeight: 600,
                color: "var(--text-muted)",
                textTransform: "uppercase",
                letterSpacing: "0.05em",
              }}
            >
              {selectedCat ? selectedCat.name : "Produtos"}
            </p>
            <span style={{ fontSize: "11px", color: "var(--text-muted)" }}>
              {products.length} produto{products.length !== 1 ? "s" : ""}
            </span>
          </div>

          {!selectedCat ? (
            <EmptyState icon={Tag} title="Selecione uma categoria" />
          ) : prodLoading ? (
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                padding: "48px",
              }}
            >
              <Spinner size={28} />
            </div>
          ) : products.length === 0 ? (
            <EmptyState
              icon={UtensilsCrossed}
              title="Nenhum produto"
              description="Esta categoria não possui produtos"
            />
          ) : (
            <div>
              {/* Column headers */}
              <div
                style={{
                  display: "grid",
                  gridTemplateColumns: "1fr auto auto",
                  padding: "8px 16px",
                  borderBottom: "1px solid var(--border)",
                  gap: "16px",
                }}
              >
                <span
                  style={{
                    fontSize: "11px",
                    color: "var(--text-muted)",
                    fontWeight: 600,
                    textTransform: "uppercase",
                    letterSpacing: "0.04em",
                  }}
                >
                  Nome
                </span>
                <span
                  style={{
                    fontSize: "11px",
                    color: "var(--text-muted)",
                    fontWeight: 600,
                    textTransform: "uppercase",
                    letterSpacing: "0.04em",
                    textAlign: "right",
                  }}
                >
                  Preço
                </span>
                <span
                  style={{
                    fontSize: "11px",
                    color: "var(--text-muted)",
                    fontWeight: 600,
                    textTransform: "uppercase",
                    letterSpacing: "0.04em",
                    textAlign: "center",
                    minWidth: "80px",
                  }}
                >
                  Disponível
                </span>
              </div>

              {products.map((product) => (
                <div
                  key={product.id}
                  style={{
                    display: "grid",
                    gridTemplateColumns: "1fr auto auto",
                    padding: "12px 16px",
                    borderBottom: "1px solid var(--border)",
                    alignItems: "center",
                    gap: "16px",
                    opacity: product.available ? 1 : 0.55,
                    transition: "opacity 0.2s",
                  }}
                >
                  <div>
                    <p style={{ fontSize: "13px", fontWeight: 500 }}>
                      {product.name}
                    </p>
                    {product.description && (
                      <p
                        style={{
                          fontSize: "11px",
                          color: "var(--text-muted)",
                          marginTop: "2px",
                        }}
                      >
                        {product.description}
                      </p>
                    )}
                  </div>
                  <span
                    style={{
                      fontSize: "13px",
                      fontWeight: 500,
                      color: "var(--text-secondary)",
                      textAlign: "right",
                    }}
                  >
                    {formatCurrency(product.price)}
                  </span>
                  <div
                    style={{
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      minWidth: "80px",
                    }}
                  >
                    {toggling === product.id ? (
                      <Spinner size={16} />
                    ) : (
                      <Toggle
                        value={product.available}
                        onChange={() => handleToggleAvailable(product)}
                      />
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
