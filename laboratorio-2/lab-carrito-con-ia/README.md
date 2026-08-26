# Laboratorio 02: Carrito de Compras en Kotlin (Versión con IA)

**Curso:** Desarrollo de Aplicaciones Móviles  
**Estudiante:** José León Barzola Veliz  
**Sección:** B  
**Repositorio:** [lab02-carrito-con-ia](https://github.com/jbarzolav/Mobiles-Seccion-B)

---

## Descripción del Programa

Este programa implementa un **carrito de compras** para la Tienda Tecsup utilizando Kotlin. El sistema permite:

- **Modelar productos** con nombre, precio y cantidad usando `data class`
- **Calcular subtotales** sumando el precio × cantidad de cada producto
- **Calcular IGV** (18%) sobre el subtotal
- **Calcular el total** a pagar (subtotal + IGV)
- **Identificar el producto más caro** usando `maxByOrNull`
- **Aplicar descuentos** según el monto total usando `when`
- **Generar un reporte detallado** con columnas alineadas usando `String.format`

### Funciones Implementadas

| Función | Descripción |
|---------|-------------|
| `calcularSubtotal()` | Suma los importes de todos los productos |
| `calcularIGV()` | Calcula el 18% del subtotal |
| `calcularTotal()` | Suma subtotal + IGV |
| `calcularDescuento()` | Aplica descuento según el monto total |
| `mostrarDetalle()` | Muestra el reporte formateado con columnas alineadas |
| `main()` | Función principal que ejecuta todo el flujo |

---

## Explicación Detallada por Partes

### PARTE 1: Proyecto y Repositorio

**Qué se hizo:** Crear el proyecto en Android Studio con la plantilla Empty Activity y configurar el repositorio Git.

**Conceptos clave:**
- Android Studio genera automáticamente la estructura del proyecto con `build.gradle.kts`, `AndroidManifest.xml`, y las carpetas `app/src/main/java/`
- El paquete se creó como `com.barzola.lab02carritokotlin`
- Se publicó en GitHub con el nombre `lab02-carrito-tuapellido`

<!-- ESPACIO PARA CAPTURA DE PARTE 1 -->

---

### PARTE 2: Modelo de Datos y Variables

**Qué se hizo:** Crear la estructura que representa un producto en el carrito.

**Código:**
```kotlin
data class Producto(val nombre: String, val precio: Double, var cantidad: Int)
```

**¿Por qué `data class`?**
- Genera automáticamente `toString()`, `equals()`, `hashCode()`, y `copy()`
- Sin `data class`, tendríamos que escribir estos métodos manualmente
- Es la forma idiomática de Kotlin para modelos de datos

**¿Por qué `val` vs `var`?**

| Propiedad | Tipo | Razón |
|-----------|------|-------|
| `nombre` | `val` | El nombre del producto no debería cambiar después de crearlo |
| `precio` | `val` | El precio base es fijo, se modifica con funciones separadas |
| `cantidad` | `var` | La cantidad cambia cuando el usuario agrega/quita unidades |

**¿Qué pasa si intento cambiar un `val`?**
```kotlin
val precio = 2500.0
precio = 999.0  // ❌ ERROR: Val cannot be reassigned
```
Kotlin protege la integridad de los datos. Esto evita bugs accidentales.

**Función `main()` inicial:**
```kotlin
fun main() {
    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()
    
    println("Cliente: $nombreCliente")
    
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    
    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
}
```

**Conceptos:**
- `mutableListOf<Producto>()` → Lista vacía que permite agregar/eliminar elementos
- `add()` → Método para agregar elementos a la lista
- `for (producto in carrito)` → Bucle que recorre cada elemento
- `"Cliente: $nombreCliente"` → String template para insertar variables

<!-- ESPACIO PARA CAPTURA DE PARTE 2 -->

---

### PARTE 3: Funciones de Cálculo

**Qué se hizo:** Separar la lógica en funciones reutilizables.

**Función 1: Calcular Subtotal**
```kotlin
fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}
```

| Elemento | Explicación |
|----------|-------------|
| `productos: List<Producto>` | Parámetro de tipo inmutable (la función no modifica la lista) |
| `: Double` | Tipo de retorno explícito |
| `var subtotal = 0.0` | Variable local mutable para acumular |
| `+=` | Operador de acumulación (subtotal = subtotal + ...) |
| `return subtotal` | Retorna el valor calculado |

**¿Por qué `List<Producto>` y no `MutableList<Producto>`?**
- Buenas prácticas: las funciones de cálculo no deberían modificar la lista
- Si usáramos `MutableList`, la función podría modificar el carrito accidentalmente

**Función 2: Calcular IGV**
```kotlin
fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}
```
- Recibe el subtotal como parámetro
- Retorna el 18% (IGV peruano)
- Función pura: solo calcula, no tiene efectos secundarios

**Función 3: Calcular Total**
```kotlin
fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}
```
- Suma subtotal + IGV
- Se separó en 3 funciones para mayor claridad y reutilización

**¿Por qué separar en 3 funciones?**
1. **Claridad**: Cada función hace una cosa específica
2. **Reutilización**: Se pueden usar independientemente
3. **Testing**: Fácil de probar cada función por separado
4. **Mantenimiento**: Si cambia el IGV, solo se modifica una función

<!-- ESPACIO PARA CAPTURA DE PARTE 3 -->

---

### PARTE 4: Reporte con Formato

**Qué se hizo:** Crear una función que muestra el detalle con columnas alineadas.

**Código:**
```kotlin
fun mostrarDetalle(productos: List<Producto>) {
    println("---------- DETALLE DEL CARRITO ----------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("------------------------------------------")
}
```

**Explicación de `String.format()`:**

| Símbolo | Significado | Ejemplo |
|---------|-------------|---------|
| `%d` | Entero | `1`, `2`, `3` |
| `%-20s` | String alineado a la izquierda en 20 espacios | `"Laptop HP           "` |
| `%8.2f` | Decimal con 2 decimales, alineado a la derecha en 8 espacios | `2500.00` |

**Ejemplo visual:**
```
1. Laptop HP           x1 S/  2500.00
2. Mouse Logitech      x2 S/    91.00
3. Audifonos Sony      x1 S/   120.00
```

**¿Por qué `String.format` y no string templates?**
- `String.format` permite controlar la alineación y el ancho de las columnas
- Los string templates (`"$variable"`) no tienen control de formato
- Para reportes con columnas alineadas, `String.format` es la herramienta correcta

<!-- ESPACIO PARA CAPTURA DE PARTE 4 -->

---

### PARTE 5: Producto Más Caro y Descuento

**Qué se hizo:** Implementar dos funcionalidades avanzadas usando funciones de Kotlin.

**1. Producto Más Caro:**
```kotlin
val masCaro = carrito.maxByOrNull { it.precio }
if (masCaro != null) {
    println("Producto más caro: ${masCaro.nombre} " +
            String.format("(S/ %.2f)", masCaro.precio))
}
```

**¿Qué hace `maxByOrNull`?**
- Recorre la lista y retorna el elemento con el valor más alto según la lambda
- Si la lista está vacía, retorna `null` (por eso el `if`)
- `{ it.precio }` → Lambda que indica comparar por precio

**¿Por qué no `maxOf`?**
- `maxOf` lanza excepción si la lista está vacía
- `maxByOrNull` es más seguro, retorna `null` en lugar de fallar

**2. Descuento con `when`:**
```kotlin
fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10  // 10% si supera S/ 5000
        total > 3000 -> total * 0.05  // 5% si supera S/ 3000
        else -> 0.0                    // Sin descuento
    }
}
```

**¿Por qué `when` sin argumento?**
- `when` con argumento: `when (opcion) { 1 -> ..., 2 -> ... }`
- `when` sin argumento: evalúa expresiones booleanas (`total > 5000`)
- Es equivalente a un `if-else` chain pero más legible

**¿Por qué `else -> 0.0`?**
- Si ninguna condición se cumple, no hay descuento
- Retorna 0.0 para mantener el tipo de retorno consistente

<!-- ESPACIO PARA CAPTURA DE PARTE 5 -->

---

## Resumen de Conceptos Kotlin por Parte

| Parte | Conceptos Principales |
|-------|----------------------|
| Parte 1 | Estructura del proyecto Android, Git, GitHub |
| Parte 2 | `data class`, `val` vs `var`, `mutableListOf()`, string templates |
| Parte 3 | Funciones con tipos explícitos, `List` vs `MutableList`, `return` |
| Parte 4 | `String.format()`, alineación de columnas, bucles `for` |
| Parte 5 | `maxByOrNull`, `when` sin argumento, funciones puras |

---

## Respuesta a la Pregunta de la Parte 2 (val vs var)

> **¿Por qué nombre y precio son `val` pero `cantidad` es `var`? ¿Qué pasaría si intentas cambiar el precio después de crear el producto?**

### Análisis

- **`nombre` y `precio` son `val`** porque son propiedades **inmutables** que definen la identidad y costo base de un producto. Una vez creado un "Laptop HP" con precio S/ 2500.0, estas propiedades no deberían cambiar directamente.

- **`cantidad` es `var`** porque es una propiedad **mutable** que representa cuántas unidades de ese producto tenemos en el carrito. Esta información cambia cuando el usuario agrega o quita unidades.

### ¿Qué pasa si intento cambiar el precio?

```kotlin
val producto = Producto("Laptop HP", 2500.0, 1)
producto.precio = 999.0  // ❌ ERROR: Val cannot be reassigned
```

Obtendríamos un **error de compilación** porque `precio` está declarado con `val`. Kotlin protege la integridad de los datos impidiendo la reasignación de propiedades inmutables. Esto es una buena práctica porque:

1. **Seguridad**: Evita modificaciones accidentales
2. **Claridad**: El código indica claramente qué propiedades pueden cambiar
3. **Concurrencia**: Las propiedades `val` son seguras en hilos múltiples

---

## Captura de Salida

![Resultado del carrito de compras](resultado.png)

```text
===========================================
    CARRITO DE COMPRAS - TIENDA TECSUP     
===========================================

Cliente: Juan Leon

Producto agregado: Laptop HP
Producto agregado: Mouse Logitech
Producto agregado: Audifonos Sony

Producto más caro: Laptop HP (S/ 2500.00)

---------- DETALLE DEL CARRITO ----------
1. Laptop HP           x1 S/  2500.00
2. Mouse Logitech      x2 S/    91.00
3. Audifonos Sony      x1 S/   120.00
------------------------------------------
Cantidad de productos: 3

Subtotal        : S/  2711.00
IGV (18%)       : S/   487.98
TOTAL           : S/  3198.98
Descuento (5%)  : -S/  159.95
TOTAL CON DSCTO : S/  3039.03
```

---

## Metodología de Desarrollo con IA (OpenCode)

### Prompts Utilizados

Para el desarrollo de este laboratorio se utilizó la siguiente estructura de prompt que define el rol, contexto y metodología de trabajo:

```
[ROL Y CONTEXTO]
Actúas como un Desarrollador Senior en Kotlin y Asistente Técnico Académico.
El objetivo es desarrollar de forma modular y guiada el "Laboratorio 02: Carrito 
de Compras en Kotlin (Versión con IA)" para el curso de Desarrollo de Aplicaciones 
Móviles.

[METODOLOGÍA DE TRABAJO]
Trabajaremos por iteraciones según las partes del laboratorio (Parte 1 a Parte 5):
1. Te adjuntaré las especificaciones o imágenes de cada parte.
2. Para cada entrega debes proveer:
   - Explicación técnica: Breve resumen sintáctico de los conceptos aplicados en Kotlin.
   - Código acumulativo: Bloque completo y limpio listo para sustituir en carrito.kt.
   - Control de versiones: El comando exacto de git commit descriptivo.
   - Cierre de turno: Solicitar la siguiente parte.

[RESTRICCIONES Y BUENAS PRÁCTICAS]
- Usar código idiomático de Kotlin (data classes, expresiones when, maxByOrNull, String.format).
- Mantener nombres de funciones claros en camelCase.
- Mantener la inmutabilidad (val) siempre que sea posible.
```

### Desarrollo Iterativo

El proyecto se desarrolló en 5 iteraciones siguiendo las partes del laboratorio:

| Parte | Descripción | Commit |
|-------|-------------|--------|
| Parte 1 | Proyecto y repositorio | `bc0640a` |
| Parte 2 | Modelo de datos y variables | `5eb4db7` |
| Parte 3 | Funciones de cálculo | `e003c5e` |
| Parte 4 | Reporte con formato | `5067b8e` |
| Parte 5 | Producto más caro y descuento | `bd92054` |

---

## Commits del Repositorio

```bash
bc0640a Parte 1: Modelo de datos Producto y menú principal interactivo
5eb4db7 Parte 2: Modelo de datos Producto y variables del carrito
179910d Parte 2: README con respuestas sobre data class y propiedades val/var
e003c5e Parte 3: Funciones de cálculo de subtotal, IGV y total
5067b8e Parte 4: Reporte de detalle con columnas alineadas
bd92054 Parte 5: Producto más caro y descuento por monto with when
ab25299 Parte 5: Test simplificado igual al lab sin IA
```

---

## Cómo Ejecutar

1. Clonar el repositorio
2. Abrir en Android Studio
3. Ejecutar el test: `CarritoTest.kt` → función `correrConsola()`
4. Verificar la salida en la pestaña "Run"