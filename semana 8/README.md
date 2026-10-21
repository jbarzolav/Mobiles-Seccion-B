# AyudaCampus - Sistema de Gestion de Tickets Academicos (Fase 1)

Aplicacion Android desarrollada en Jetpack Compose y Kotlin para la gestion de incidencias y tickets en el campus universitario. Permite un flujo completo tanto para estudiantes como para el personal de Soporte Tecnico TI.

---

## Arbol de Paquetes
```text
com.tecsup.ayudacampus/
├── data/
│   ├── local/
│   │   ├── dao/ (CategoriaDao, TicketDao, UsuarioDao)
│   │   ├── entity/ (Categoria, Comentario, Ticket, Usuario)
│   │   ├── relation/ (TicketConDetalle)
│   │   ├── AyudaDatabase.kt
│   │   └── DatosIniciales.kt
│   ├── prefs/ (PreferenciasStore - DataStore)
│   └── repository/ (AyudaRepository, AyudaRepositoryRoom)
├── model/ (ConteoCategoria, EstadoTicket, Prioridad, Rol)
├── ui/
│   ├── cliente/ (DetalleTicketScreen, LoginScreen, MisTicketsScreen, NuevoTicketScreen, PerfilScreen, SplashScreen)
│   ├── components/ (AppTopBar, AyudaButton, AyudaTextField, BurbujaComentario, EstadoChip, Estrellas, PrioridadChip, StepperTicket, TicketCard, graficos/)
│   ├── navigation/ (NavAyuda, Rutas)
│   ├── soporte/ (AjustesScreen, AtenderTicketScreen, BandejaScreen, CategoriasScreen, FormularioCategoriaScreen, PanelScreen)
│   └── theme/ (Color, Theme, Type)
└── viewmodel/ (AtenderViewModel, AyudaViewModelFactory, BandejaViewModel, CategoriasViewModel, MisTicketsViewModel, NuevoTicketViewModel, PanelViewModel, SesionViewModel, TicketDetalleViewModel)
```

---

## Diagrama de Arquitectura (MVVM)
```mermaid
graph TD
    UI[UI / Composable Screens] -->|Eventos de Usuario| VM[ViewModel]
    VM -->|Llamada suspend / Flow| Repo[AyudaRepository]
    Repo -->|DAO / Coroutines| Room[(Room Database)]
    Repo -->|DataStore| Prefs[(DataStore Preferences)]
    Room -->|Flow / StateFlow| VM
    Prefs -->|Flow| VM
    VM -->|StateFlow / UI State| UI
```

---

## Capturas de Pantalla (Pruebas de Aceptacion)

### 1. Login con clave incorrecta
![Login con clave incorrecta](imagenes/login%20con%20clave%20incorrecta.png)

### 2. Estudiante: crear ticket vacio
![Crear ticket vacio](imagenes/crear%20ticket%20vacio.png)

### 3. Estudiante: crear ticket valido
![Crear ticket valido](imagenes/crear%20ticket%20valido.png)

### 4. Soporte: bandeja de tickets
![Bandeja de tickets](imagenes/bandeja.png)

### 5. Soporte: pasar a En proceso y responder
![Pasar a En proceso y responder](imagenes/Soporte%20pasar%20a%20En%20proceso%20y%20responder.png)

### 6. Soporte: intentar cerrar un ticket
![Intentar cerrar un ticket](imagenes/soporte%20intenta%20cerrar%20un%20ticket.png)

### 7. Soporte: marcar como resuelto
![Marcar como resuelto](imagenes/soporte%20marcar%20como%20%20resuelto.png)

### 8. Estudiante: reabrir un ticket resuelto
![Reabrir un ticket resuelto](imagenes/estudiante%20reabrir%20un%20estado%20abierto.png)

---

## Para Pensar (Preguntas y Respuestas)

| Pregunta | Tu respuesta |
| :--- | :--- |
| **¿Por qué las reglas de estado están en el ViewModel y no en el DAO ni en el Composable?** | Porque el ViewModel debe manejar la logica de negocio. El DAO solo trabaja con los datos y el Composable solo muestra la interfaz. |
| **¿Por qué crearTicket es suspend y observarTicket es Flow?** | Porque crearTicket realiza una accion puntual, mientras que observarTicket debe actualizar los datos automaticamente cuando cambian. |
| **¿Qué pasaría si un ticket pudiera cerrarse sin calificacion?** | No se cumpliria la regla de negocio y se perderia la valoracion del estudiante sobre la atencion recibida. |
| **¿Qué debería cambiar si mañana los tickets viven en un servidor?** | Principalmente cambiaria la implementacion del Repository para obtener los datos desde una API, sin tener que modificar los ViewModels ni las pantallas. |
