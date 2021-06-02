package space.example.friends.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import space.example.friends.di.Resource
import space.example.friends.model.LoginManager

class LoginViewModel(private val auth: LoginManager): ViewModel() {
    val user : MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()

    fun signIn(login: String, password: String){
        user.value = Resource.loading()
        auth.signIn(login, password,
            {
                user.value = Resource.success(it)
            },
            {
                user.value = Resource.error(it)
            })

    }
}