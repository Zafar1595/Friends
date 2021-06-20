package space.example.friends.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import space.example.friends.data.Message
import space.example.friends.data.UsersData

class FirestoreManager(private val instance: FirebaseFirestore) {

    companion object{
        const val MESSAGE_COLLECTION = "messages"
    }

    fun sendMessage(message: Message){
        instance.collection(MESSAGE_COLLECTION).add(message)
    }

    fun getData(onSuccess: (message: List<Message>) -> Unit, onFailure: (msg: String) -> Unit) {
        val mList: MutableList<Message> = mutableListOf()
//        mList.clear()
        instance.collection(MESSAGE_COLLECTION).get()
            .addOnSuccessListener {
                it.documents.forEach { document ->
                    document.toObject(Message::class.java)?.let { message ->
                        mList.add(message)
                    }
                }
                onSuccess.invoke(mList)
            }
            .addOnFailureListener {
                onFailure.invoke(it.message.toString())
            }
    }


    fun changeListener(onProductAdded: (message: Message) -> Unit) {
        instance.collection(MESSAGE_COLLECTION)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    for (document in it.documentChanges) {
                        when (document.type) {
                            DocumentChange.Type.ADDED -> {
                                onProductAdded.invoke(document.document.toObject(Message::class.java))
                                Log.e("test", "ADDED")
                            }

                            DocumentChange.Type.MODIFIED -> {
                            }

                            DocumentChange.Type.REMOVED -> {
                            }
                        }
                    }
                }
            }
    }

    fun getUsers(onSuccess: (result: Boolean) -> Unit){
        val mList: MutableList<space.example.friends.data.User> = mutableListOf()
        instance.collection("users").get()
            .addOnSuccessListener {
                it.documents.forEach { document ->
                    document.toObject(space.example.friends.data.User::class.java)?.let { user ->
                        mList.add(user)
                    }
                }
                UsersData.users = mList
                onSuccess.invoke(true)
            }
            .addOnFailureListener {
            }
        UsersData.userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    }

}