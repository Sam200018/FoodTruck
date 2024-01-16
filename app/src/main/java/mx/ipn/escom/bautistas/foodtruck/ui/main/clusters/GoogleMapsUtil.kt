package mx.ipn.escom.bautistas.foodtruck.ui.main.clusters


import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Extensión de lista que calcula el centro de un polígono representado por una lista de puntos [LatLng].
 *
 * @return [LatLngBounds] que representa el centro del polígono.
 */
fun List<LatLng>.getCenterOfPolygon(): LatLngBounds {
    // Se utiliza un constructor de LatLngBounds para construir un límite que incluye todos los puntos del polígono.
    val centerBuilder: LatLngBounds.Builder = LatLngBounds.builder()
    forEach { centerBuilder.include(LatLng(it.latitude, it.longitude)) }
    return centerBuilder.build()
}

/**
 * Función que calcula los puntos de vista de la cámara en un polígono representado por una lista de puntos [LatLng].
 *
 * @param pctView Porcentaje de expansión de la vista de la cámara alrededor del polígono.
 * @return Lista de [LatLng] que representa los puntos de vista de la cámara.
 */
fun List<LatLng>.calculateCameraViewPoints(pctView: Double = 0.25): List<LatLng> {
    // Se obtienen los valores máximos y mínimos de latitud y longitud en el polígono.
    val coordMax = findMaxMins()

    // Se calcula el aumento en latitud y longitud basado en el porcentaje proporcionado.
    val dy = coordMax.yMax - coordMax.yMin
    val dx = coordMax.xMax - coordMax.xMin
    val yT = (dy * pctView) + coordMax.yMax
    val yB = coordMax.yMin - (dy * pctView)
    val xR = (dx * pctView) + coordMax.xMax
    val xL = coordMax.xMin - (dx * pctView)

    // Se devuelven los cuatro puntos que definen la nueva vista de la cámara.
    return listOf(
        LatLng(coordMax.xMax, yT),
        LatLng(coordMax.xMin, yB),
        LatLng(xR, coordMax.yMax),
        LatLng(xL, coordMax.yMin)
    )
}

/**
 * Clase de datos privada que almacena los valores máximos y mínimos de latitud y longitud.
 *
 * @property yMax Valor máximo de longitud.
 * @property yMin Valor mínimo de longitud.
 * @property xMax Valor máximo de latitud.
 * @property xMin Valor mínimo de latitud.
 */
private data class CameraViewCoord(
    val yMax: Double,
    val yMin: Double,
    val xMax: Double,
    val xMin: Double
)

/**
 * Función privada que encuentra los valores máximos y mínimos de latitud y longitud en una lista de puntos [LatLng].
 *
 * @return [CameraViewCoord] que contiene los valores máximos y mínimos.
 * @throws IllegalStateException si la lista de puntos es vacía.
 */
private fun List<LatLng>.findMaxMins(): CameraViewCoord {
    // Se verifica que la lista de puntos no esté vacía.
    check(size > 0) { "No se pueden calcular las coordenadas de vista de nada." }

    // Se itera sobre la lista de puntos para encontrar los valores máximos y mínimos.
    var viewCoord: CameraViewCoord? = null
    for (point in this) {
        viewCoord = CameraViewCoord(
            yMax = viewCoord?.yMax?.let { yMax ->
                if (point.longitude > yMax) {
                    point.longitude
                } else {
                    yMax
                }
            } ?: point.longitude,
            yMin = viewCoord?.yMin?.let { yMin ->
                if (point.longitude < yMin) {
                    point.longitude
                } else {
                    yMin
                }
            } ?: point.longitude,
            xMax = viewCoord?.xMax?.let { xMax ->
                if (point.latitude > xMax) {
                    point.latitude
                } else {
                    xMax
                }
            } ?: point.latitude,
            xMin = viewCoord?.xMin?.let { xMin ->
                if (point.latitude < xMin) {
                    point.latitude
                } else {
                    xMin
                }
            } ?: point.latitude,
        )
    }
    // Se devuelve el objeto CameraViewCoord resultante o se lanza una excepción si es nulo.
    return viewCoord ?: throw IllegalStateException("viewCoord no puede ser nulo.")
}
