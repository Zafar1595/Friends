package space.example.friends.ui.startWindow

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import space.example.friends.R
import space.example.friends.databinding.FragmentStartBinding
import space.example.friends.di.ResourceState
import space.example.friends.ui.viewingWindow.ViewingActivity

class StartFragment : Fragment(R.layout.fragment_start) {
    private var adapter = ImageAdapter()
    private lateinit var binding: FragmentStartBinding
    private val startViewModel: StartViewModel by inject()

    private var urlList: List<String> = listOf()
    private var list: ArrayList<String> = arrayListOf()
    private var uriList: MutableList<Uri> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)

        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener { index ->
            val intent = Intent(requireContext(), ViewingActivity::class.java)
            intent.putExtra("list", list)
            intent.putExtra("index", index)
            startActivity(intent)
        }

        observeListener()

        binding.floatingActionBtn.setOnClickListener {
            startFileChose()
        }

    }

    private fun startFileChose() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            if (data.clipData != null) {
                val countClipData: Int = data.clipData!!.itemCount
                var currentItemSelect = 0
                while (currentItemSelect < countClipData) {
                    uriList.add(data.clipData!!.getItemAt(currentItemSelect).uri)
                    currentItemSelect++
                }

                showMessage("You $currentItemSelect file selected")
                sendData()
            } else {
                val uri = data.data
                uri?.let {
                    uriList.add(it)
                    sendData()
                }
            }
        }

    }

    private fun sendData() {
        startViewModel.uploadImages(uriList)
        showProgressBar(true)
        observeListenerResult()
        uriList.clear()
    }

    private fun observeListenerResult() {
        startViewModel.imagesSend.observe(requireActivity(), {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    showMessage("Complete")
                    showProgressBar(false)
                    startViewModel.getImages()
                }
                ResourceState.ERROR -> {
                    showProgressBar(false)
                    showMessage("Error")
                }
            }
        })
    }

    private fun observeListener() {
        startViewModel.getImages()
        startViewModel.images.observe(requireActivity(), {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    urlList = it.data!!
                    setData()
                    showProgressBar(false)
                }
                ResourceState.ERROR -> {
                    showMessage(it.message!!)
                }
                ResourceState.LOADING -> {
                    showProgressBar(true)
                }
            }
        }
        )
    }

    private fun setData() {
        adapter.imageUrlList = urlList
        list = urlList as ArrayList<String>

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