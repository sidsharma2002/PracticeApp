package com.siddharth.practiceapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.Todo

class TodoRvAdapter : RecyclerView.Adapter<TodoRvAdapter.TodoViewHolder>() {

    var dataList: MutableList<Todo> = mutableListOf()

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHeading: TextView = itemView.findViewById(R.id.tv_todo_heading)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_todo_desc)

        fun setData(holder: TodoViewHolder, data: Todo) {
            holder.tvHeading.text = data.heading
            holder.tvDesc.text = data.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        Log.d("TodoAdapter", "onBind fired")
        holder.setData(holder, dataList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}