package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.foodtruck.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.UserTypeSelector
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.MapViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authState: AuthState,
    authViewModel: AuthViewModel,
    mapViewModel: MapViewModel,
    signOut: () -> Unit,
) {
    when (authState.userData?.userType) {
        null -> {
            SelectType(authViewModel = authViewModel, signOut = signOut)
        }

        "Cliente" -> {
            Orders(
                modifier = modifier, signOut = signOut
            )
        }

        "Empleado" -> {
            Empleado(
                modifier = modifier,
                signOut = signOut,
                mapViewModel = mapViewModel
            )
        }

    }

}

@Composable
fun SelectType(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    signOut: () -> Unit,
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Iniciaste sesion con Google!")
                Text(text = "Por favor selecciona el tipo de usuario")
                UserTypeSelector(
                    value = authViewModel.userType, onSelectOption = {
                        authViewModel.onUserTypeChange(it)
                    })
                ButtonComponent(label = "Continuar", isEnable = authViewModel.userType.isNotEmpty()) {
                    authViewModel.updateUserInfo()
                }
            }
        }
    }
}

@Composable
fun Orders(
    modifier: Modifier = Modifier, signOut: () -> Unit
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column() {
                Text(text = "Cliente")
                Button(onClick = {
                    signOut()
                }) {
                    Text(text = "Cerrar sesion")
                }

            }
        }
    }
}


@Composable
fun Empleado(
    modifier: Modifier = Modifier,
    signOut: () -> Unit,
    mapViewModel: MapViewModel,
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Empleado")

                Button(onClick = { signOut() }) {
                    Text(text = "Cerrar sesion")
                }
                MapScreen(
                    state = mapViewModel.state.value, // Obtiene el estado del mapa desde el ViewModel
                    setupClusterManager = mapViewModel::setupClusterManager,
                    calculateZoneViewCenter = mapViewModel::calculateZoneLatLngBounds
                )
            }
        }
    }
}

