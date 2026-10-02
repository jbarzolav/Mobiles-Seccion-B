# Lab06 TECSUP Store

## VI Preguntas de reflexión

**1 Por qué el DropdownMenu se declara dentro de un Box junto al ícono que lo activa y no en cualquier parte de la pantalla**

Porque el menú se ancla al nodo donde se declara y ahí mismo vive su estado expanded
Por eso se abre justo debajo del ícono de los tres puntos y cada tarjeta tiene el suyo
Si lo declarara en otra parte aparecería lejos del ícono y sería una sola instancia para todos los productos

**2 Diferencia de alcance entre las opciones del DropdownMenu y las del NavigationDrawer**

Las del menú trabajan estado local dentro de TarjetaProducto y solo afectan a ese producto
Las del drawer cambian opcionSeleccionada que vive en PantallaPrincipalStore y alimenta el bloque when
Por eso afectan a toda la pantalla Inicio Favoritos o Perfil

**3 Cómo estructuré el código para que el contador de favoritos del drawer se entere de lo que pasa en el DropdownMenu de cada producto**

Lo hice con state hoisting es decir subir el estado al ancestro común
En el padre declaro `val favoritos = remember { mutableStateListOf<Int>() }`
A TarjetaProducto le paso `esFavorito` y `onFavoritoCambiado`
Al drawer le paso `badge = { Badge { Text(favoritos.size.toString()) } }`
Al tocar una opción la tarjeta solo llama al callback y quien agrega o quita de la lista es el padre
Como el estado vive arriba Compose recompone lista y drawer a la vez y el badge se actualiza solo

**4 Qué corregí del código que me generó la IA para la mejora del badge de favoritos**

El contador estaba dentro de la tarjeta así que el drawer no se movía lo subí al padre
La IA hacía contador++ dentro del onClick en vez de avisar al padre con la función que recibe
Faltaban imports como `mutableStateListOf` y `Badge` y el ícono no reflejaba si ya era favorito
También verifiqué que el menú se cerrara con `expanded = false` en cada opción

## VII Observaciones y conclusiones

**Observaciones**

1 Al conectar el menú con el drawer el número no cambiaba porque el estado estaba en cada tarjeta y no en el ancestro común
2 En la Fase 2 el filtro de texto reemplazaba al de categoría tuve que pedir que ambos se combinaran con `coincideCategoria && coincideTexto` que el texto vacío no filtrara y agregar `singleLine` y el import de `Close`
3 Documentar los prompts y capturar las imágenes me tomó casi el mismo tiempo que el código pero dejó evidencia de todo el proceso

**Conclusiones**

1 En la Fase 1 escribí y depuré casi todo por mi cuenta más lento pero entendiendo cada línea y en la Fase 2 la IA resolvió rápido por lo que mi rol pasó de escribir a verificar
2 La IA sirve como par programador y no como sustituto lo más valioso fue aprender a pedir concretamente con archivo estado y restricciones y a validar el resultado dejando los prompts documentados como evidencia del razonamiento
