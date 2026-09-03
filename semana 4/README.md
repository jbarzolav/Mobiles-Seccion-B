# Lab 04 - Carrito Tecsup

## Desarrollador
José Barzola Veliz

## Descripción
Aplicación en Kotlin con Jetpack Compose que permite registrar productos (nombre, precio y cantidad) y agregarlos a un carrito de compras.

## Proyecto
`Lab04CarritoTecsup`

## Etapa 1: Proyecto, modelo y estados
- Proyecto Android creado y publicado.
- Data class `Producto(nombre, precio, cantidad)` en su propio archivo `Producto.kt`.
- Composable `PantallaCarrito` con los estados del formulario (nombre, precio, cantidad) y la lista observable de productos.

## Pregunta
**¿Por qué la lista se declara con `val` y aún así podemos agregarle elementos?**

En Jetpack Compose se declara:
```kotlin
val productos = remember { mutableStateListOf<Producto>() }
```

La palabra `val` fija la **referencia** a la lista, no su contenido. `remember` conserva el objeto entre recomposiciones, y `mutableStateListOf` crea una lista observable (mutable). Al llamar `productos.add(...)`, modificamos el contenido del objeto al que apunta la referencia; como la lista es un `SnapshotStateList`, Compose detecta el cambio y recomponne la UI automáticamente. Por eso `val` no impide agregar elementos.