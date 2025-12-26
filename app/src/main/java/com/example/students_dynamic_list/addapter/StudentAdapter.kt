package com.example.students_dynamic_list.addapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.students_dynamic_list.databinding.StudentRowLayoutBinding
import com.example.students_dynamic_list.model.Student

class StudentAdapter(
    private val students: MutableList<Student>
) : RecyclerView.Adapter<StudentRowViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(student: Student, position: Int)
    }

    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentRowViewHolder {
        val binding = StudentRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentRowViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StudentRowViewHolder,
        position: Int
    ) {
        val student = students[position]
        holder.bind(student)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(student, position)
        }

        holder.binding.checkBox.setOnClickListener {
            student.isChecked = holder.binding.checkBox.isChecked
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}