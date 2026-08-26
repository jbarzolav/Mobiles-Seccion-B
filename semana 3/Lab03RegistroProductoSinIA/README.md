# Laboratorio 03: Registro de Producto con Jetpack Compose

**Estudiante:** José León Barzola Veliz  
**Sección:** B

## Descripción

Aplicación de registro de producto desarrollada con Jetpack Compose. Permite ingresar nombre, precio y cantidad de un producto, y muestra una Card con el resumen y el importe calculado (precio × cantidad con 2 decimales).

## Capturas de pantalla

### Pantalla vacía
![Pantalla vacía](captura%20sin%20nada%20.png)

### Pantalla con producto registrado
![Pantalla con producto](Captura%20con%20productos%20rellenados%20.png)

## ¿Qué pasaría si declaras las variables de los campos SIN remember?

Si se declaran las variables sin `remember`, cada vez que Compose redibuja la pantalla (por ejemplo, al escribir en un campo), las variables se vuelven a crear con su valor inicial (""). Esto significa que:

- El texto que escribes desaparece al perder el foco
- Los campos nunca guardan lo que el usuario ingresa
- La pantalla nunca puede mostrar los datos porque se resetean en cada recomposición

`remember` guarda el valor entre recomposiciones, permitiendo que el estado persista mientras la pantalla esté visible.
