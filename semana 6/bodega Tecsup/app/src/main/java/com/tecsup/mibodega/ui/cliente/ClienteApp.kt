package com.tecsup.mibodega.ui.cliente

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntrega
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.cliente.screens.terminos.TerminosScreen

/**
 * "Director de orquesta" de la app cliente:
 * - Tiene el NavHost con las rutas de cada pantalla.
 * - Tiene el estado del carrito (List<ItemCarrito>), que se reparte
 *   hacia abajo a Inicio, Detalle, Carrito y Entrega.
 * Ninguna Screen navega sola ni modifica el carrito directamente:
 * todas reciben funciones (lambdas) desde aquí (state hoisting).
 */
private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val TERMINOS = "terminos"
    const val INICIO = "inicio"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    // El carrito vive aquí arriba, no en ninguna Screen.
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }

    // Datos de entrega: se guardan al confirmar el formulario y los lee
    // la pantalla de confirmación (viajan por estado, no por argumentos).
    var datosEntrega by remember { mutableStateOf<DatosEntrega?>(null) }

    // Contexto para persistir el registro (SharedPreferences) y avisar con Toast.
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA
    ) {
        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onIniciarSesion = { navController.navigate(Rutas.LOGIN) },
                onTerminos = { navController.navigate(Rutas.TERMINOS) }
            )
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                onIniciarSesion = {
                    // Login correcto: se entra a Inicio y se borra el acceso
                    // (Bienvenida y Login) del historial.
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                },
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.TERMINOS) {
            TerminosScreen(onVolver = { navController.popBackStack() })
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onCrearCuenta = { nombre, telefono, direccion, referencia ->
                    // Se guardan en el dispositivo (SharedPreferences); cuando
                    // exista una base de datos real, esto pasa al Repository.
                    guardarRegistro(context, nombre, telefono, direccion, referencia)
                    Toast.makeText(context, "Cuenta creada", Toast.LENGTH_SHORT).show()
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                onVolver = { navController.popBackStack() },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                            else -> null // si llega a 0, se elimina de la lista
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = { navController.navigate(Rutas.ENTREGA) }
            )
        }

        composable(Rutas.ENTREGA) {
            DatosEntregaScreen(
                // Si el usuario ya se registró, el formulario llega precargado.
                datosIniciales = leerRegistro(context),
                onVolver = { navController.popBackStack() },
                onConfirmarPedido = { datos ->
                    datosEntrega = datos
                    navController.navigate(Rutas.CONFIRMACION)
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            // Solo llegamos aquí después de guardar los datos en la pantalla anterior.
            val datos = datosEntrega
            if (datos != null) {
                ConfirmacionScreen(
                    datos = datos,
                    subtotal = carrito.sumOf { it.producto.precio * it.cantidad },
                    onVolverInicio = {
                        // Pedido terminado: se vacía el carrito y se limpia el
                        // historial hasta Inicio, para que la flecha atrás no
                        // regrese a confirmación, entrega ni carrito.
                        carrito = emptyList()
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

/**
 * Si el producto ya está en el carrito, le suma la cantidad;
 * si no, lo agrega como un ItemCarrito nuevo.
 */
private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}

/**
 * Guarda el registro del cliente en SharedPreferences del dispositivo:
 * son los datos que después precargan la pantalla de entrega.
 * Es el "almacén temporal" que pedía el TODO mientras no existe
 * una base de datos real (Room o API).
 */
private fun guardarRegistro(
    context: Context,
    nombre: String,
    telefono: String,
    direccion: String,
    referencia: String
) {
    context.getSharedPreferences("cliente", Context.MODE_PRIVATE)
        .edit()
        .putString("nombre", nombre)
        .putString("telefono", telefono)
        .putString("direccion", direccion)
        .putString("referencia", referencia)
        .apply()
}

/**
 * Devuelve el registro guardado, o null si todavía no se creó ninguna cuenta.
 */
private fun leerRegistro(context: Context): DatosEntrega? {
    val prefs = context.getSharedPreferences("cliente", Context.MODE_PRIVATE)
    val nombre = prefs.getString("nombre", null) ?: return null
    return DatosEntrega(
        nombre = nombre,
        telefono = prefs.getString("telefono", "").orEmpty(),
        direccion = prefs.getString("direccion", "").orEmpty(),
        referencia = prefs.getString("referencia", "").orEmpty(),
        horario = "" // el horario se elige en la pantalla de entrega
    )
}