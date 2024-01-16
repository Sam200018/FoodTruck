package mx.ipn.escom.bautistas.foodtruck.ui.main.clusters


import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager

/**
 * Clase que extiende [ClusterManager] y se encarga de la gestión de clústeres en el mapa.
 *
 * @param context Contexto de la aplicación.
 * @param googleMap Instancia de [GoogleMap] en la que se realizará la gestión de clústeres.
 */
class ZoneClusterManager(
    context: Context,
    googleMap: GoogleMap,
): ClusterManager<ZoneClusterItem>(context, googleMap, MarkerManager(googleMap))
