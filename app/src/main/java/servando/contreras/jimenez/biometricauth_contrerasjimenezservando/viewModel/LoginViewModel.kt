package servando.contreras.jimenez.biometricauth_contrerasjimenezservando.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import servando.contreras.jimenez.biometricauth_contrerasjimenezservando.data.authenticate

class LoginViewModel: ViewModel() {
    var authStatus by mutableStateOf("")

    fun login(user: String, password: String, toHome: ()->Unit){
        if (authenticate(user, password)){
            toHome()
        }
        else{
            authStatus = "Credenciales invalidad"
        }
    }
}