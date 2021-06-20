package space.example.friends.ui.chatWindow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import space.example.friends.R
import space.example.friends.databinding.FragmentChatBinding
import space.example.friends.di.ResourceState
import space.example.friends.data.Message

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by inject()
    private var adapter = ChatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)

        binding.recyclerView.adapter = adapter

        binding.imgBtnSend.setOnClickListener {
            sendMessage()
            binding.editTextSend.text.clear()
        }


        changeListener()
        usersList()

    }

    private fun sendMessage(){
        if(binding.editTextSend.text.isNotEmpty()) {
            chatViewModel.sendMessage(binding.editTextSend.text.toString())
        }
    }

    private fun observeListener() {
        chatViewModel.getMessages()
        chatViewModel.messages.observe(requireActivity(), {
            when (it.status) {
                ResourceState.ERROR -> {
                    showMessage("Error")
                    showProgressBar(false)
                }
                ResourceState.SUCCESS -> {
                    adapter.models = (it.data as MutableList<Message>?)!!
                    showProgressBar(false)
                }
                ResourceState.LOADING -> {
                    showProgressBar(true)
                }
            }
        })
    }

    private fun changeListener() {
        chatViewModel.changeListener()
        showProgressBar(true)
        chatViewModel.message.observe(requireActivity(), {
            showProgressBar(false)
            adapter.addModel(it)
            binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
        })
    }

    private fun usersList(){
        chatViewModel.userList.observe(requireActivity(), {
            when(it.status){
                ResourceState.LOADING -> {
                    showProgressBar(true)
                }
                ResourceState.SUCCESS -> {
                    showProgressBar(false)
                    binding.imgBtnSend.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showProgressBar(load: Boolean) {
        if (load) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}