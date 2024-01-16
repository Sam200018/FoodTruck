package mx.ipn.escom.bautistas.foodtruck.ui.main.views


import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.foodtruck.ui.main.clusters.ZoneClusterManager
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.MapState

@Composable
fun MapScreen(
    state: MapState,
    setupClusterManager: (Context, GoogleMap) -> ZoneClusterManager,
    calculateZoneViewCenter: () -> LatLngBounds,
) {
    // Contador de clics del botón
    var buttonClickCount by remember { mutableStateOf(0) }

    // Propiedades del mapa, incluida la habilitación de la ubicación si se conoce la última ubicación.
    val mapProperties = MapProperties(
        isMyLocationEnabled = state.lastKnownLocation != null,
    )

    // Estado que almacena la posición de la cámara del mapa.
    val cameraPositionState = rememberCameraPositionState()

    // Diseño principal de la pantalla utilizando Jetpack Compose
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Primera mitad: Google Map
        Box(
            modifier = Modifier
                .weight(1f) // Ocupa la mitad superior de la pantalla
                .fillMaxWidth()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = mapProperties,
                cameraPositionState = cameraPositionState
            ) {
                // Código específico del mapa
                val context = LocalContext.current
                val scope = rememberCoroutineScope()

                // Efecto del mapa que maneja la lógica de agrupación y marcadores
                MapEffect(state.clusterItems) { map ->
                    if (state.clusterItems.isNotEmpty()) {
                        val clusterManager = setupClusterManager(context, map)
                        map.setOnCameraIdleListener(clusterManager)
                        map.setOnMarkerClickListener(clusterManager)

                        // Agregar polígonos al mapa
                        state.clusterItems.forEach { clusterItem ->
                            map.addPolygon(clusterItem.polygonOptions)
                        }

                        // Animar la cámara al cargar el mapa
                        map.setOnMapLoadedCallback {
                            if (state.clusterItems.isNotEmpty()) {
                                scope.launch {
                                    cameraPositionState.animate(
                                        update = CameraUpdateFactory.newLatLngBounds(
                                            calculateZoneViewCenter(),
                                            50
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }

                // Ventana de información del marcador
                MarkerInfoWindow(
                    state = rememberMarkerState(position = LatLng(19.323160, -99.120670)),
                    snippet = "Some stuff",
                    onClick = {
                        System.out.println("Mitchs_: No se puede hacer clic")
                        true
                    },
                    draggable = true
                )
            }
        }

        // Segunda mitad: Texto y Botón
        Box(
            modifier = Modifier
                .weight(1f) // Ocupa la mitad inferior de la pantalla
                .fillMaxWidth()
                .padding(16.dp) // Agrega un poco de espacio a los bordes
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Información del usuario
                Text("Usuario")
                Text("Pedido")
                Text("Precio pedido")
                Text("Direccion")

                // Botón de entrega con contador de clics
                Button(onClick = {
                    buttonClickCount++
                    println("Botón clickeado $buttonClickCount veces")
                }) {
                    Text("Entregar")
                }
            }
        }
    }
}