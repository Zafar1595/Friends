package space.example.friends.ui.uploadWindow

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.example.friends.di.Resource
import space.example.friends.model.FirebaseManager

class UploadViewModel(private val db: FirebaseManager): ViewModel() {
    var images: MutableLiveData<Resource<Int>> = MutableLiveData()

    fun uploadImages(list: List<Uri>) {
        images.value = Resource.loading()
        db.uploading(list,
            { _, count ->
                images.value = Resource.success(count)
            },
            {
                images.value = Resource.error(it)
            }
        )
    }

}