# PROMPTS.md — Fase 2: Mejora con IA

Rama: `mejora-con-ia-semana-5`

Mejora implementada: **cancelar una cita con AlertDialog de confirmación** en la pantalla Mis citas.

---

## Prompt 1

**Qué se pidió:**

> Agrega la función de cancelar una cita en la pantalla Mis citas. Cuando el usuario toque el botón "Cancelar" en una cita, debe mostrarse un AlertDialog de confirmación con el mensaje "¿Deseas cancelar esta cita?" y los botones "Sí, cancelar" y "No". Si confirma, la cita debe eliminarse de la lista.

**Resultado:** Se agregó el botón Cancelar en `CitaCard.kt`, el AlertDialog en `MisCitasScreen.kt` y se cambió `SampleData.citasAgendadas` a `mutableStateListOf` para que la lista sea observable y la pantalla se actualice en tiempo real al eliminar.

**Qué hubo que corregir:** La lista original era `listOf` (inmutable), por lo que no se podía eliminar una cita; se cambió a `mutableStateListOf`.

**Commit:** `Se agrego boton cancelar con AlertDialog de confirmacion en Mis citas`

---

## Prompt 2

**Qué se pidió:**

> La cita se elimina pero necesito que al confirmar la cancelación también se muestre un mensaje breve de "Cita cancelada" y que si el usuario elige "No" el diálogo se cierre sin eliminar nada. Además la lista no debe quedar vacía visualmente sin un texto indicando que no hay citas.

**Resultado:** Se agregó `SnackbarHost` con el mensaje "Cita cancelada", el botón "No" asigna `null` a la cita a cancelar cerrando el diálogo sin cambios, y el estado vacío muestra "No tienes citas agendadas" con estilo `bodyLarge`.

**Qué hubo que corregir:** Al editar el archivo se rompieron las llaves de cierre del `LazyColumn` y del `else`; se corrigió la estructura y se verificó con `compileDebugKotlin`.

**Commit:** `Se agrego mensaje Cita cancelada con Snackbar y estado vacio mejorado`

---

## Prompt 3

**Qué se pidió:**

> Valida que solo se puedan cancelar citas con estado "Confirmada". Si la cita está "Completada", no debe mostrarse el botón de cancelar porque esa cita ya se realizó.

**Resultado:** En `CitaCard.kt` el botón Cancelar solo se muestra cuando `cita.estado == EstadoCita.CONFIRMADA`, y en `MisCitasScreen.kt` se validó el estado antes de eliminar la cita del `AlertDialog`.

**Qué hubo que corregir:** Se agregó además la validación dentro del `confirmButton` del AlertDialog para doble seguridad, y se verificó que compile correctamente.

**Commit:** `Se valido cancelar solo citas Confirmadas y se creo PROMPTS.md`
