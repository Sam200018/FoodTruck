package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.foodtruck.R
import mx.ipn.escom.bautistas.foodtruck.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.CircularButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.TextUnderlinedButton
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.formValid
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.SignInViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authState: AuthState,
    onSignIn: () -> Unit,
    navigateToSignUp: () -> Unit,
    onGoogleSignIn: () -> Unit,
    signInViewModel: SignInViewModel,
) {


    val context = LocalContext.current

    LaunchedEffect(key1 = authState) {
        if (authState.errorMessage != null) {

            authState.errorMessage.let { error ->
                Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val signInState by signInViewModel.signInState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = signInState) {
        signInState.errorMessage?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(bottomBar = {
        TextUnderlinedButton(label = stringResource(id = R.string.sign_up_main_text)) {
            navigateToSignUp()
        }

    }) {
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
                    Text(text = stringResource(R.string.sign_in_main_text))
                }
                TextFieldComponent(
                    value = signInViewModel.emailValue,
                    isError = signInState.isEmailValid.not(),
                    errorMessage = stringResource(id = R.string.email_not_valid),
                    label = stringResource(id = R.string.email_label),
                    onChanged = {
                        signInViewModel.onEmailChange(it)
                    })
                TextFieldComponent(
                    value = signInViewModel.passwordValue,
                    isError = signInState.isPasswordValid.not(),
                    errorMessage = stringResource(id = R.string.password_not_valid),
                    label = stringResource(id = R.string.password_label),
                    onChanged = {
                        signInViewModel.onPasswordChange(it)
                    })
                Spacer(modifier = modifier.height(10.dp))
                ButtonComponent(
                    label = if (signInState.isLoading.not())
                        stringResource(id = R.string.button_sign_in_label)
                    else
                        stringResource(id = R.string.loading_text),
                    isEnable = signInState.formValid() or signInState.isLoading
                ) {
                    onSignIn()
                }
                Text(text = stringResource(id = R.string.sign_in_with_text))
                CircularButtonComponent {
                    onGoogleSignIn()
                }

            }
        }
    }
}

