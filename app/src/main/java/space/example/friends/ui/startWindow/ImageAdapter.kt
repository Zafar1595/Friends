package space.example.friends.ui.startWindow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.example.friends.R

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ListViewHolder>() {

    var imageUrlList: List<String> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }



    private var onItemClick: (position: Int) -> Unit = {}
    fun setOnItemClickListener(onItemClick: (position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun populateModel(url: String, position: Int){
            val imageView = itemView.findViewById<ImageView>(R.id.image_view)
            Log.d("ssilka", url)
            Glide.with(itemView).load(url).into(imageView)
            itemView.setOnClickListener {
                onItemClick.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_image_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.populateModel(imageUrlList[position], position)
    }

    override fun getItemCount(): Int = imageUrlList.size
}