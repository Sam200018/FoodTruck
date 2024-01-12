package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.foodtruck.ui.components.TextFieldComponent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    signOut: () -> Unit
) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            Button(onClick = {
                signOut()
            }) {
                Text(text = "Cerrar sesion")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPrev() {
    HomeScreen() {}
}