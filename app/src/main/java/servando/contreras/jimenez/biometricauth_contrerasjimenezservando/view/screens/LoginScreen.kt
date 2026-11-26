package servando.contreras.jimenez.biometricauth_contrerasjimenezservando.view.screens


import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Button
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import servando.contreras.jimenez.biometricauth_contrerasjimenezservando.viewModel.LoginViewModel
import java.util.concurrent.Executor

@Composable
fun LoginScreen(innerPadding: PaddingValues, onSuccessfullLogin: ()-> Unit, loginViewModel: LoginViewModel = viewModel()){
    var user = remember { TextFieldState() }
    var password = remember { TextFieldState() }

    val context = LocalContext.current
    var isBiometricAvailable by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val biometricManager = BiometricManager.from(context)
        when(biometricManager.canAuthenticate(BIOMETRIC_STRONG)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                loginViewModel.authStatus= "Biometricos disponibles"
                isBiometricAvailable = true
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->{
                loginViewModel.authStatus = "El dispositivo no tiene sensores"
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE-> {
                loginViewModel.authStatus = "Biometria no disponible"
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->{
                loginViewModel.authStatus = "No hay datos biometricos disponibles"
            }
        }
    }

    val activity = context as FragmentActivity
    val executor: Executor = ContextCompat.getMainExecutor(context)

    val biometricPrompt = remember(activity) {
        if (activity != null) {
            BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    loginViewModel.authStatus = "Error: $errString"
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    loginViewModel.authStatus = "Autenticación correcta"
                    onSuccessfullLogin()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    loginViewModel.authStatus = "No se reconoció la huella/rostro"
                }
            })
        } else null
    }

    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Usa tu huella digital o reconocimiento facial")
            .setDescription("Coloca tu dedo o mira a la cámara")
            .setNegativeButtonText("Cancelar")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .build()
    }


    Column(Modifier.fillMaxSize().padding(innerPadding).padding(15.dp), verticalArrangement = Arrangement.SpaceBetween) {
        TextField(user, label = { Text("Correo electronico")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))

        SecureTextField(
            state = password,
            label = { Text("Contraseña") },
            textObfuscationMode = TextObfuscationMode.RevealLastTyped
        )

        Button(onSuccessfullLogin) {
            Text("Iniciar sesión")
        }

        Text(loginViewModel.authStatus)

        Spacer(Modifier.size(20.dp))

        Button(
            onClick = { biometricPrompt?.authenticate(promptInfo) },
            enabled = isBiometricAvailable
        ) {
            Text("Ingresar con huella")
        }
    }
}