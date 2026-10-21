# Prompts utilizados (Fase 2 - IA)

## Prompt 1
**Texto enviado a Gemini:** 
Tengo una app Kotlin con Jetpack Compose, Material 3, MVVM, Room y un AyudaRepository (interface). Agrega en el panel de soporte el tiempo promedio de resolucion de los tickets cerrados (fechaCierre - fecha). La consulta va en TicketDao, la funcion en AyudaRepository y su implementacion, y el valor se expone como StateFlow en PanelViewModel. La View solo lo muestra. No uses XML. Muestrame solo los archivos que cambian.

**Que genero la IA:**
- Consulta SQL en TicketDao usando `AVG(fechaCierre - fecha)`.
- Adicion de la funcion en el contrato `AyudaRepository` y su implementacion en `AyudaRepositoryRoom`.
- Exposicion del StateFlow `tiempoPromedioResolucion` en `PanelViewModel`.
- Tarjeta visual en `PanelScreen`.

**Que tuve que corregir yo:**
- Asegurar que la division y conversion a horas maneje correctamente valores nulos o vacios sin arrojar excepciones.

**Respeta MVVM?:** Sí. La View se limita a observar el StateFlow del ViewModel, el ViewModel consume el repositorio y este invoca al DAO de Room, manteniendo las capas desacopladas.

---

## Prompt 2
**Texto enviado a Gemini:** 
En una app de tickets en Jetpack Compose, necesito calcular y mostrar el SLA restante para cada ticket segun su prioridad (Alta: 4h, Media: 24h, Baja: 72h) usando la fecha de creacion. Crea una funcion de calculo y muestrala como una etiqueta o contador en el componente TicketCard de la interfaz.

**Que genero la IA:**
- Funcion de calculo `calcularSlaRestante` basada en la prioridad y milisegundos transcurridos.
- Surface/badge de texto en `TicketCard` para visualizar el tiempo restante o si el SLA ha vencido.

**Que tuve que corregir yo:**
- Pasar el parametro de fecha directamente desde las pantallas contenedoras (`MisTicketsScreen` y `BandejaScreen`) hacia la `TicketCard`.

**Respeta MVVM?:** Sí. La logica de calculo de tiempos de SLA es pura y se procesa antes de renderizar la tarjeta en el componente UI.

---

## Prompt 3
**Texto enviado a Gemini:** 
Necesito implementar busqueda avanzada y filtros multiples en BandejaViewModel para que Soporte TI pueda filtrar tickets simultaneamente por texto (titulo, id o cliente), estado, categoria y prioridad usando coroutines Flow y combine. Muestrame las modificaciones en BandejaViewModel y BandejaScreen.

**Que genero la IA:**
- Uso de `combine` en `BandejaViewModel` para fusionar el flujo de todos los tickets con los StateFlows de estado, prioridad, categoria y busqueda de texto.
- Logica de filtrado con `.filter { ... }`.
- Adicion de barra de busqueda y filas de chips de filtros en `BandejaScreen`.

**Que tuve que corregir yo:**
- Ajustar los nombres de los estados de flujo privados y publicos (`_estado`, `_prioridad`, etc.) para que se integren de manera limpia con los componentes composables existentes.

**Respeta MVVM?:** Sí. La logica de filtrado se centraliza en el `BandejaViewModel` combinando flujos reactivos, mientras que la `View` unicamente envia los eventos de filtro y observa los resultados filtrados.

---

## Reflexion
- **Que mejoro la IA y que no?** La IA me ayudó a acelerar la implementacion de consultas agregadas en Room, el operador `combine` para flujos reactivos y el calculo de SLA. Sin embargo, en el diseño visual tuve que ajustar los margenes y estilos para asegurar que cumplan con Material 3.
- **Que error cometio?** En algunas propuestas iniciales, la IA intentó colocar logica de negocio y calculos directamente dentro de los composables, lo cual corregí para mantener la arquitectura MVVM estricta.
- **Que aprendi sobre MVVM, Room y reglas de negocio?** Aprendí que la combinacion de `Flow` y `combine` en ViewModels facilita enormemente la busqueda avanzada y filtros multiples en tiempo real, manteniendo las capas desacopladas del acceso a datos.
