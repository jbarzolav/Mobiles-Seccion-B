# Prompts — TECSUP Fit (Opción B)

**Rama:** `mejora-con-ia`
**Meta:** 3 mejoras aplicadas con IA de Android Studio.

---

## Prompt 1 — Solicitud de la mejora

En la app TECSUP Fit, en la pantalla de Detalle de clase (`DetalleClaseScreen.kt`), quiero que al presionar el botón "Reservar cupo" se muestre un AlertDialog de confirmación con el mensaje "¿Deseas reservar esta clase?" y las acciones "Cancelar" y "Aceptar". Si el usuario acepta, se navega a la pantalla de Confirmación de reserva; si cancela, se cierra el diálogo y no ocurre nada. Usa solo `remember` y `mutableStateOf`, sin ViewModel.

## Prompt 2 — Corrección tras probar

En la pantalla de Confirmación de reserva (`ConfirmacionReservaScreen.kt`), después de que el usuario pulsa "Volver al inicio", muestra un Snackbar con el mensaje "Reserva registrada con éxito" y luego navega al Home. Usa el Scaffold ya existente de la pantalla y el `SnackbarHost` correspondiente.

## Prompt 3 (opción B) — Validación de estado

En la pantalla Mis reservas (`ReservasScreen.kt`), cada `ReservaCard` debe mostrar su estado "Confirmada" o "Completada" y, si la reserva está "Completada", el botón "Cancelar" debe estar deshabilitado (deshabilitado = no se puede cancelar una reserva completada). Valida que solo se pueda cancelar reservas con estado "Confirmada".
