package com.example.students_dynamic_list.addapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.students_dynamic_list.R
import com.example.students_dynamic_list.StudentDetailActivity
import com.example.students_dynamic_list.databinding.StudentRowLayoutBinding
import com.example.students_dynamic_list.model.Student

class StudentRowViewHolder(val binding: StudentRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    private var currentStudent: Student? = null

    init {
        itemView.setOnClickListener {
            currentStudent?.let { student ->
                val context = itemView.context
                val intent = Intent(context, StudentDetailActivity::class.java).apply {
                    putExtra("STUDENT_NAME", student.name)
                    putExtra("STUDENT_ID", student.id)
                    putExtra("STUDENT_PHONE", student.phone)
                    putExtra("STUDENT_ADDRESS", student.address)
                    putExtra("STUDENT_IS_CHECKED", student.isChecked)
                    putExtra("STUDENT_POSITION", adapterPosition)
                }
                context.startActivity(intent)
            }
        }

        binding.checkBox.setOnClickListener {
            currentStudent?.isChecked = binding.checkBox.isChecked
        }
    }

    fun bind(student: Student){
        this.currentStudent = student

        binding.studentRowLayoutName.text = "Name: ${student.name}"
        binding.studentRowLayoutId.text = "ID: ${student.id}"
        binding.checkBox.isChecked = student.isChecked
    }
}
