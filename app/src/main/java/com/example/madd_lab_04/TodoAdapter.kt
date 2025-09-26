package com.example.madd_lab_04

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madd_lab_04.databinding.ItemTodoBinding

class TodoAdapter(
    private val todos: MutableList<Todo>,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit,
    private val onToggleDone: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        // Avoid recycled listener issue:
        holder.binding.chkDone.setOnCheckedChangeListener(null)
        holder.binding.chkDone.isChecked = todo.isDone

        holder.binding.tvTitle.text = todo.title

        // checkbox
        holder.binding.chkDone.setOnCheckedChangeListener { _, isChecked ->
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) onToggleDone(adapterPos, isChecked)
        }

        holder.binding.btnEdit.setOnClickListener {
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) onEdit(adapterPos)
        }
        holder.binding.btnDelete.setOnClickListener {
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) onDelete(adapterPos)
        }
    }

    override fun getItemCount(): Int = todos.size
}