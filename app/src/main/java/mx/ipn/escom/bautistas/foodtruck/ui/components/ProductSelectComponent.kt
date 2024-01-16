@file:OptIn(ExperimentalMaterial3Api::class)

package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.foodtruck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSelectorComponent(
    modifier: Modifier = Modifier,
    isError: Boolean = true,
    select: (Int) -> Unit
) {
    var productMenuOpen by remember {
        mutableStateOf(false)
    }
    var product by remember {
        mutableStateOf("")
    }
    val options = listOf(
        "Torta de huevo con chorizo",
        "Tamal de rojo",
        "Tostada de pata",
        "Higado encebollado",
        "Enslada rusa",
        "Mermelada de chabacano"
    )

    ExposedDropdownMenuBox(
        expanded = productMenuOpen,
        onExpandedChange = { productMenuOpen = it }) {
        Column {
            TextField(
                label = { Text(text = stringResource(id = R.string.select_product)) },
                modifier = modifier.menuAnchor(),
                value = product,
                readOnly = true,
                isError =isError,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = productMenuOpen)
                })
            Spacer(modifier = modifier.height(5.dp))
        }
        ExposedDropdownMenu(
            expanded = productMenuOpen,
            onDismissRequest = { productMenuOpen = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                    select(index)
                    productMenuOpen = false
                    product = options[index]
                })
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun ProductSelectPrev() {
    ProductSelectorComponent() {}
}