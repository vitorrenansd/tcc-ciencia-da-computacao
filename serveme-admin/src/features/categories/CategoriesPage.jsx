import { useState, useEffect, useCallback } from "react";
import { categoryApi } from "../../lib/api";
import {
  Button,
  Input,
  Modal,
  Spinner,
  EmptyState,
  Table,
  Tr,
  Td,
  Badge,
  Toggle,
} from "../../components/ui";
import { Plus, Search, Tag, Pencil, Trash2 } from "lucide-react";

const EMPTY_FORM = { name: "", active: true };

export function CategoriesPage() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [modal, setModal] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState(EMPTY_FORM);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);
  const [deleting, setDeleting] = useState(null);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const data = search.trim()
        ? await categoryApi.getByKeyword(search.trim())
        : await categoryApi.getAll();
      setCategories(data);
    } finally {
      setLoading(false);
    }
  }, [search]);

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

  const openEdit = (cat) => {
    setEditing(cat);
    setForm({ name: cat.name, active: cat.active ?? true });
    setErrors({});
    setModal(true);
  };

  const validate = () => {
    const e = {};
    if (!form.name.trim()) e.name = "Nome obrigatório";
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const handleSave = async () => {
    if (!validate()) return;
    setSaving(true);
    try {
      const payload = { name: form.name.trim(), active: form.active };
      if (editing) await categoryApi.update(editing.id, payload);
      else await categoryApi.create(payload);
      setModal(false);
      await load();
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (cat) => {
    if (
      !confirm(
        `Excluir categoria "${cat.name}"? Isso pode afetar produtos vinculados.`,
      )
    )
      return;
    setDeleting(cat.id);
    try {
      await categoryApi.delete(cat.id);
      await load();
    } finally {
      setDeleting(null);
    }
  };

  const columns = [
    { key: "id", label: "ID", width: 60 },
    { key: "name", label: "Nome" },
    { key: "status", label: "Status", width: 100 },
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
          <h1 style={{ fontSize: "20px", fontWeight: 700 }}>Categorias</h1>
          <p
            style={{
              fontSize: "12px",
              color: "var(--text-muted)",
              marginTop: "2px",
            }}
          >
            Cadastro de categorias do cardápio
          </p>
        </div>
        <Button onClick={openNew}>
          <Plus size={14} /> Nova Categoria
        </Button>
      </div>

      {/* Search */}
      <div style={{ position: "relative", maxWidth: "320px" }}>
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
          placeholder="Buscar categoria..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
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
            data={categories}
            emptyState={
              <EmptyState icon={Tag} title="Nenhuma categoria encontrada" />
            }
            renderRow={(cat) => (
              <Tr key={cat.id}>
                <Td style={{ color: "var(--text-muted)", fontSize: "12px" }}>
                  {cat.id}
                </Td>
                <Td style={{ fontWeight: 500 }}>{cat.name}</Td>
                <Td>
                  {cat.active !== false ? (
                    <Badge
                      label="Ativa"
                      color="var(--success)"
                      bg="var(--success-bg)"
                    />
                  ) : (
                    <Badge
                      label="Inativa"
                      color="var(--danger)"
                      bg="var(--danger-bg)"
                    />
                  )}
                </Td>
                <Td>
                  <div style={{ display: "flex", gap: "6px" }}>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => openEdit(cat)}
                    >
                      <Pencil size={13} />
                    </Button>
                    <Button
                      variant="danger"
                      size="sm"
                      onClick={() => handleDelete(cat)}
                      disabled={deleting === cat.id}
                    >
                      {deleting === cat.id ? (
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
        title={editing ? "Editar Categoria" : "Nova Categoria"}
        width={420}
      >
        <div style={{ display: "flex", flexDirection: "column", gap: "14px" }}>
          <Input
            label="Nome *"
            value={form.name}
            onChange={(e) => setForm((p) => ({ ...p, name: e.target.value }))}
            error={errors.name}
            placeholder="Ex: Bebidas, Lanches..."
          />
          <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
            <Toggle
              value={form.active}
              onChange={(v) => setForm((p) => ({ ...p, active: v }))}
            />
            <span style={{ fontSize: "13px", color: "var(--text-secondary)" }}>
              Categoria ativa
            </span>
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
              {editing ? "Salvar" : "Criar Categoria"}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
