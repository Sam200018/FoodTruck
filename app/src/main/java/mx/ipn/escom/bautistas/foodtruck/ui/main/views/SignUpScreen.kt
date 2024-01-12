package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import mx.ipn.escom.bautistas.foodtruck.ui.components.UserTypeSelector
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.formValid
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.SignUpViewModel

@ExperimentalMaterial3Api
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel,
    onSignUp: () -> Unit,
    back: () -> Unit,
    onGoogleSignIn: () -> Unit,
) {
    val state by signUpViewModel.signUpState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = state.isSuccess) {
        if (state.isSuccess) {
            Toast.makeText(
                context,
                R.string.sign_up_successful_text,
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    LaunchedEffect(key1 = state) {
        state.errorMessage?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { /*TODO*/ }, navigationIcon = {
                IconButton(onClick = { back() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                }
            })
        }
    ) {
        Box(modifier.padding(it)) {
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier
                        .height(50.dp)
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.sign_up_main_text))
                }
                TextFieldComponent(
                    value = signUpViewModel.nameValue,
                    isError = state.isNameValid.not(),
                    errorMessage = stringResource(id = R.string.name_not_valid),
                    label = stringResource(id = R.string.name_label),
                    onChanged = {
                        signUpViewModel.onNameChange(it)
                    })
                TextFieldComponent(
                    value = signUpViewModel.emailValue,
                    isError = state.isEmailValid.not(),
                    errorMessage = stringResource(id = R.string.email_not_valid),
                    label = stringResource(id = R.string.email_label),
                    onChanged = {
                        signUpViewModel.onEmailChange(it)
                    })
                TextFieldComponent(
                    value = signUpViewModel.passwordValue,
                    isError = state.isPasswordValid.not(),
                    errorMessage = stringResource(id = R.string.password_not_valid),
                    label = stringResource(id = R.string.password_label),
                    onChanged = {
                        signUpViewModel.onPasswordChange(it)
                    })
                TextFieldComponent(
                    value = signUpViewModel.confirmPasswordValue,
                    isError = state.isMatchPassword.not(),
                    errorMessage = stringResource(id = R.string.password_not_match),
                    label = stringResource(id = R.string.confirm_password_label),
                    onChanged = {
                        signUpViewModel.onConfirmPasswordChange(it)
                    })
                Spacer(modifier = modifier.height(10.dp))
                UserTypeSelector(isError = state.isUserSelected.not(),
                    errorMessage = stringResource(
                        id = R.string.user_type_not_selected
                    ),
                    value = signUpViewModel.userTypeValue,
                    onSelectOption = {
                        signUpViewModel.onTypeChange(it)
                    })

                ButtonComponent(
                    label = if (state.isLoading.not())
                        stringResource(id = R.string.button_sign_up_label)
                    else
                        stringResource(id = R.string.loading_text),
                    isEnable = state.formValid() or state.isLoading
                ) {
                    onSignUp()
                }
                Text(text = stringResource(id = R.string.sign_in_with_text))

                CircularButtonComponent {
                    onGoogleSignIn()
                }

            }
        }
    }
}
