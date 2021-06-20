package space.example.friends.model

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import space.example.friends.data.Image

class FirebaseStorageManager(private val storage: FirebaseStorage) {

    companion object {
        const val PATH_NAME = "Friends from Haji/"
    }

    fun removeImage(
        image: Image,
        onSuccess: () -> Unit,
        onFailure: (msg: String) -> Unit
    ) {
        val desertRef = storage.reference.child(PATH_NAME).child(image.name)
        desertRef.delete()
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun uploading(
        uriList: List<Uri>,
        onSuccess: (complete: Boolean, count: Int) -> Unit,
        onFailure: (msg: String) -> Unit
    ) {
        val imageRef = storage.reference.child(PATH_NAME)
        var l = 0
        uriList.forEach { uri ->
            val fileName = "image${System.currentTimeMillis()}"
            imageRef.child(fileName).putFile(uri)
                .addOnSuccessListener {
                    l++
                    if(l == uriList.size) {
                        onSuccess.invoke(true, l)
                    }
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
        }

    }

    fun getData(onSuccess: (List<Image>) -> Unit, onFailure: (msg: String) -> Unit) {

        val storageRef = storage.reference.child("Friends from Haji")
        val imageList: MutableList<Image> = mutableListOf()

        val listAllTask: Task<ListResult> = storageRef.listAll()

        listAllTask.addOnCompleteListener { result ->
//            val items: List<StorageReference> = result.result!!.items

            result.result!!.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener {
                    imageList.add(Image(item.name, it.toString()))
                }.addOnCompleteListener {
                    onSuccess.invoke(imageList)
                }.addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
            }
        }
    }


}