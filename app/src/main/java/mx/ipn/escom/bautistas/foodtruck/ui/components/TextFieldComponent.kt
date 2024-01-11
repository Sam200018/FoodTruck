package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    readOnly: Boolean = false,
    onChanged: (String) -> Unit
) {
    Column {
        TextField(
            value = value,
            onValueChange = onChanged,
            label = {
                Text(text = label)
            },
            readOnly = readOnly,
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = keyboardOptions
        )
        Spacer(modifier = modifier.height(5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldCompPrev() {
    TextFieldComponent(){}
}