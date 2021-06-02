package space.example.friends.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import space.example.friends.setting.Setting

class LoginManager(private val auth: FirebaseAuth) {

    fun signIn(
        login: String, password: String,
        onSuccess: (user: FirebaseUser?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        Log.e("test", "signIn")
        auth.signInWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                Log.e("test", "signIn success")
                onSuccess.invoke(it.user)
            }
            .addOnFailureListener {
                Log.e("test", "signIn failure")
                onFailure.invoke(it.localizedMessage)
            }
    }

}