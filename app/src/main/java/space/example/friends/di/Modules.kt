package space.example.friends.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.example.friends.model.FirebaseStorageManager
import space.example.friends.model.FirestoreManager
import space.example.friends.model.LoginManager
import space.example.friends.setting.Setting
import space.example.friends.ui.login.LoginViewModel
import space.example.friends.ui.startWindow.StartViewModel
import space.example.friends.ui.chatWindow.ChatViewModel

val dataModule = module {
    single { FirebaseStorage.getInstance() }
    single { FirebaseStorageManager(get()) }
    single { FirebaseAuth.getInstance() }
    single { LoginManager(get()) }
    single { FirebaseFirestore.getInstance() }
    single { FirestoreManager(get()) }
    single { Setting(get()) }
}

val viewModules = module {
    viewModel { StartViewModel(get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}