import { useState, useEffect, useCallback } from "react";
import { productApi, categoryApi } from "../../lib/api";
import { formatCurrency } from "../../lib/formatters";
import {
  Button,
  Input,
  Select,
  Modal,
  Spinner,
  EmptyState,
  Table,
  Tr,
  Td,
  Badge,
  Toggle,
} from "../../components/ui";
import { Plus, Search, Package, Trash2, Pencil } from "lucide-react";

const EMPTY_FORM = {
  name: "",
  categoryId: "",
  description: "",
  price: "",
  active: true,
  available: true,
};

export function ProductsPage() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [filterCat, setFilterCat] = useState("");
  const [modal, setModal] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState(EMPTY_FORM);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);
  const [deleting, setDeleting] = useState(null);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      let data;
      if (search.trim()) data = await productApi.getByKeyword(search.trim());
      else if (filterCat)
        data = await productApi.getByCategory(Number(filterCat));
      else data = await productApi.getAll();
      setProducts(data);
    } finally {
      setLoading(false);
    }
  }, [search, filterCat]);

  useEffect(() => {
    categoryApi.getAll().then(setCategories);
  }, []);

  useEffect(() => {
    const timer = setTimeout(load, 350);
    return () => clearTimeout(timer);
  }, [load]);

  const openNew = () => {
    setEditing(null);
    setForm(EMPTY_FORM);
    setErrors({});
    setModal(true);
  };

  const openEdit = async (product) => {
    setEditing(product);
    setForm({
      name: product.name,
      categoryId: String(product.categoryId ?? ""),
      description: product.description ?? "",
      price: String(product.price ?? ""),
      active: product.active ?? true,
      available: product.available ?? true,
    });
    setErrors({});
    setModal(true);
  };

  const validate = () => {
    const e = {};
    if (!form.name.trim()) e.name = "Nome obrigatório";
    if (!form.categoryId) e.categoryId = "Categoria obrigatória";
    if (!form.price || isNaN(Number(form.price))) e.price = "Preço inválido";
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const handleSave = async () => {
    if (!validate()) return;
    setSaving(true);
    try {
      const payload = {
        name: form.name.trim(),
        categoryId: Number(form.categoryId),
        description: form.description.trim(),
        price: Number(form.price),
        active: form.active,
        available: form.available,
      };
      if (editing) await productApi.update(editing.id, payload);
      else await productApi.create(payload);
      setModal(false);
      await load();
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (product) => {
    if (!confirm(`Excluir "${product.name}"?`)) return;
    setDeleting(product.id);
    try {
      await productApi.delete(product.id);
      await load();
    } finally {
      setDeleting(null);
    }
  };

  const f = (field) => (val) => setForm((p) => ({ ...p, [field]: val }));

  const getCategoryName = (id) =>
    categories.find((c) => c.id === id)?.name ?? "—";

  const columns = [
    { key: "id", label: "ID", width: 60 },
    { key: "name", label: "Nome", width: 200 },
    { key: "category", label: "Categoria", width: 130 },
    { key: "desc", label: "Descrição" },
    { key: "price", label: "Preço", width: 100 },
    { key: "status", label: "Status", width: 130 },
    { key: "actions", label: "Ações", width: 100 },
  ];

  return (
    <div
      className="animate-fade"
      style={{ display: "flex", flexDirection: "column", gap: "20px" }}
    >
      {/* Header */}
      <div
        style={{
          display: "flex",
          alignItems: "flex-start",
          justifyContent: "space-between",
          flexWrap: "wrap",
          gap: "12px",
        }}
      >
        <div>
          <h1 style={{ fontSize: "20px", fontWeight: 700 }}>Produtos</h1>
          <p
            style={{
              fontSize: "12px",
              color: "var(--text-muted)",
              marginTop: "2px",
            }}
          >
            Cadastro e gestão de produtos
          </p>
        </div>
        <Button onClick={openNew}>
          <Plus size={14} /> Novo Produto
        </Button>
      </div>

      {/* Filters */}
      <div style={{ display: "flex", gap: "10px", flexWrap: "wrap" }}>
        <div style={{ position: "relative", flex: "1", minWidth: "180px" }}>
          <Search
            size={14}
            style={{
              position: "absolute",
              left: "10px",
              top: "50%",
              transform: "translateY(-50%)",
              color: "var(--text-muted)",
              pointerEvents: "none",
            }}
          />
          <input
            placeholder="Buscar por nome..."
            value={search}
            onChange={(e) => {
              setSearch(e.target.value);
              setFilterCat("");
            }}
            style={{
              width: "100%",
              padding: "8px 12px 8px 32px",
              background: "var(--bg-600)",
              border: "1px solid var(--border)",
              borderRadius: "var(--radius-sm)",
              color: "var(--text-primary)",
              fontSize: "13px",
              fontFamily: "Poppins",
              outline: "none",
            }}
          />
        </div>
        <select
          value={filterCat}
          onChange={(e) => {
            setFilterCat(e.target.value);
            setSearch("");
          }}
          style={{
            padding: "8px 12px",
            background: "var(--bg-600)",
            border: "1px solid var(--border)",
            borderRadius: "var(--radius-sm)",
            color: filterCat ? "var(--text-primary)" : "var(--text-muted)",
            fontSize: "13px",
            fontFamily: "Poppins",
            cursor: "pointer",
            outline: "none",
          }}
        >
          <option value="">Todas as categorias</option>
          {categories.map((c) => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </select>
      </div>

      {/* Table */}
      <div
        style={{
          background: "var(--bg-700)",
          border: "1px solid var(--border)",
          borderRadius: "var(--radius)",
          overflow: "hidden",
        }}
      >
        {loading ? (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              padding: "48px",
            }}
          >
            <Spinner size={28} />
          </div>
        ) : (
          <Table
            columns={columns}
            data={products}
            emptyState={
              <EmptyState icon={Package} title="Nenhum produto encontrado" />
            }
            renderRow={(p) => (
              <Tr key={p.id}>
                <Td style={{ color: "var(--text-muted)", fontSize: "12px" }}>
                  {p.id}
                </Td>
                <Td style={{ fontWeight: 500 }}>{p.name}</Td>
                <Td>
                  <Badge
                    label={getCategoryName(p.categoryId)}
                    color="var(--blue-300)"
                    bg="var(--blue-glow)"
                  />
                </Td>
                <Td
                  style={{
                    color: "var(--text-muted)",
                    fontSize: "12px",
                    maxWidth: "240px",
                  }}
                >
                  <span
                    style={{
                      display: "block",
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      whiteSpace: "nowrap",
                    }}
                  >
                    {p.description || "—"}
                  </span>
                </Td>
                <Td style={{ fontWeight: 500 }}>{formatCurrency(p.price)}</Td>
                <Td>
                  <div
                    style={{ display: "flex", gap: "6px", flexWrap: "wrap" }}
                  >
                    {p.active !== false ? (
                      <Badge
                        label="Ativo"
                        color="var(--success)"
                        bg="var(--success-bg)"
                      />
                    ) : (
                      <Badge
                        label="Inativo"
                        color="var(--danger)"
                        bg="var(--danger-bg)"
                      />
                    )}
                    {p.available ? (
                      <Badge
                        label="Disponível"
                        color="var(--blue-300)"
                        bg="var(--blue-glow)"
                      />
                    ) : (
                      <Badge
                        label="Indisponível"
                        color="var(--text-muted)"
                        bg="var(--bg-500)"
                      />
                    )}
                  </div>
                </Td>
                <Td>
                  <div style={{ display: "flex", gap: "6px" }}>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => openEdit(p)}
                    >
                      <Pencil size={13} />
                    </Button>
                    <Button
                      variant="danger"
                      size="sm"
                      onClick={() => handleDelete(p)}
                      disabled={deleting === p.id}
                    >
                      {deleting === p.id ? (
                        <Spinner size={12} />
                      ) : (
                        <Trash2 size={13} />
                      )}
                    </Button>
                  </div>
                </Td>
              </Tr>
            )}
          />
        )}
      </div>

      {/* Modal */}
      <Modal
        open={modal}
        onClose={() => setModal(false)}
        title={editing ? "Editar Produto" : "Novo Produto"}
      >
        <div style={{ display: "flex", flexDirection: "column", gap: "14px" }}>
          <Input
            label="Nome *"
            value={form.name}
            onChange={(e) => f("name")(e.target.value)}
            error={errors.name}
            placeholder="Ex: X-Bacon Duplo"
          />
          <Select
            label="Categoria *"
            value={form.categoryId}
            onChange={(e) => f("categoryId")(e.target.value)}
            error={errors.categoryId}
          >
            <option value="">Selecione...</option>
            {categories.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </Select>
          <Input
            label="Descrição"
            value={form.description}
            onChange={(e) => f("description")(e.target.value)}
            placeholder="Ingredientes, detalhes..."
          />
          <Input
            label="Preço (R$) *"
            type="number"
            step="0.01"
            value={form.price}
            onChange={(e) => f("price")(e.target.value)}
            error={errors.price}
            placeholder="0.00"
          />
          <div style={{ display: "flex", gap: "20px" }}>
            <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
              <Toggle value={form.active} onChange={f("active")} />
              <span
                style={{ fontSize: "13px", color: "var(--text-secondary)" }}
              >
                Ativo
              </span>
            </div>
            <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
              <Toggle value={form.available} onChange={f("available")} />
              <span
                style={{ fontSize: "13px", color: "var(--text-secondary)" }}
              >
                Disponível
              </span>
            </div>
          </div>
          <div
            style={{
              display: "flex",
              justifyContent: "flex-end",
              gap: "8px",
              marginTop: "4px",
            }}
          >
            <Button variant="secondary" onClick={() => setModal(false)}>
              Cancelar
            </Button>
            <Button onClick={handleSave} disabled={saving}>
              {saving ? <Spinner size={14} /> : null}
              {editing ? "Salvar" : "Criar Produto"}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
