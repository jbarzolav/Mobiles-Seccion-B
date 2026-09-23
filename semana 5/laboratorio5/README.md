# Laboratorio 05 — Navegación con Jetpack Compose

**NavLab** — App Android con navegación entre pantallas usando Navigation Compose.

**Rama:** `semana-5-con-ia`

## Commits

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![comit 1](imagenes/comit%201.jpeg) | Creación del proyecto NavLab y configuración de la dependencia de navegación |
| 2 | ![comit 2](imagenes/comit%202.jpeg) | Creación de la estructura de paquetes y archivos |
| 3 | ![comit 3](imagenes/comit%203.jpeg) | Screen.kt — sealed class con rutas |
| 4 | ![comit 4](imagenes/comit%204.jpeg) | AppNavigation.kt — NavHost con rutas y rememberNavController |
| 5 | ![comit 5](imagenes/comit%205.jpeg) | Configuración de MainActivity para iniciar la navegación |
| 6 | ![comit 6](imagenes/comit%206.jpeg) | HomeScreen con botones de navegación a List y Profile |
| 7 | ![comit 7](imagenes/comit%207.jpeg) | ListScreen con Scaffold, TopAppBar y LazyColumn |
| 8 | ![comit 8](imagenes/comit%208.jpeg) | DetailScreen y ProfileScreen |

## Resultados (antes de IA)

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![resultado 1](imagenes/resultado%201.jpeg) | Pantalla de inicio (HomeScreen) |
| 2 | ![resultado 2](imagenes/resultado%202.jpeg) | Lista de elementos (ListScreen) |
| 3 | ![resultado 3](imagenes/resultado%203.jpeg) | Detalle del elemento (DetailScreen) |
| 4 | ![resultado 4](imagenes/resultado%204.jpeg) | Perfil (ProfileScreen) |

## Mejora con IA — Gemini (Android Studio)

Se utilizó **Gemini** dentro de **Android Studio** para mejorar la presentación visual de la primera parte de la app (Portal Académico), aplicando Material 3 con degradés, accesibilidad, modo oscuro y responsividad.

### Prompt utilizado

> Actúa como un profesional senior en diseño UI/UX móvil y desarrollo Android con Jetpack Compose. Tu tarea es mejorar la presentación visual de las siguientes pantallas de una app académica llamada "Portal Académico", manteniendo la funcionalidad existente sin romper la navegación: Login con campos de correo institucional, contraseña, botón INICIAR SESIÓN y enlace "¿Olvidaste tu contraseña?"; Home/Bienvenida con saludo "Bienvenido, Jose Barzola", pregunta "¿Qué deseas gestionar hoy?", dos tarjetas ("Directorio de Alumnos" y "Mi Perfil Académico") y botón "Cerrar Sesión Segura" al pie; Directorio de Alumnos con lista de 5 estudiantes con foto, nombre y carrera; Expediente Académico con header, foto, nombre, carrera, datos (ID Estudiante, Correo, Facultad) y sección Biografía; Configuración de Perfil con header, secciones "INFORMACIÓN PERSONAL" y "ACADÉMICO" con iconos y botón "Cerrar Sesión". Para la mejora visual utiliza Material 3 con degradé (Brush.verticalGradient) en headers, botones y fondos donde aporte profundidad, esquema de colores morados consistente en toda la app, jerarquía visual clara con títulos, subtítulos y contenido diferenciados, sombras sutiles, bordes redondeados consistentes (16.dp tarjetas, 12.dp campos), espaciado uniforme (16.dp o 24.dp), iconos Material en campos y tarjetas, estados visuales para botones, tipografía escalonada (headlineMedium títulos, titleMedium subtítulos, bodyMedium contenido), transiciones suaves con AnimatedVisibility, soporte de modo oscuro con degradés adaptados, accesibilidad con contraste AA, contentDescription en todos los iconos y áreas de toque mínimas de 48.dp, responsividad para distintos tamaños de pantalla, estados de carga y lista vacía, y comentarios breves en el código explicando cada decisión de diseño, logrando un diseño profesional, limpio y moderno tipo app universitaria real. Como restricciones: no modifiques la lógica de navegación ni las rutas en Screen.kt ni AppNavigation.kt, no cambies los nombres de las funciones composable existentes, mantén los parámetros de entrada (navController, itemId, etc.), trabaja solo en la UI sin alterar la estructura del proyecto y asegura que el código compile sin errores, entregando el código completo de cada archivo modificado indicando claramente qué archivo corresponde a cada mejora.

### Resultados mejorados con Gemini

| # | Imagen | Descripción |
|---|--------|-------------|
| 1 | ![imagen 1](imagenes%20con%20ia/imagen%201.png) | Login — Portal Académico mejorado con Material 3 y degradé |
| 2 | ![imagen 2](imagenes%20con%20ia/imagenes%202.png) | Home/Bienvenida — Tarjetas y saludo con diseño mejorado |
| 3 | ![imagen 3](imagenes%20con%20ia/imagen%203.png) | Directorio de Alumnos y Expediente Académico |
| 4 | ![imagen 4](imagenes%20con%20ia/imagen%204.png) | Configuración de Perfil con secciones mejoradas |
