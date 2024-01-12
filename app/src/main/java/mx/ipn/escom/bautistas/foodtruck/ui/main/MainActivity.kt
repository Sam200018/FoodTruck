package mx.ipn.escom.bautistas.foodtruck.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.foodtruck.R
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.GoogleAuthUIClient
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.SignInResult
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.SignUpViewModel
import mx.ipn.escom.bautistas.foodtruck.ui.main.views.HomeScreen
import mx.ipn.escom.bautistas.foodtruck.ui.main.views.Routes
import mx.ipn.escom.bautistas.foodtruck.ui.main.views.SignInScreen
import mx.ipn.escom.bautistas.foodtruck.ui.main.views.SignUpScreen
import mx.ipn.escom.bautistas.foodtruck.ui.theme.FoodTruckTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUIClient: GoogleAuthUIClient

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodTruckTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel by viewModels()
                val state by authViewModel.authState.collectAsStateWithLifecycle()

                val startDestination = if (state.isSignInSuccessful) {
                    Routes.HomeScreen.route
                } else {
                    Routes.SignInScreen.route
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(Routes.SignInScreen.route) {
                        LaunchedEffect(key1 = Unit) {
                            if (googleAuthUIClient.getSignedInUser() != null) {
                                authViewModel.onSignInResult(
                                    SignInResult(
                                        googleAuthUIClient.getSignedInUser(),
                                        errorMessage = ""
                                    )
                                )
                            }
                        }


                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult =
                                            googleAuthUIClient.googleSignInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                        authViewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    R.string.login_successful_text,
                                    Toast.LENGTH_LONG,
                                ).show()
                            }
                        }

                        SignInScreen(state = state, onSignIn = {}, onGoogleSignIn = {
                            lifecycleScope.launch {
                                val signinIntentSender = googleAuthUIClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signinIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        }, navigateToSignUp = {
                            navController.navigate(Routes.SignupScreen.route)
                        })

                    }

                    composable(Routes.HomeScreen.route) {
                        HomeScreen() {
                            lifecycleScope.launch {
                                googleAuthUIClient.signOut()
                                Toast.makeText(
                                    applicationContext,
                                    R.string.logout_text,
                                    Toast.LENGTH_LONG
                                )
                                authViewModel.resetState()
                            }
                        }
                    }

                    composable(Routes.SignupScreen.route) {


                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult =
                                            googleAuthUIClient.googleSignInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                        authViewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    R.string.login_successful_text,
                                    Toast.LENGTH_LONG,
                                ).show()
                            }
                        }

                        val signUpViewModel: SignUpViewModel by viewModels()


                        SignUpScreen(
                            signUpViewModel = signUpViewModel,
                            onSignUp = {
                                signUpViewModel.createAccount {
                                    navController.popBackStack()
                                    signUpViewModel.clearFields()
                                }
                                signUpViewModel.resetState()
                            },
                            back = {
                                navController.popBackStack()
                                signUpViewModel.clearFields()
                                signUpViewModel.resetState()
                            }) {
                            lifecycleScope.launch {
                                val signinIntentSender = googleAuthUIClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signinIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
