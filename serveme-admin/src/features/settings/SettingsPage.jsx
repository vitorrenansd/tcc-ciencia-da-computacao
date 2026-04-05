import { useState, useEffect, useRef } from "react";
import { configApi } from "../../lib/api";
import { Button, Input, Card, Spinner } from "../../components/ui";
import { Upload, CheckCircle, Building2 } from "lucide-react";

export function SettingsPage({ onSettingsChange }) {
  const [name, setName] = useState("");
  const [iconUrl, setIconUrl] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [saved, setSaved] = useState(false);
  const fileRef = useRef();

  // Carrega config atual da API ao montar
  useEffect(() => {
    configApi
      .get()
      .then((data) => {
        setName(data.name ?? "");
        setIconUrl(data.iconUrl ?? null);
        onSettingsChange?.({ name: data.name, logo: data.iconUrl });
      })
      .finally(() => setLoading(false));
  }, []);

  const handleSave = async () => {
    if (!name.trim()) return;
    setSaving(true);
    try {
      await configApi.update(name.trim());
      onSettingsChange?.((prev) => ({ ...prev, name: name.trim() }));
      setSaved(true);
      setTimeout(() => setSaved(false), 2500);
    } finally {
      setSaving(false);
    }
  };

  const handleIconChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setUploading(true);
    try {
      await configApi.uploadIcon(file);
      // Recarrega config para pegar a nova iconUrl gerada pelo backend
      const updated = await configApi.get();
      setIconUrl(updated.iconUrl);
      onSettingsChange?.((prev) => ({ ...prev, logo: updated.iconUrl }));
    } finally {
      setUploading(false);
      // Limpa o input para permitir re-upload do mesmo arquivo
      e.target.value = "";
    }
  };

  if (loading) {
    return (
      <div
        style={{ display: "flex", justifyContent: "center", padding: "48px" }}
      >
        <Spinner size={28} />
      </div>
    );
  }

  return (
    <div
      className="animate-fade"
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "24px",
        maxWidth: "560px",
      }}
    >
      <div>
        <h1 style={{ fontSize: "20px", fontWeight: 700 }}>Configurações</h1>
        <p
          style={{
            fontSize: "12px",
            color: "var(--text-muted)",
            marginTop: "2px",
          }}
        >
          Personalize as informações do painel
        </p>
      </div>

      <Card>
        <div style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
          <div
            style={{
              display: "flex",
              alignItems: "center",
              gap: "10px",
              marginBottom: "4px",
            }}
          >
            <Building2 size={16} color="var(--blue-400)" />
            <p style={{ fontSize: "14px", fontWeight: 600 }}>
              Dados do Estabelecimento
            </p>
          </div>

          {/* Ícone */}
          <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
            <label
              style={{
                fontSize: "12px",
                fontWeight: 500,
                color: "var(--text-secondary)",
              }}
            >
              Logo / Ícone da Sidebar
            </label>
            <div style={{ display: "flex", alignItems: "center", gap: "14px" }}>
              <div
                style={{
                  width: 56,
                  height: 56,
                  borderRadius: 10,
                  background: "var(--bg-600)",
                  border: "1px solid var(--border)",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  overflow: "hidden",
                  flexShrink: 0,
                }}
              >
                {uploading ? (
                  <Spinner size={20} />
                ) : iconUrl ? (
                  <img
                    src={iconUrl}
                    alt="logo"
                    style={{
                      width: "100%",
                      height: "100%",
                      objectFit: "cover",
                    }}
                  />
                ) : (
                  <span
                    style={{
                      fontSize: "22px",
                      fontWeight: 700,
                      color: "var(--text-muted)",
                    }}
                  >
                    {(name || "S")[0].toUpperCase()}
                  </span>
                )}
              </div>
              <div
                style={{ display: "flex", flexDirection: "column", gap: "6px" }}
              >
                <Button
                  variant="secondary"
                  size="sm"
                  onClick={() => fileRef.current?.click()}
                  disabled={uploading}
                >
                  <Upload size={13} />{" "}
                  {uploading ? "Enviando..." : "Enviar imagem"}
                </Button>
                <input
                  ref={fileRef}
                  type="file"
                  accept="image/*"
                  style={{ display: "none" }}
                  onChange={handleIconChange}
                />
                <p style={{ fontSize: "11px", color: "var(--text-muted)" }}>
                  PNG, JPG. Recomendado: 64×64px
                </p>
              </div>
            </div>
          </div>

          {/* Nome */}
          <Input
            label="Nome do Estabelecimento"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Ex: Burguer Place"
          />

          {/* Salvar */}
          <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
            <Button onClick={handleSave} disabled={saving || !name.trim()}>
              {saving ? <Spinner size={14} /> : null}
              Salvar Alterações
            </Button>
            {saved && (
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  gap: "5px",
                  color: "var(--success)",
                  fontSize: "13px",
                }}
              >
                <CheckCircle size={15} /> Salvo!
              </div>
            )}
          </div>
        </div>
      </Card>

      <Card style={{ opacity: 0.5 }}>
        <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
          <p
            style={{
              fontSize: "14px",
              fontWeight: 600,
              color: "var(--text-secondary)",
            }}
          >
            🔐 Acesso e Autenticação
          </p>
          <p style={{ fontSize: "12px", color: "var(--text-muted)" }}>
            Login de administrador, permissões e segurança — disponível em
            breve.
          </p>
        </div>
      </Card>
    </div>
  );
}
