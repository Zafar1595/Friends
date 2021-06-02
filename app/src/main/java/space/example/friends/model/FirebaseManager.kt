package space.example.friends.model

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

class FirebaseManager(private val storage: FirebaseStorage) {


    fun uploading(
        uriList: List<Uri>,
        onSuccess: (complete: Boolean, count: Int) -> Unit,
        onFailure: (msg: String) -> Unit
    ) {
        val imageRef = storage.reference.child("Friends from Haji/")
        var l = 0
        uriList.forEach { uri ->
            val fileName = "image${System.currentTimeMillis()}"
            imageRef.child(fileName).putFile(uri)
                .addOnSuccessListener {
                    l++
                    onSuccess.invoke(true, l)
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
        }

    }

    fun getData(onSuccess: (List<String>) -> Unit, onFailure: (msg: String) -> Unit) {

        val storageRef = storage.reference.child("Friends from Haji")
        val imageList: MutableList<String> = mutableListOf()

        val listAllTask: Task<ListResult> = storageRef.listAll()

        listAllTask.addOnCompleteListener { result ->
            val items: List<StorageReference> = result.result!!.items

            items.forEach { item ->
                item.downloadUrl.addOnSuccessListener {
                    imageList.add(it.toString())
                }.addOnCompleteListener {
                    onSuccess.invoke(imageList)
                }.addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
            }
        }
    }
}