package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import android.content.ClipData.Item
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.foodtruck.R
import mx.ipn.escom.bautistas.foodtruck.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.OrderComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.UserTypeSelector
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.OrdersState
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.MapViewModel
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.OrdersViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authState: AuthState,
    ordersViewModel: OrdersViewModel,
    authViewModel: AuthViewModel,
    mapViewModel: MapViewModel,
    goToNewOrder: () -> Unit,
    signOut: () -> Unit,
) {
    when (authState.userData?.userType) {
        null -> {
            SelectType(authViewModel = authViewModel)
        }

        "Cliente" -> {
            Orders(
                modifier = modifier, ordersViewModel, goToNewOrder, signOut = signOut
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
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.home_main_text))
                Text(text = stringResource(id = R.string.select_user_type_text))
                UserTypeSelector(
                    value = authViewModel.userType, onSelectOption = {
                        authViewModel.onUserTypeChange(it)
                    })
                ButtonComponent(
                    label = stringResource(id = R.string.continue_label_text),
                    isEnable = authViewModel.userType.isNotEmpty()
                ) {
                    authViewModel.updateUserInfo()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orders(
    modifier: Modifier = Modifier,
    ordersViewModel: OrdersViewModel,
    goToNewOrder: () -> Unit,
    signOut: () -> Unit,
) {
    val state by ordersViewModel.ordersState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = goToNewOrder) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
        topBar = {
            TopAppBar(title = {

                Text(
                    text = stringResource(id = R.string.orders_main_text),
                    style = MaterialTheme.typography.headlineMedium
                )
            })
        }

    ) {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            if (state.ordersList.isNotEmpty()) {
                LazyColumn(
                    modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    item {
                        Column(modifier.padding(10.dp)) {
                            Row(
                                modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "id", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Producto", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Cantidad", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Status", style = MaterialTheme.typography.titleLarge)
                            }
                            Divider()

                        }
                    }
                    items(state.ordersList.size) {
                        OrderComponent(order = state.ordersList[it])
                    }
                }
            } else {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "No hay ordenes")
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

