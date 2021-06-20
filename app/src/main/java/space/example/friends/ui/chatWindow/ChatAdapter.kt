package space.example.friends.ui.chatWindow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.example.friends.R
import space.example.friends.data.Message
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ListViewHolder>() {

    var models: MutableList<Message> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addModel(message: Message) {
        models.add(message)
        notifyItemInserted(models.size)
        notifyItemRangeChanged(0, models.size)
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun populateModel(message: Message){
            val textViewMessage: TextView = itemView.findViewById(R.id.text_view_chat)
            val textViewUsername: TextView = itemView.findViewById(R.id.text_view_char_username)
            val textViewTime: TextView = itemView.findViewById(R.id.text_view_chat_time)

            textViewMessage.text = message.messageText
            textViewUsername.text = message.author
            textViewTime.text = convertLongToTime(message.timeMessage)
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size
}