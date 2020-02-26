package com.gustavo.stela_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {
    private var socket = IO.socket("http:localhost:8000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat log"

        setupDummyData()

        button_send.setOnClickListener {
            performSendMessage()
        }
    }

    private fun performSendMessage() {
        socket.let {
            it!!.connect()
                .on(Socket.EVENT_CONNECT) {
                    Toast.makeText(this, "cONNECTED", Toast.LENGTH_LONG).show()
                    Log.d("chatlog", "connected")
                }
        }
    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatFromItem("FROM MESSSSAGEEESSS"))
        adapter.add(ChatToItem("TO MESSAGE\ndsafsadsa"))

        recycler_view_chat_log.adapter = adapter
    }
}

class ChatFromItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_from_text.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_to_text.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}