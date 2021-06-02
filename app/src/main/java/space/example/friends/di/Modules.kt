package space.example.friends.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.example.friends.model.FirebaseManager
import space.example.friends.model.LoginManager
import space.example.friends.ui.login.LoginViewModel
import space.example.friends.ui.startWindow.StartViewModel
import space.example.friends.ui.uploadWindow.UploadViewModel

val dataModule = module {
    single { FirebaseStorage.getInstance() }
    single { FirebaseManager(get()) }
    single { FirebaseAuth.getInstance() }
    single { LoginManager(get()) }
}

val viewModules = module {
//    viewModel { LoginViewModel(get()) }
    viewModel { StartViewModel(get()) }
    viewModel { UploadViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}