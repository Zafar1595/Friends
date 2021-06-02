package space.example.friends.ui.uploadWindow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import space.example.friends.R
import space.example.friends.databinding.FragmentChatBinding

class ChatFragment : Fragment(R.layout.fragment_chat) {

    lateinit var binding: FragmentChatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)

    }

}