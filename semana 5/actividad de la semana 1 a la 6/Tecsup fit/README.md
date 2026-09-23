# TECSUP Fit — Reserva de clases de gimnasio

**Opción B** — App Android con Jetpack Compose: reserva de clases de gimnasio con bottomBar de Scaffold para navegar entre secciones.

## Flujo principal

Inicio → Detalle de clase → Confirmación

## Commits

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![commit 1](imagenes/imagen%20del%20comit%201.jpeg) | Se creo el proyecto inicial de TECSUP Fit |
| 2 | ![commit 2](imagenes/imagen%20del%20comit%202.jpeg) | Se agregaron modelos de datos, datos de ejemplo y rutas de navegacion |
| 3 | ![commit 3](imagenes/imagen%20del%20comit%203.jpeg) | Se creo la pantalla de Inicio con Scaffold, topBar y bottomBar |
| 4 | ![commit 4](imagenes/imagen%20del%20comit%204.jpeg) | Se creo el bottomBar con 4 pestanas reutilizable |
| 5 | ![commit 5](imagenes/imagen%20del%20comit%205.jpeg) | Se agrego el LazyRow con chips de filtro Hoy y Esta semana |
| 6 | ![commit 6](imagenes/primer%20imagen%20del%20commit%206.jpeg) | Se agrego el LazyColumn con la lista de clases y filtro por chip |
| 7 | ![commit 7](imagenes/imagen%20del%20comit%207.jpeg) | Se creo la pantalla de detalle de clase con boton Reservar cupo |
| 8 | ![commit 8](imagenes/primer%20imagen%20del%20comit%208.jpeg) | Se crearon las pantallas Reservas, Rutinas y Perfil |

## Resultados

| # | Imagen | Pantalla |
|---|--------|----------|
| 1 | ![resultado 1](imagenes/segunda%20imagen%20del%20comit%203.jpeg) | Inicio con Scaffold, topBar y bottomBar |
| 2 | ![resultado 2](imagenes/segunda%20imagen%20del%20comit%204.jpeg) | bottomBar con 4 pestañas e ícono activo |
| 3 | ![resultado 3](imagenes/segunda%20imagen%20del%20comit%205.jpeg) | LazyRow de chips Hoy / Esta semana |
| 4 | ![resultado 4](imagenes/segunda%20imagen%20del%20comit%206.jpeg) | LazyColumn de clases disponibles |
| 5 | ![resultado 5](imagenes/tercera%20imagen%20del%20comit%206.jpeg) | Filtro de clases por chip |
| 6 | ![resultado 6](imagenes/segunda%20imagen%20del%20comit%207.jpeg) | Detalle de clase con botón Reservar cupo |
| 7 | ![resultado 7](imagenes/tercer%20imagen%20del%20comit%207.jpeg) | Confirmación de reserva |
| 8 | ![resultado 8](imagenes/segunada%20imagendel%20comit%208.jpeg) | Mis reservas con estados Confirmada / Completada |
| 9 | ![resultado 9](imagenes/tercera%20imagen%20del%20comit%208.jpeg) | Rutinas |
| 10 | ![resultado 10](imagenes/imgen%204%20del%20comit%208.jpeg) | Mi perfil con estadísticas |

## Mejoras con IA — Fase 2

**Rama:** `mejora-con-ia-semana-5` — Mejoras de reserva con IA.

### Commits

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![mejora commit 1](imagenes%20con%20ia/imagen%20del%20promt%201.jpeg) | Se agrego AlertDialog de confirmacion al reservar cupo en Detalle de clase |
| 2 | ![mejora commit 2](imagenes%20con%20ia/imagen%201%20del%20promt%202.jpeg) | Se agrego Snackbar de reserva exitosa en la pantalla de Confirmacion |
| 3 | ![mejora commit 3](imagenes%20con%20ia/imagen%201%20del%20promt%203.jpeg) | Se valido cancelar solo reservas Confirmadas y se creo PROMPTS.md |

### Resultados

| # | Imagen | Pantalla |
|---|--------|----------|
| 1 | ![mejora 1](imagenes%20con%20ia/imagen%202%20del%20promt%201.jpeg) | AlertDialog "¿Deseas reservar esta clase?" en Detalle |
| 2 | ![mejora 2](imagenes%20con%20ia/imagen%202%20del%20promt%202.jpeg) | Snackbar "Reserva registrada con éxito" en Confirmación |
| 3 | ![mejora 3](imagenes%20con%20ia/imagen%202%20del%20promt%203.jpeg) | Mis reservas con botón Cancelar según estado |

### Prompts

**Prompt 1 — Solicitud de la mejora:**

> En la pantalla de Detalle de clase, al presionar el botón "Reservar cupo" se muestra un AlertDialog de confirmación con el mensaje "¿Deseas reservar esta clase?" y las acciones "Cancelar" y "Aceptar". Si acepta, se navega a la Confirmación de reserva; si cancela, se cierra el diálogo. Usa solo remember y mutableStateOf, sin ViewModel.

**Prompt 2 — Corrección tras probar:**

> En la pantalla de Confirmación de reserva, después de que el usuario pulsa "Volver al inicio", muestra un Snackbar con el mensaje "Reserva registrada con éxito" y luego navega al Home. Usa el Scaffold ya existente y el SnackbarHost correspondiente.

**Prompt 3 (opción B) — Validación de estado:**

> En Mis reservas, el botón "Cancelar" de cada ReservaCard debe estar deshabilitado cuando el estado sea "Completada", de modo que solo se puedan cancelar reservas con estado "Confirmada". Valida con remember y mutableStateOf, sin ViewModel.
