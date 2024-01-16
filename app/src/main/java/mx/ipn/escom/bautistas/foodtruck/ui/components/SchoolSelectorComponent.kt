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

@Composable
fun SchoolSelectorComponent(
    modifier: Modifier = Modifier,
    isError: Boolean= true,
    select: (Int) -> Unit
) {
    var schoolMenuOpen by remember {
        mutableStateOf(false)
    }
    var school by remember {
        mutableStateOf("")
    }
    val options = listOf(
        "ESCOM",
        "ESFM",
        "ESIA(Tecamachalco)",
        "ESIA(Ticoman)",
        "ESIA(Zacatenco)",
        "ESIME(Azcapotzalco)",
        "ESIME(Culhuacan)",
        "ESIME(Ticoman)",
        "ESIME(Zacatenco)",
        "ESIQIE",
        "ESIT",
    )

    ExposedDropdownMenuBox(expanded = schoolMenuOpen, onExpandedChange = {
        schoolMenuOpen = it
    }) {

        Column {
            TextField(
                label = {
                    Text(text = stringResource(id = R.string.select_school))
                },
                modifier = modifier.menuAnchor(),
                value = school,
                readOnly = true,
                isError = isError,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = schoolMenuOpen)
                })
            Spacer(modifier = modifier.height(5.dp))
        }
        ExposedDropdownMenu(
            expanded = schoolMenuOpen,
            onDismissRequest = { schoolMenuOpen = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                    select(index)
                    schoolMenuOpen = false
                    school = options[index]
                }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
            }

        }

    }
}

@Preview
@Composable
fun SchoolSelectorPrev() {
    SchoolSelectorComponent() {}
}