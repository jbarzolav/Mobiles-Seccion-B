# Laboratorio 02: Carrito de Compras en Kotlin (Version sin IA)

**Curso:** Desarrollo de Aplicaciones Moviles
**Estudiante:** Jose Leon Barzola Veliz
**Seccion:** B
**Repositorio:** [Mobiles-Seccion-B](https://github.com/jbarzolav/Mobiles-Seccion-B)

---

## Descripcion del Programa

Carrito de compras para la Tienda Tecsup en Kotlin. Utiliza `data class` para modelar productos y funciones para calcular subtotales, IGV, descuentos, busqueda y eliminacion.

## Funciones Implementadas

| Funcion | Descripcion |
|---------|-------------|
| `calcularSubtotal()` | Suma precio x cantidad de cada producto |
| `calcularIGV()` | Calcula el 18% del subtotal |
| `calcularTotal()` | Suma subtotal + IGV |
| `calcularDescuento()` | Aplica descuento segun el monto total |
| `mostrarDetalle()` | Reporte con columnas alineadas usando `String.format` |
| `buscarProducto()` | Busca un producto por nombre usando `find` |
| `eliminarProducto()` | Elimina un producto por nombre usando `removeIf` |
| `ejecutarCarrito()` | Funcion principal que ejecuta todo el flujo |

## Conceptos Kotlin Utilizados

- `data class`: Modelo de datos simple para Producto
- `mutableListOf()`: Lista dinamica para el carrito
- `maxByOrNull`: Producto mas caro
- `when`: Descuento segun el monto
- `String.format`: Formato de columnas alineadas
- `find` y `removeIf`: Busqueda y eliminacion

## Captura de Salida

![Resultado del carrito de compras](resultado.png)

```
=========================================
   CARRITO DE COMPRAS - TIENDA TECSUP   
=========================================

Cliente: Juan Leon

--------- DETALLE DEL CARRITO ---------
1. Laptop HP            x1 S/  2500.00
2. Mouse Logitech       x2 S/    91.00
3. Audifonos Sony       x1 S/   120.00
4. Curso Kotlin         x1 S/   135.00
---------------------------------------
Cantidad de productos: 4

Producto mas caro: Laptop HP (S/ 2500.00)

Subtotal :                S/  2846.00
IGV (18%):                S/   512.28
TOTAL :                   S/  3358.28
Descuento aplicado :      S/   167.91
TOTAL CON DESCUENTO:      S/  3190.37

========== BUSCAR PRODUCTO ==========
Producto encontrado: Mouse Logitech - S/ 45.5

========== ELIMINAR PRODUCTO ==========
Producto 'Audifonos Sony' eliminado del carrito

--------- DETALLE DEL CARRITO ---------
1. Laptop HP            x1 S/  2500.00
2. Mouse Logitech       x2 S/    91.00
3. Curso Kotlin         x1 S/   135.00
---------------------------------------
Cantidad de productos: 3

Subtotal :                S/  2726.00
IGV (18%):                S/   490.68
TOTAL :                   S/  3216.68
Descuento aplicado :      S/   160.83
TOTAL CON DESCUENTO:      S/  3055.85
```

## Explicacion de val vs var

**Pregunta: Por que nombre y precio son `val` pero cantidad es `var`? Que pasaria si intentas cambiar el precio despues de crear el producto?**

- `nombre` y `precio` son `val` porque son propiedades inmutables que definen la identidad y costo base de un producto. Una vez creado, no deberian cambiar directamente.
- `cantidad` es `var` porque es una propiedad mutable que representa cuantas unidades hay en el carrito.

Si intento cambiar el precio:

```kotlin
val producto = Producto("Laptop HP", 2500.0, 1)
producto.precio = 999.0  // ERROR: Val cannot be reassigned
```

Kotlin genera un error de compilacion porque `precio` esta declarado con `val`. Esto protege la integridad de los datos y evita modificaciones accidentales.

## Como Ejecutar

1. Clonar el repositorio
2. Abrir `lab-carrito-sin-ia` en Android Studio
3. Ejecutar el test: `ExampleUnitTest.kt` - funcion `correrConsola()`
4. Verificar la salida en la pestana "Run"
