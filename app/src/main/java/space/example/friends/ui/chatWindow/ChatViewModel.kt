package space.example.friends.ui.chatWindow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import space.example.friends.di.Resource
import space.example.friends.model.FirestoreManager
import space.example.friends.data.Message
import space.example.friends.data.User
import space.example.friends.data.UsersData

class ChatViewModel(private val db: FirestoreManager): ViewModel() {

    var userList: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    init {
        userList.value = Resource.loading()
        db.getUsers {
            userList.value = Resource.success(it)
        }
    }

    fun sendMessage(msg: String){
        var user = User()
        UsersData.users.forEach {
            if(it.id == UsersData.userId){
                user = it
            }
        }
        val message = Message(msg, "${user.firstName} ${user.secondName}")
        db.sendMessage(message)
    }

    var messages: MutableLiveData<Resource<List<Message>>> = MutableLiveData()
    fun getMessages(){
//        messages.value = Resource.loading()
        db.getData(
            {
                messages.value = Resource.success(it)
            },
            {
                messages.value = Resource.error(it)
            })
//        db.getUsers()
    }

    var message: MutableLiveData<Message> = MutableLiveData()
    fun changeListener(){
        db.changeListener {
            message.value = it
        }
    }
}