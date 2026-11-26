package servando.contreras.jimenez.biometricauth_contrerasjimenezservando

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import servando.contreras.jimenez.biometricauth_contrerasjimenezservando.ui.theme.BiometricAuth_ContrerasJimenezServandoTheme
import servando.contreras.jimenez.biometricauth_contrerasjimenezservando.view.screens.LoginScreen
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiometricAuth_ContrerasJimenezServandoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    LoginScreen(
                        innerPadding = innerPadding,
                        onSuccessfullLogin = {
                            Toast.makeText(this, "¡Bienvenido de vuelta!", Toast.LENGTH_SHORT).show()
                        }
                    )

                }
            }
        }
    }
}

