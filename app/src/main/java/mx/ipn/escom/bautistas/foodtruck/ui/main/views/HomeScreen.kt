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
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authState: AuthState,
    signOut: () -> Unit,
) {
    when (authState.userData!!.userType) {
        null -> {
            SelectType(signOut = signOut)
        }

        "Cliente" -> {
            Orders() {
                signOut
            }
        }

        "Empleado" -> {
            TODO("Cambiar el nombre del composable por lo que vaya pasar aqui")
            Empleado()
        }

    }

}

@Composable
fun SelectType(
    modifier: Modifier = Modifier,
    signOut: () -> Unit,
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
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
fun Orders(
    modifier: Modifier = Modifier, signOut: () -> Unit
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
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
fun Empleado() {
    Text(text = "Aqui va lo de empleado")
}
