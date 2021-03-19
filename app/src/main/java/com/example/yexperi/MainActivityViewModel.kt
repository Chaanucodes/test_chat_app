package com.example.yexperi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivityViewModel : ViewModel() {

    var chatList = mutableListOf(UsersDTO())

    var isChanged = MutableLiveData<Boolean>()
    val ref = FirebaseFirestore.getInstance().collection("messages")

    init {
        loadChat()
    }

    //Function to initially load the chat
    private fun loadChat(){
        ref.
            orderBy("timeStamp", Query.Direction.ASCENDING)
            .addSnapshotListener { value, e -> //realtime updates to update chat whenever someone enters some data
            if (e != null) {
                Log.w("MAIN_V_MODEL", "Listen failed.", e)
                return@addSnapshotListener
            }
            var list = mutableListOf<UsersDTO>()

                //converting data from firebase to UsersDTO
            value?.let {querySnap->
                for (doc in value) {
                    UsersDTO(
                        message = doc.data["message"] as String?,
                        author = doc.data["author"] as String?,
                        timeStamp = doc.data["timeStamp"] as Long?,
                        userId = doc.data["userId"] as String?).apply {
                        list.add(this)
                    }
                }
                if(list.size == 0 || list[0].message.isNullOrBlank()){
                    return@addSnapshotListener
                }else{
                    chatList.clear()
                    chatList = list
                    isChanged.value = true
//                    list.clear()
                }
            }



        }
    }


    //function to send message to firebase
    fun sendMessage(suffix: String) {
        ref.document().set(
            UsersDTO(
                message = suffix,
                timeStamp = System.currentTimeMillis(),
                author = FirebaseAuth.getInstance().currentUser!!.displayName!!,
                userId = FirebaseAuth.getInstance().currentUser!!.uid
            )
        ).addOnSuccessListener {
            Log.i("MAIN_V_MODEL_SUCCESS", "SUCCESS!!!")
        }
    }
}