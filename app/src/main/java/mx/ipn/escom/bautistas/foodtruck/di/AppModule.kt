package mx.ipn.escom.bautistas.foodtruck.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.GoogleAuthUIClient
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesLocationService(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesGoogleUIClient(
        @ApplicationContext app: Context,
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): GoogleAuthUIClient = GoogleAuthUIClient(
        context = app,
        oneTapClient = Identity.getSignInClient(
            app
        ),
        firebaseAuth,
        firebaseFirestore
    )
}