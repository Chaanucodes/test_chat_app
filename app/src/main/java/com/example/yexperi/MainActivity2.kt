package com.example.yexperi

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mAdapter: ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        mAdapter = ChatAdapter(viewModel.chatList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter

        //Observing changes that will be made to Firebase and sending the list to Adapter
        viewModel.isChanged.observe(this, Observer {
            if(it){
                if(recyclerView.visibility == View.INVISIBLE){
                    recyclerView.visibility = View.VISIBLE
                }
                mAdapter.updateList(viewModel.chatList)
                mAdapter.notifyDataSetChanged()
                viewModel.isChanged.value = false
                recyclerView.scrollToPosition(viewModel.chatList.size-1)
            }
        })
    }

    //OnClickListener for send button
    fun sendMessage(v : View){
        if(edit_message.text.isNotBlank()){
            viewModel.sendMessage(edit_message.text.removeSuffix("").toString())
        }
        edit_message.setText("")
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edit_message.windowToken, 0)
    }


}