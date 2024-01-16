package mx.ipn.escom.bautistas.foodtruck.data.geolocation

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationDataSource @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient

) {


    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return  fusedLocationProviderClient.lastLocation.await()

    }



}