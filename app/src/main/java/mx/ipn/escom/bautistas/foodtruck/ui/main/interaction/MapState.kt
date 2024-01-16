package mx.ipn.escom.bautistas.foodtruck.ui.main.interaction


import android.location.Location
import mx.ipn.escom.bautistas.foodtruck.ui.main.clusters.ZoneClusterItem

/**
 * Clase de datos que representa el estado del mapa.
 *
 * @property lastKnownLocation Última ubicación conocida del dispositivo.
 * @property clusterItems Lista de elementos de clúster para mostrar en el mapa.
 */
data class MapState(
    val lastKnownLocation: Location?,
    val clusterItems: List<ZoneClusterItem>,
)
