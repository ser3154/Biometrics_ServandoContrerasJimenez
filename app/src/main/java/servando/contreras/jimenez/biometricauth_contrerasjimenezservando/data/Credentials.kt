package servando.contreras.jimenez.biometricauth_contrerasjimenezservando.data

val USER= "Servando.contreras@mail.com"

val PASSWORD = "123456"

fun authenticate(user: String, password: String): Boolean{
    if (user.equals(USER,  true) && password.equals(PASSWORD, true)) {
        return true
    }
    return false

}