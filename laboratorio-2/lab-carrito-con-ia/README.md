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
- **Aplicar descuentos** según el monto total usando `when`:
  - 10% de descuento si el total supera S/ 5000
  - 5% de descuento si el total supera S/ 3000
  - Sin descuento si el total es menor o igual a S/ 3000
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

### Conceptos Kotlin Aplicados

- **Data classes**: Modelo de datos conciso
- **Expresiones when**: Lógica condicional para descuentos
- **maxByOrNull**: Búsqueda de elemento máximo en colecciones
- **String.format**: Formateo de salida con columnas alineadas
- **Funciones con tipos explícitos**: Código claro y documentado
- **Inmutabilidad (val)**: Protección de datos
- **Bucles for**: Iteración sobre colecciones

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