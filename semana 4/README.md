# Lab 04 - Carrito Tecsup

## Desarrollador
José Barzola Veliz

## Descripción
Aplicación en Kotlin con Jetpack Compose que permite registrar productos (nombre, precio y cantidad) y agregarlos a un carrito de compras con totales e IGV.

## Proyecto
`Lab04CarritoTecsup`

## Capturas

### Carrito vacío
![Carrito vacío](imagen%201.png)

### Carrito con productos
![Carrito con productos](imagen%202.png)

## Preguntas conceptuales

### (a) ¿Por qué mutableStateListOf y no una MutableList normal?
Porque `mutableStateListOf` crea una lista observable que notifica a Compose cuando cambia. Una `MutableList` normal no dispararía recomposición, por lo que la UI no se actualizaría al agregar o eliminar productos.

### (b) ¿Por qué la lista es val?
Porque `val` fija la referencia a la lista, no su contenido. La lista es un `SnapshotStateList` mutable internamente; al usar `productos.add(...)` o `productos.remove(...)` mutamos el contenido sin cambiar la referencia. Compose detecta esos cambios en el contenido y recompone automáticamente.

### (c) ¿Qué hace weight(1f) en la LazyColumn?
`weight(1f)` hace que la LazyColumn ocupe todo el espacio vertical disponible que queda en el Column principal, dejando espacio fijo arriba para el formulario y abajo para el panel de totales. Sin él, la lista se achicaría o se expandiría de forma incorrecta.
