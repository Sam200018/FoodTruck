package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.foodtruck.R
import mx.ipn.escom.bautistas.foodtruck.ui.components.TextFieldComponent

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier
) {
    Scaffold {
        Box(modifier.padding(it)) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(20.dp)) {
                Text(text = "Selecciona tu pedido")
                TextFieldComponent(
                    label = stringResource(id = R.string.amount_label),
                    onChanged = {}
                )
                Text(text = "Destino del pedido")
                Text(text = "Selecciona tu escuela")
                Text(text = "Selecciona tu escuela")
                TextFieldComponent(
                    label = stringResource(id = R.string.building_label),
                    onChanged = {}
                )
                TextFieldComponent(
                    label = stringResource(id = R.string.comment_label),
                    onChanged = {}
                )



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPrev() {
    OrderScreen()
}