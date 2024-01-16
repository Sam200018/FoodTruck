package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.ipn.escom.bautistas.foodtruck.ui.main.clusters.ZoneClusterManager
import mx.ipn.escom.bautistas.foodtruck.ui.main.clusters.calculateCameraViewPoints
import mx.ipn.escom.bautistas.foodtruck.ui.main.clusters.getCenterOfPolygon
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.MapState
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    // Estado del mapa que se observa desde la interfaz de usuario
    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            clusterItems = listOf()
        )
    )

    // Método para obtener la ubicación actual del dispositivo
    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            // Obtener la última ubicación conocida del dispositivo
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Actualizar el estado con la última ubicación conocida
                    state.value = state.value.copy(
                        lastKnownLocation = task.result,
                    )
                }
            }
        } catch (e: SecurityException) {
            // Manejar la excepción de seguridad (por ejemplo, mostrar un mensaje de error)
        }
    }

    // Método para configurar y devolver un objeto ZoneClusterManager
    fun setupClusterManager(
        context: Context,
        map: GoogleMap,
    ): ZoneClusterManager {
        val clusterManager = ZoneClusterManager(context, map)
        clusterManager.addItems(state.value.clusterItems)
        return clusterManager
    }

    // Método para calcular los límites de latitud y longitud para ajustar la cámara del mapa
    fun calculateZoneLatLngBounds(): LatLngBounds {
        // Obtener todos los puntos de todos los polígonos y calcular la vista de cámara que los mostrará todos.
        val latLngs = state.value.clusterItems.map { it.polygonOptions }
            .map { it.points.map { LatLng(it.latitude, it.longitude) } }.flatten()
        return latLngs.calculateCameraViewPoints().getCenterOfPolygon()
    }

    companion object {
        private val POLYGON_FILL_COLOR = Color.parseColor("#ABF44336")
    }
}