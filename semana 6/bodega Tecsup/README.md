# Mi Bodega — Fase 2 con IA

## Prompt 1

![promt 1](evidencias%20parte%202/promt%201.png)

```
En InicioScreen.kt, cuando productosFiltrados quede vacío, muestra dentro del LazyVerticalGrid un mensaje centrado "No se encontrados productos" y, si textoBusqueda no está vacío, un TextButton "Limpiar búsqueda" que ponga textoBusqueda = "". Usa un Box con contentAlignment = Center y condicional if (productosFiltrados.isEmpty()) { ... } else { LazyVerticalGrid... }. No toques el filtro ni el campo de búsqueda.
```

---

## Prompt 2

![promt 2](evidencias%20parte%202/promt%202.png)

```
En el OutlinedTextField de InicioScreen.kt agrega trailingIcon con Icons.Default.Close visible solo cuando textoBusqueda.isNotBlank(), que al click haga textoBusqueda = "". Usa trailingIcon = { if (textoBusqueda.isNotBlank()) { IconButton(onClick = { textoBusqueda = "" }) { Icon(Icons.Default.Close, contentDescription = "Limpiar") } } }. Mantén singleLine = true y el leadingIcon de lupa.
```

---

## Prompt 3

![promt 3](evidencias%20parte%202/promt%203.png)

```
Añade animateItemPlacement() al LazyVerticalGrid de InicioScreen.kt para que las tarjetas se reordenen suavemente al filtrar. Cambia items(productosFiltrados) a items(productosFiltrados, key = { it.id }) { ... } y en el LazyVerticalGrid agrega .animateItemPlacement() antes del closure items. Verifica que compile y se vea fluido.
```

## VII. Observaciones y conclusiones

**Observaciones**

Al implementar el estado vacío note que el LazyVerticalGrid no acepta hijos directos mezclados tuve que envolver todo en un Box condicional para mostrar el mensaje o la cuadrilla según corresponda

El trailingIcon no aparecía al principio porque faltaba importar Icons.Default.Close y el onClick debía estar dentro del IconButton no en el Icon directamente

**Conclusiones**

Trabajar desde el esqueleto en la Fase 1 me obligó a entender cada componente y su conexión y aunque fue lento logré dominio total del flujo mientras que en la Fase 2 la IA resolvió rápido pero mi rol cambió a revisar y validar así que el aprendizaje profundo vino de la primera fase y la velocidad de la segunda

El esqueleto dio estructura y reglas claras lo que evitó decisiones de arquitectura y permitió enfocarme en la lógica de negocio y en la Fase 2 la IA fue útil como par programador para detalles de usabilidad pero sin la base de la Fase 1 no habría sabido si el código generado encajaba bien