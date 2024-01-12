package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserTypeSelector(
    modifier: Modifier = Modifier,
    value: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    onSelectOption: (String) -> Unit
) {
    val radioOptions = listOf("Cliente", "Empleado")
    Column(modifier.selectableGroup()) {
        radioOptions.forEach { option ->
            Row(modifier
                .fillMaxWidth()
                .height(45.dp)
                .selectable(
                    selected = (option == value), onClick = {

                        onSelectOption(option)
                    }
                )
                .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = (option == value), onClick = null)
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier.padding(16.dp)
                )

            }
        }
        if (isError) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = errorMessage, color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserTypeSelectorPrev() {
    UserTypeSelector() {}
}