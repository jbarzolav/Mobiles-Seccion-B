# Clínica Salud+ — Reserva de citas médicas

**Opción A** — App Android con Jetpack Compose: reserva de citas médicas con menú lateral (drawer).

## Flujo principal

Inicio → Perfil del médico → Agendar cita → Confirmación

## Commits

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![commit 1](imagenes/imagen%201.jpeg) | Se creó el proyecto base de Clínica Salud+ con Jetpack Compose |
| 2 | ![commit 2](imagenes/imagen%202.jpeg) | Se agregaron modelos de datos, datos de ejemplo y rutas de navegacion |
| 3 | ![commit 3](imagenes/imagen%203.jpeg) | Se creo la pantalla de Inicio con Scaffold, topBar e icono de menu |
| 4 | ![commit 4](imagenes/imagen%204.jpeg) | Se creo el menu lateral drawer con 4 destinos |
| 5 | ![commit 5](imagenes/imagen%205.jpeg) | Se agrego el LazyRow de especialidades con chips de filtro |
| 6 | ![commit 6](imagenes/imagen%206.6.jpeg) | Se agrego el LazyColumn con la lista de medicos y filtro por especialidad |
| 7 | ![commit 7](imagenes/imagen%207.jpeg) | Se creo la pantalla de perfil del medico con boton Agendar cita |
| 8 | ![commit 8](imagenes/imagen%208.jpeg) | Se creo la pantalla de agendar cita con seleccion unica de fecha y hora |
| 9 | ![commit 9](imagenes/imagen%209.jpeg) | Se creo la pantalla de confirmacion de la cita agendada |
| 10 | ![commit 10](imagenes/imagen%2010.jpeg) | Se crearon las pantallas Mis citas, Historial medico y Perfil |

## Resultados

| # | Imagen | Pantalla |
|---|--------|----------|
| 1 | ![resultado 1](imagenes/imagen%201.1.jpeg) | Pantalla inicial (plantilla) |
| 2 | ![resultado 2](imagenes/imagen%202.2.jpeg) | Pantalla inicial (plantilla) |
| 3 | ![resultado 3](imagenes/imagen%203.3.jpeg) | Inicio con Scaffold y topBar |
| 4 | ![resultado 4](imagenes/imagen%204.4.jpeg) | Menú lateral drawer con 4 destinos |
| 5 | ![resultado 5](imagenes/imagen%205.5.jpeg) | LazyRow de especialidades |
| 6 | ![resultado 6](imagenes/imagen%206.jpeg) | LazyColumn de médicos |
| 7 | ![resultado 7](imagenes/imagen%207.7.jpeg) | Perfil del médico |
| 8 | ![resultado 8](imagenes/imagen%208.8.jpeg) | Agendar cita (selección única) |
| 9 | ![resultado 9](imagenes/imagen%209.9.jpeg) | Confirmación de la cita |
| 10 | ![resultado 10](imagenes/imagen%2010.10.jpeg) | Mis citas con estados |

## Mejoras con IA (Fase 2)

Rama: `mejora-con-ia-semana-5` — Mejora: cancelar cita con AlertDialog de confirmación.

### Commits

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![mejora commit 1](imagenes%20con%20ia/imagen%201%20con%20ia.jpeg) | Se agrego boton cancelar con AlertDialog de confirmacion en Mis citas |
| 2 | ![mejora commit 2](imagenes%20con%20ia/imagen%20del%20segundo%20promt.jpeg) | Se agrego mensaje Cita cancelada con Snackbar y estado vacio mejorado |
| 3 | ![mejora commit 3](imagenes%20con%20ia/imagen%20del%20tercer%20promt.jpeg) | Se valido cancelar solo citas Confirmadas y se creo PROMPTS.md |

### Resultados

| # | Imagen | Pantalla |
|---|--------|----------|
| 1 | ![mejora 1](imagenes%20con%20ia/segunada%20imagen%20del%20primer%20promp.jpeg) | AlertDialog de confirmación para cancelar cita |
| 2 | ![mejora 2](imagenes%20con%20ia/tercera%20imagen%20del%20primer%20promt.jpeg) | Cita cancelada con Snackbar / Mis citas actualizada |

### Prompts

**Prompt 1 — Solicitud de la mejora:**

> Agrega la función de cancelar una cita en la pantalla Mis citas. Cuando el usuario toque el botón "Cancelar" en una cita, debe mostrarse un AlertDialog de confirmación con el mensaje "¿Deseas cancelar esta cita?" y los botones "Sí, cancelar" y "No". Si confirma, la cita debe eliminarse de la lista.

**Prompt 2 — Corrección tras probar:**

> La cita se elimina pero necesito que al confirmar la cancelación también se muestre un mensaje breve de "Cita cancelada" y que si el usuario elige "No" el diálogo se cierre sin eliminar nada. Además la lista no debe quedar vacía visualmente sin un texto indicando que no hay citas.

**Prompt 3 (opción B) — Validación de estado:**

> Valida que solo se puedan cancelar citas con estado "Confirmada". Si la cita está "Completada", no debe mostrarse el botón de cancelar porque esa cita ya se realizó.
