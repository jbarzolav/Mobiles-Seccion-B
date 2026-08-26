# Laboratorio 02: Carrito de Compras en Kotlin

**Alumno:** José Manuel Barzola Veliz  
**Curso:** Desarrollo de Aplicaciones Móviles  
**Sección:** B

---

## Descripción del Proyecto

Este laboratorio es un programa en consola de Kotlin para gestionar un carrito de compras de la Tienda Tecsup. El programa guarda productos en una lista, imprime la tabla de compras alineada, calcula el subtotal, el IGV (18%) y el total final. También busca el producto más caro de la lista y aplica un descuento según el monto acumulado usando `when`.

### Funciones Implementadas

| Función | Descripción |
|---------|-------------|
| `calcularSubtotal()` | Suma el precio por cantidad de cada producto |
| `calcularIGV()` | Saca el 18% del subtotal |
| `calcularTotal()` | Suma el subtotal más el IGV |
| `mostrarDetalle()` | Muestra la lista de productos bien alineada en consola |
| `calcularDescuento()` | Evalúa el total con `when` para aplicar el porcentaje de descuento |
| `buscarProducto()` | Busca un producto por nombre usando `find` |
| `eliminarProducto()` | Elimina un producto por nombre usando `removeIf` |

---

## Parte 1: Modelo de Datos

**Qué se hizo:** Crear la estructura que representa un producto en el carrito.

```kotlin
open class Producto(val nombre: String, val precio: Double, var cantidad: Int) {
    open fun calcularSubtotal(): Double = precio * cantidad
}
```

**Conceptos:**
- `open class`: Clase que puede ser heredada
- `val`: Propiedad inmutable (no cambia)
- `var`: Propiedad mutable (puede cambiar)
- `calcularSubtotal()`: Método que calcula el subtotal del producto

---

## Parte 2: Funciones de Cálculo

**Qué se hizo:** Separar la lógica en funciones reutilizables.

```kotlin
fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.calcularSubtotal()
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double = subtotal * 0.18

fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv
```

**Conceptos:**
- Funciones con tipos explícitos
- `List<Producto>`: Tipo inmutable de lista
- `return`: Retorna el valor calculado

---

## Parte 3: Reporte con Formato

**Qué se hizo:** Crear una función que muestra el detalle con columnas alineadas.

```kotlin
fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, p.calcularSubtotal()))
        i++
    }
    println("---------------------------------------")
}
```

**Conceptos:**
- `String.format()`: Formateo de salida con columnas alineadas
- `%-20s`: String alineado a la izquierda en 20 espacios
- `%8.2f`: Decimal con 2 decimales, alineado a la derecha

---

## Parte 4: Producto Más Caro y Descuento

**Qué se hizo:** Implementar búsqueda de producto más caro y descuentos.

```kotlin
val masCaro = carrito.maxByOrNull { it.precio }

fun calcularDescuento(total: Double): Double = when {
    total > 5000 -> total * 0.10
    total > 3000 -> total * 0.05
    else -> 0.0
}
```

**Conceptos:**
- `maxByOrNull`: Encuentra el elemento con mayor valor
- `when`: Expresión condicional (equivalente a switch)
- Lambdas: `{ it.precio }`

---

## Parte 5: POO - Programación Orientada a Objetos

**Qué se hizo:** Agregar encapsulamiento, herencia, polimorfismo y abstracción.

### Encapsulamiento
```kotlin
open class Producto(val nombre: String, val precio: Double, var cantidad: Int) {
    open fun calcularSubtotal(): Double = precio * cantidad
}
```
Datos y comportamiento juntos en una clase.

### Herencia
```kotlin
class ProductoDigital(nombre: String, precio: Double, cantidad: Int, val formato: String) 
    : Producto(nombre, precio, cantidad) {
    override fun calcularSubtotal(): Double = precio * cantidad * 0.9
}
```
`ProductoDigital` hereda de `Producto` y agrega un descuento del 10%.

### Polimorfismo
```kotlin
for (p in carrito) {
    println(p.calcularSubtotal())  // Se ejecuta según el tipo
}
```
Mismo método, diferente comportamiento según el tipo de producto.

### Abstracción
```kotlin
interface Calculable {
    fun calcular(): Double
}
```
Contrato que define qué se debe hacer sin importar cómo.

---

## Respuestas de la Parte 2

1. **¿Por qué `nombre` y `precio` son `val` pero `cantidad` es `var`?**  
   Porque el nombre y el precio del producto no cambian en la tienda. En cambio, la cantidad de cosas que el cliente compra sí cambia porque puede agregar o quitar productos en el carrito.

2. **¿Qué pasaría si intentas cambiar el precio después de crear el producto?**  
   Sale un error en Android Studio porque la palabra `val` no permite cambiar el valor de una variable después de que ya se creó.

---

## Resultado de la Consola

![Captura del carrito](resultado.png)

```text
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
```

---

## Commits

```bash
42d6545 POO: Encapsulamiento, Herencia, Polimorfismo y Abstraccion
021120c Reto adicional: Buscar y eliminar producto
bd92054 Parte 5: Producto mas caro y descuento por monto con when
5067b8e Parte 4: Reporte de detalle con columnas alineadas
e003c5e Parte 3: Funciones de calculo de subtotal, IGV y total
5eb4db7 Parte 2: Modelo de datos Producto y variables del carrito
bc0640a Parte 1: Modelo de datos Producto y menu principal interactivo
```