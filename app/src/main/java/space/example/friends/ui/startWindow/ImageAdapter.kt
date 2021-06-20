package space.example.friends.ui.startWindow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.example.friends.R
import space.example.friends.data.Image

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ListViewHolder>() {

    var imageUrlList: MutableList<Image> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun removeModel(image: Image) {
        var index = -1
        for(i in imageUrlList.indices){
            if(imageUrlList[i].name == image.name) {
                index = i
                break
            }
        }
        imageUrlList.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(0, imageUrlList.size)
    }

    private var onItemClickMore: (view: View, position: Int) -> Unit = { _, _ ->}
    fun setOnItemClickMoreListener(onItemClickMore: (view: View, position: Int) -> Unit) {
        this.onItemClickMore = onItemClickMore
    }


    private var onItemClick: (position: Int) -> Unit = {}
    fun setOnItemClickListener(onItemClick: (position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun populateModel(image: Image, position: Int){
            val imageView = itemView.findViewById<ImageView>(R.id.image_view)
            Glide.with(itemView).load(image.url).into(imageView)
            itemView.setOnClickListener {
                onItemClick.invoke(position)
            }

            itemView.findViewById<ImageButton>(R.id.img_btn_more).setOnClickListener {
                onItemClickMore.invoke(itemView.findViewById(R.id.img_btn_more), position)
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