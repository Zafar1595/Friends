package space.example.friends.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import space.example.friends.databinding.ActivityLoginBinding
import space.example.friends.di.ResourceState
import space.example.friends.setting.Setting
import space.example.friends.ui.MainActivity

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by inject()
    private lateinit var setting: Setting


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setting = Setting(this)
        if(!setting.isAppFirstLaunched()){
            updateUi()
        }

        binding.btnLogin.setOnClickListener {
            if(binding.edUsername.text.isNotEmpty() && binding.edPassword.text.isNotEmpty()){
                loginViewModel.signIn(binding.edUsername.text.toString(), binding.edPassword.text.toString())
            }else{
                Toast.makeText(this, "Заполни все поля!", Toast.LENGTH_SHORT).show()
            }
        }
        observeState()
    }

    private fun observeState(){
        loginViewModel.user.observe(this, {
            when(it.status){
                ResourceState.SUCCESS -> {
                    updateUi()
                    loading(false)
                }
                ResourceState.LOADING -> {
                    loading(true)
                }
                ResourceState.ERROR -> {
                    loading(false)
                    showMessage(it.message.toString())
                }
            }
        })
    }

    private fun updateUi(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loading(load: Boolean){
        if(load){
            binding.pbLoading.visibility = View.VISIBLE
        }else{
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}