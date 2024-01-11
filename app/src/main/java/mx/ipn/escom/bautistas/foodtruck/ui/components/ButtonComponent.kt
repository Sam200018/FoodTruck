package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    label: String = "",
    action: () -> Unit,
) {
    OutlinedButton(onClick = action, modifier.fillMaxWidth()) {
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonCompPrev() {
    ButtonComponent(){}
}