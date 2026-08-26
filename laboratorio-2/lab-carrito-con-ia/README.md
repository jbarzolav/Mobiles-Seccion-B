# Laboratorio 02: Carrito de Compras en Kotlin (Versión con IA)

**Curso:** Desarrollo de Aplicaciones Móviles  
**Estudiante:** José León  
**Sección:** B

---

## Parte 2: Modelo de datos y variables

### Preguntas del laboratorio

**1. ¿Por qué nombre y precio son `val` pero `cantidad` es `var`?**

- `nombre` y `precio` son `val` porque son propiedades fijas de un producto que no deberían cambiar después de crearlo (un "Laptop HP" siempre será "Laptop HP" y su precio base no debería modificarse directamente).
- `cantidad` es `var` porque representa cuántas unidades de ese producto tenemos en el carrito, y esta información cambia cuando el usuario agrega o quita unidades.

**2. ¿Qué pasaría si intentas cambiar el precio después de crear el producto?**

Obtendrías un error de compilación. Como `precio` está declarado con `val`, Kotlin no permite reasignarlo. Si intentaras hacer `producto.precio = 999.0`, el compilador mostraría: *"Val cannot be reassigned"*. Esto protege la integridad de los datos.

---

### Captura de salida

```text
Cliente: Juan Leon

Producto agregado: Laptop HP
Producto agregado: Mouse Logitech
Producto agregado: Teclado Mecánico
Producto agregado: Monitor Samsung
```