# PROMPTS — Fase 2: Mejora con IA

**Proyecto:** TECSUP Store — `semana 6/Lab06`
**Rama:** `semana-6-mejora-con-ia` (creada a partir de `main`)
**Mejora obligatoria:** que el campo de búsqueda de la Pantalla 3 (Inicio) filtre la lista de
productos en tiempo real a medida que el usuario escribe, combinándose correctamente con el
filtro de categoría ya existente (ambos filtros deben funcionar juntos, no reemplazarse).

---

## Commit 1 — `ca9349f` Agregar campo de busqueda visual de productos en la pantalla principal

**Prompt 1:**

```
Dentro del composable PantallaPrincipalStore, en la rama "Inicio" de la pantalla, hay un
LazyRow de chips de categoría (FilterChip) y un LazyColumn que muestra
items(productosFiltrados).

AGREGA un campo de búsqueda visual:
- Un OutlinedTextField con singleLine = true, ícono de lupa (Icons.Default.Search) al
  inicio, placeholder "Buscar productos...", modifier fillMaxWidth() con padding
  horizontal de 16.dp, colocado entre el LazyRow de chips y el LazyColumn.
- El estado debe ser: var textoBusqueda by remember { mutableStateOf("") } y
  onValueChange = { textoBusqueda = it }.
- Deja todo lo demás igual (drawer, chips, tarjetas) y NO toques la lógica de filtrado
  todavía.

Cuando termines de editar y verificar que el código compila
```

**Imagen — prompt 1:**

![Prompt 1](semana%206/Lab06/imagenes/promt%201.png)

**Resultado:** se agregó el `OutlinedTextField` de búsqueda con su estado `textoBusqueda`
dentro de la rama `"Inicio"`, sin tocar la lógica de filtrado.
**Archivo:** `semana 6/Lab06/app/src/main/java/com/tecsup/tecsupstore/MainActivity.kt`

---

## Commit 2 — `9bfa82f` Implementar filtrado de productos por texto y categoria en tiempo real

**Prompt 2:**

```
existe esta lógica:

val productosFiltrados = if (categoriaSeleccionada == "Todos") {
    listaProductosDemo
} else {
    listaProductosDemo.filter { it.categoria == categoriaSeleccionada }
}

MODIFÍCALA para que el campo de búsqueda (textoBusqueda) filtre la lista de productos EN
TIEMPO REAL mientras el usuario escribe, y que AMBOS filtros funcionen JUNTOS (no se
reemplacen entre sí):
- Primero filtra por categoría (si categoriaSeleccionada == "Todos", no filtra por categoría).
- Luego filtra por texto: coincidencia parcial en el nombre sin distinguir mayúsculas de
  minúsculas (contains(textoBusqueda, ignoreCase = true)); si textoBusqueda está vacío o en
  blanco, no debe excluir ningún producto.
- Usa remember(textoBusqueda, categoriaSeleccionada) para recalcular solo cuando cambien.
- No agregues ni quites componentes de UI; solo cambia la lógica del filtrado.

Cuando termines y verifiques que compila
```

**Imagen — prompt 2:**

![Prompt 2](semana%206/Lab06/imagenes/promt%202.png)

**Resultado:** `productosFiltrados` ahora combina ambos filtros (categoría **y** texto) y se
recalcula solo con `remember(textoBusqueda, categoriaSeleccionada)`.
**Archivo:** `semana 6/Lab06/app/src/main/java/com/tecsup/tecsupstore/MainActivity.kt`

---

## Commit 3 — `e08ac5e` feat: agregar estado vacio y boton para limpiar la busqueda

**Prompt 3:**

```
COMPLETA la mejora de búsqueda con estos detalles de usabilidad:
1. Si productosFiltrados queda vacío, muestra dentro del LazyColumn un mensaje centrado
   "No se encontraron productos" y, si textoBusqueda no está vacío, un TextButton
   "Limpiar búsqueda" que ponga textoBusqueda = "".
2. En el OutlinedTextField agrega trailingIcon con un botón de cerrar (Icons.Default.Close)
   visible solo cuando textoBusqueda no esté vacío, para borrar lo escrito.
3. Verifica que el campo tenga singleLine = true para que no crezca al escribir.

Cuando termines y verifiques que compila,
```

**Imagen — prompt 3:**

![Prompt 3](semana%206/Lab06/imagenes/promt%203.png)

**Resultado:** estado vacío con mensaje + botón "Limpiar búsqueda", `trailingIcon` con la "X"
para borrar lo escrito y `singleLine = true` confirmado.
**Archivo:** `semana 6/Lab06/app/src/main/java/com/tecsup/tecsupstore/MainActivity.kt`

---

## Decisión técnica

El filtro de texto se aplica **después** del filtro de categoría: un producto se muestra solo
si cumple las dos condiciones. Ejemplo: categoría "Accesorios" + texto "carga" → solo
"Cargador Carga Rápida". Si el usuario borra el texto se conserva el filtro de categoría, y si
elige "Todos" el texto sigue filtrando. Así ambos filtros trabajan **juntos y no se
reemplazan**. Se usa `remember(textoBusqueda, categoriaSeleccionada)` para no recalcular la
lista en cada recomposición, solo cuando cambia una de las dos claves.
