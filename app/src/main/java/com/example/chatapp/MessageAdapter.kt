package com.example.chatapp

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

private val Message.message: CharSequence?
    get() {
        TODO("Not yet implemented")
    }
private val Message.senderId: String?
    get() {
        TODO("Not yet implemented")
    }//******************** from class message********

class MessageAdapter(private val context: Context, private val messageList: ArrayList<com.example.chatapp.Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SEND = 2   // maked private

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1){
            //inflate Receive
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)

        }else{
            //inflate send
            val view:View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }

    }

    override fun getItemCount(): Int {

        return messageList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if (holder.javaClass == SendViewHolder::class.java) {

            //do stuff for send view holder
            val currentMessage = messageList[position]

            val viewHolder = holder as SendViewHolder
            holder.sendMessage.text = currentMessage.message
                /*currentMessage.copyFrom(currentMessage).toString()*/ ///*************

        } else {

            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SEND
        } else {
            return ITEM_RECEIVE
        }
    }

    class SendViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        val sendMessage = itemView.findViewById<TextView>(R.id.textSendMessage)

    }

    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val receiveMessage = itemView.findViewById<TextView>(R.id.textReceiveMessage)

    }

}