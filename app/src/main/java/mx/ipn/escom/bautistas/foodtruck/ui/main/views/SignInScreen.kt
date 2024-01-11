package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import mx.ipn.escom.bautistas.foodtruck.R
import mx.ipn.escom.bautistas.foodtruck.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.CircularButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onGoogleSignIn: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state) {
        state.errorMessage?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold {
        Box(modifier = modifier.padding(it)) {
            Column(
                modifier = modifier.padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier
                        .height(240.dp)
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.sing_in_main_text))
                }
                TextFieldComponent(
                    label = stringResource(id = R.string.email_label),
                    onChanged = {})
                TextFieldComponent(
                    label = stringResource(id = R.string.password_label),
                    onChanged = {})
                Spacer(modifier = modifier.height(10.dp))
                ButtonComponent(label = stringResource(id = R.string.button_sign_in_label)) {

                }
                Text(text = stringResource(id = R.string.sign_in_with_text))
                CircularButtonComponent {
                    onGoogleSignIn()
                }

            }
        }
    }
}

//@Preview
//@Composable
//fun SignInScreenPrev() {
//    SignInScreen(){}
//}