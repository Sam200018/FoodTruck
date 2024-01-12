package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextUnderlinedButton(
    modifier: Modifier = Modifier,
    label: String = "",
    action: () -> Unit,

    ) {
    TextButton(onClick = { action() }, modifier.fillMaxWidth()) {
        Text(
            text = label,
            textDecoration = TextDecoration.Underline
        )
    }

}

@Preview
@Composable
fun TextUnderlinedButtonPrev() {
    TextUnderlinedButton() {}
}