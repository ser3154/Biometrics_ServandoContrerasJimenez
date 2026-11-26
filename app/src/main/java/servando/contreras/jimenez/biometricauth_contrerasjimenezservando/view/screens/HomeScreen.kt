package servando.contreras.jimenez.biometricauth_contrerasjimenezservando.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(innerPadding: PaddingValues, onLogout: ()-> Unit){
    Column() {
        Text("Bienvenido!")

        Spacer(Modifier.size(20.dp))

        Button(onLogout) {
            Text("Cerrar Sesion")
        }
    }
}