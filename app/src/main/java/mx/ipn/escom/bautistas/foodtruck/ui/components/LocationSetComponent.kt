package mx.ipn.escom.bautistas.foodtruck.ui.components

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mx.ipn.escom.bautistas.foodtruck.R

@Composable
fun LocationSetComponent(
    modifier: Modifier = Modifier,
    location: Location? = null,
    onSetLoc:()->Unit
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.order_destiny))
        IconButton(onClick = onSetLoc) {
            if (location!=null){
                Icon(Icons.Filled.LocationOn, contentDescription ="", tint = Color.Blue)
            }else{
                Icon(Icons.Filled.LocationOn, contentDescription ="", tint = Color.Red)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationSetPrev() {
    LocationSetComponent(){}
}