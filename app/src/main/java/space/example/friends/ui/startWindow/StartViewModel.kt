package space.example.friends.ui.startWindow

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.example.friends.data.Image
import space.example.friends.di.Resource
import space.example.friends.model.FirebaseStorageManager

class StartViewModel(private val db: FirebaseStorageManager) : ViewModel() {

    val deleteResult: MutableLiveData<Resource<Image>> = MutableLiveData()

    fun removeImage(image: Image){
        deleteResult.value = Resource.loading()
        db.removeImage(image,
            {
                deleteResult.value = Resource.success(image)
            },
            {
                deleteResult.value = Resource.error(it)
            })
    }

    var images: MutableLiveData<Resource<List<Image>>> = MutableLiveData()
    fun getImages() {
        images.value = Resource.loading()
        db.getData(
            {
                images.value = Resource.success(it)
            },
            {
                images.value = Resource.error(it)
            }
        )
    }

    var imagesSend: MutableLiveData<Resource<Int>> = MutableLiveData()

    fun uploadImages(list: List<Uri>) {
        images.value = Resource.loading()
        db.uploading(list,
            { _, count ->
                imagesSend.value = Resource.success(count)
            },
            {
                imagesSend.value = Resource.error(it)
            }
        )
    }
}