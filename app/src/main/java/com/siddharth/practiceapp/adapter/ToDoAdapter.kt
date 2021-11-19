package com.siddharth.practiceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.practiceapp.data.entities.ToDo
import com.siddharth.practiceapp.databinding.TodoItemBinding
import com.siddharth.practiceapp.viewModels.ToDoViewModel

class ToDoAdapter(private val viewModel: ToDoViewModel) : RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ToDo>(){
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this,differCallback)

    inner class MyViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTodoItem = differ.currentList[position]

        holder.binding.apply {
            todoItemTitle.text = currentTodoItem.title
            todoItemDesc.text = currentTodoItem.description
            itemCheckbox.isChecked = currentTodoItem.isChecked

            itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    currentTodoItem.setSelected(true)
                }else{
                    currentTodoItem.setSelected(false)
                }
            }
            todoItemLayout.setOnClickListener {
                val action =
                holder.itemView.findNavController().navigate(action)
            }

        }
    }

    override fun getItemCount() = differ.currentList.size

    fun removeSelectedItem(){
        for(todoItem in differ.currentList){
            if(todoItem.isSelected()){
                viewModel.deleteTodo(todoItem)
            }
        }
    }
}