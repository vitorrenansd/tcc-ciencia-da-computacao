import { useRef } from "react";
import "./CategoryTopbar.css";

export default function CategoryTopbar({ categories, selectedId, onSelect }) {
  const scrollRef = useRef(null);

  // Permite arrastar horizontalmente com o dedo/mouse
  const isDragging = useRef(false);
  const startX = useRef(0);
  const scrollLeft = useRef(0);

  function onMouseDown(e) {
    isDragging.current = true;
    startX.current = e.pageX - scrollRef.current.offsetLeft;
    scrollLeft.current = scrollRef.current.scrollLeft;
  }
  function onMouseLeave() {
    isDragging.current = false;
  }
  function onMouseUp() {
    isDragging.current = false;
  }
  function onMouseMove(e) {
    if (!isDragging.current) return;
    e.preventDefault();
    const x = e.pageX - scrollRef.current.offsetLeft;
    const walk = (x - startX.current) * 1.2;
    scrollRef.current.scrollLeft = scrollLeft.current - walk;
  }

  return (
    <nav className="category-topbar">
      <div
        className="category-topbar__list"
        ref={scrollRef}
        onMouseDown={onMouseDown}
        onMouseLeave={onMouseLeave}
        onMouseUp={onMouseUp}
        onMouseMove={onMouseMove}
      >
        {categories.map((cat) => (
          <button
            key={cat.id}
            className={`category-topbar__item ${selectedId === cat.id ? "active" : ""}`}
            onClick={() => onSelect(cat.id)}
          >
            {cat.name}
          </button>
        ))}
      </div>
    </nav>
  );
}
