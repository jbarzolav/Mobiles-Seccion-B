# Mi Bodega — App Cliente

**Fase 1 sin IA** · rama `main` · 8 commits · app de bodega en Jetpack Compose con navegación por rutas y estado compartido

## Commits

1. `9299a28` agregar codigo esqueleto de Mi Bodega
2. `e89793c` crear pantalla de datos de entrega con formulario y seleccion de horario
3. `adfbb08` crear pantalla de confirmacion del pedido con resumen de entrega y pago
4. `8a62db1` agregar rutas de entrega y confirmacion con sus pantallas en ClienteApp
5. `fb9062b` conectar el boton continuar pedido con la pantalla de datos de entrega
6. `686fcd0` volver al inicio con popUpTo y vaciar el carrito al confirmar el pedido
7. `9da0ab4` completar los TODO pendientes de login terminos registro y favorito
8. este README con preguntas de reflexion y conclusiones

## Evidencias

### Commit 1 — código esqueleto

![Commit 1](evidencias/commit%201.png)

### Commit 2 — pantalla de datos de entrega

![Commit 2a](evidencias/commmit%202.png)

![Commit 2b](evidencias/tercer%20commit%202.png)

### Commit 3 — pantalla de confirmación

![Commit 3](evidencias/tercer%20commit.png)

### Commit 4 — rutas de entrega y confirmación

![Commit 4a](evidencias/cuarto%20commit.png)

![Commit 4b](evidencias/cuarto%20commit%202.png)

### Commit 5 — conectar «Continuar pedido»

![Commit 5](evidencias/quinto%20commit.png)

### Commit 6 — popUpTo y carrito vacío

![Commit 6](evidencias/sexto%20commit.png)

### Commit 7 — login términos registro y favorito

![Commit 7a](evidencias/septimo%20commit.png)

![Commit 7b](evidencias/septimo%20commit%202.png)

## Preguntas de reflexión

**¿Qué aprendiste sobre el estado compartido?**

> el carrito y los datos de entrega los mantengo arriba en ClienteApp con remember y los reparto hacia abajo con funciones lambda así ninguna pantalla modifica los datos a escondidas y cuando agrego una pantalla nueva solo le paso lo que necesita

**¿Cómo funciona la navegación de esta app?**

> cada pantalla tiene su ruta en el objeto Rutas y ClienteApp decide qué abrir con el NavHost y con popUpTo controlo qué pantallas se borran del historial para que la flecha atrás no regrese a pantallas ya terminadas

**¿Qué dificultades tuviste y cómo las resolviste?**

> al principio no veía las pantallas nuevas en el emulador porque seguía con el APK viejo y también creé la cuenta vacía así que el formulario de entrega llegaba en blanco la solución fue recompilar con Run y escribir los datos antes de crear la cuenta

**¿Por qué es importante el control de versiones?**

> porque me permite separar el trabajo en commits pequeños volver atrás si algo sale mal y demostrar paso a paso qué hice en cada commit sin perder lo que ya funcionaba

**¿Qué harías diferente la próxima vez?**

> probar en el emulador después de cada commit en vez de dejar todas las pruebas para el final

## Conclusiones

- Aprendí a levantar una app completa con Compose usando estado compartido y navegación con rutas
- Entendí que crear una pantalla y conectarla con su ruta son dos pasos distintos
- Con popUpTo evité amontonar pantallas en el historial y logré que el pedido termine limpio
- Practiqué el ciclo de trabajo commit compilar probar capturar documentar
- Verifiqué el registro guardado en el dispositivo y su uso para precargar la entrega
- Voy a la fase 2 con el flujo completo funcionando de punta a punta
