package mx.ipn.escom.bautistas.foodtruck.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.foodtruck.R

@Composable
fun CircularButtonComponent(
    modifier: Modifier = Modifier,
    image: Painter = painterResource(id = R.drawable.google_icon_logo),
    action: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier
            .size(70.dp)
            .clip(CircleShape), onClick = action
    ) {
        Icon(painter = image, contentDescription = "", modifier.size(70.dp))
    }
}

@Preview
@Composable
fun CircularButtonPrev() {
    CircularButtonComponent(){}
}