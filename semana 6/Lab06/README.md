# Lab06 — TECSUP Store

Fase 2 — Mejora con IA.

## Prompt 1

![promt 1](imagenes/promt%201.png)

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

---

## Prompt 2

![promt 2](imagenes/promt%202.png)

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

---

## Prompt 3

![promt 3](imagenes/promt%203.png)

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
