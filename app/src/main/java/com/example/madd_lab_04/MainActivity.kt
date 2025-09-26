package com.example.madd_lab_04

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madd_lab_04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoList = mutableListOf<Todo>()
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView and adapter
        adapter = TodoAdapter(
            todoList,
            onEdit = { position -> editTodoDialog(position) },
            onDelete = { position -> deleteTodo(position) },
            onToggleDone = { position, isDone -> toggleDone(position, isDone) }
        )

        binding.rvTodos.layoutManager = LinearLayoutManager(this)
        binding.rvTodos.adapter = adapter

        // Add button click
        binding.btnAdd.setOnClickListener {
            val title = binding.etTodo.text.toString().trim()
            if (title.isNotEmpty()) {
                todoList.add(Todo(title))
                adapter.notifyItemInserted(todoList.size - 1)
                binding.etTodo.text?.clear()
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editTodoDialog(position: Int) {
        val editText = EditText(this).apply { setText(todoList[position].title) }

        AlertDialog.Builder(this)
            .setTitle("Edit Todo")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = editText.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    todoList[position].title = newTitle
                    adapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteTodo(position: Int) {
        todoList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun toggleDone(position: Int, isDone: Boolean) {
        todoList[position].isDone = isDone
        adapter.notifyItemChanged(position)
    }
}