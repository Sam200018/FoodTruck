package mx.ipn.escom.bautistas.foodtruck.ui.main.clusters


import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.clustering.ClusterItem

/**
 * Clase de datos que implementa la interfaz [ClusterItem] para representar un elemento de clúster en el mapa.
 *
 * @property id Identificador único del elemento de clúster.
 * @property title Título asociado al elemento de clúster.
 * @property snippet Información adicional del elemento de clúster.
 * @property polygonOptions Opciones de polígono asociadas al elemento de clúster.
 */
data class ZoneClusterItem(
    val id: String,
    private val title: String,
    private val snippet: String,
    val polygonOptions: PolygonOptions
) : ClusterItem {

    /**
     * Obtiene la información adicional (snippet) del elemento de clúster.
     *
     * @return Información adicional del elemento de clúster.
     */
    override fun getSnippet() = snippet

    /**
     * Obtiene el título del elemento de clúster.
     *
     * @return Título del elemento de clúster.
     */
    override fun getTitle() = title

    /**
     * Obtiene la posición del elemento de clúster, que es el centro del polígono asociado.
     *
     * @return Posición central del polígono asociado al elemento de clúster.
     */
    override fun getPosition() = polygonOptions.points.getCenterOfPolygon().center
}