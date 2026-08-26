# Laboratorio 02: Carrito de Compras en Kotlin

**Alumno:** José Manuel Barzola Veliz  
**Curso:** Desarrollo de Aplicaciones Móviles

---

## Descripción del Proyecto
Este laboratorio es un programa en consola de Kotlin para gestionar un carrito de compras de la Tienda Tecsup. El programa guarda productos en una lista, imprime la tabla de compras alineada, calcula el subtotal, el IGV (18%) y el total final. También busca el producto más caro de la lista y aplica un descuento según el monto acumulado usando `when`.

### Funciones Implementadas
* `calcularSubtotal()`: Suma el precio por cantidad de cada producto.
* `calcularIGV()`: Saca el 18% del subtotal.
* `calcularTotal()`: Suma el subtotal más el IGV.
* `mostrarDetalle()`: Muestra la lista de productos bien alineada en consola.
* `calcularDescuento()`: Evalúa el total con `when` para aplicar el porcentaje de descuento.

---

## Respuestas de la Parte 2

1. **¿Por qué `nombre` y `precio` son `val` pero `cantidad` es `var`?**  
   Porque el nombre y el precio del producto no cambian en la tienda. En cambio, la cantidad de cosas que el cliente compra sí cambia porque puede agregar o quitar productos en el carrito.

2. **¿Qué pasaría si intentas cambiar el precio después de crear el producto?**  
   Sale un error en Android Studio porque la palabra `val` no permite cambiar el valor de una variable después de que ya se creó.

---

## Resultado de la Consola
![Captura del carrito](resultado.png)