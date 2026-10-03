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

## VI. Preguntas de reflexión

- ¿Por qué Producto.kt y MainActivity.kt se entregaron completos, y las pantallas no? ¿Qué tienen en común los archivos que sí se dejaron como esqueleto?

> Producto.kt define los datos que usan todas las pantallas y MainActivity.kt es el punto de entrada que arranca la app sin esos dos nada compila por eso llegaron completos las pantallas quedaron como esqueleto porque eran la práctica y tenían TODO y rutas pendientes

- ¿Cómo lograste que el filtro de categoría (LazyRow) y el cálculo del carrito reaccionen automáticamente sin que tú "actualices" nada a mano?

> gracias al estado de Compose con remember cuando toco un chip cambia categoriaSeleccionada y el filtro se recalcula solo sin tocar la lista igual el carrito vive en ClienteApp como lista inmutable al sumar o restar nace un estado nuevo y total y contador se redibujan solos

- ¿Qué diferencia notaste entre navigate() normal (Inicio→Detalle) y el que usa popUpTo (Datos de entrega→Confirmación)?

> navigate normal apila la pantalla encima y la flecha atrás regresa a la anterior por eso de Inicio a Detalle vuelvo sin problema popUpTo le avisa hasta dónde borrar el historial al terminar el pedido borra entrega confirmación y carrito y la app cierra sin repasar pantallas viejas

- ¿Qué tuviste que corregir del código que te generó la IA para el buscador en tiempo real?

> lo principal fue que el filtro perdía la categoría al escribir ahora une los dos con && y categoriaSeleccionada y textoBusqueda viven juntos en remember también corregí que la X solo salga con texto y agregué mensaje de sin resultados con botón limpiar búsqueda

- Compara el NavigationDrawer del Laboratorio 6 con el NavigationBar de esta tarea: ¿en qué caso usarías cada uno en un proyecto propio?

> el NavigationDrawer esconde muchas secciones tras el menú hamburguesa y sirve para perfiles ajustes o catálogos largos la NavigationBar deja tres o cinco destinos visibles a un clic del pulgar en mi proyecto usaría la barra para inicio carrito y perfil y el drawer si hay muchas secciones

## Conclusiones

- Aprendí a levantar una app completa con Compose usando estado compartido y navegación con rutas
- Entendí que crear una pantalla y conectarla con su ruta son dos pasos distintos
- Con popUpTo evité amontonar pantallas en el historial y logré que el pedido termine limpio
- Practiqué el ciclo de trabajo commit compilar probar capturar documentar
- Verifiqué el registro guardado en el dispositivo y su uso para precargar la entrega
- Voy a la fase 2 con el flujo completo funcionando de punta a punta
