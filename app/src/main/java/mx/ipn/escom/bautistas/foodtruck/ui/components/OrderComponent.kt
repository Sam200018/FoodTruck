package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.foodtruck.data.oder.model.Order

@Composable
fun OrderComponent(
    modifier: Modifier = Modifier,
    order: Order
) {
    val products = listOf(
        "Torta de huevo con chorizo",
        "Tamal de rojo",
        "Tostada de pata",
        "Higado encebollado",
        "Enslada rusa",
        "Mermelada de chabacano"
    )
    Column(
        modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier.width(50.dp)) {
                Text(text = order.id!!, maxLines = 1, overflow = TextOverflow.Ellipsis)

            }
            Box(modifier = modifier.width(120.dp)) {
                Text(text = products[order.product])

            }
            Text(text = order.amount.toString())
            Text(text = "Status")
        }
        Divider()
    }
}

@Preview(showSystemUi = true)
@Composable
fun OrderPrev() {
    OrderComponent(order = Order(id = "jvajdskajdajiajajaslkjdlsa"))
}
