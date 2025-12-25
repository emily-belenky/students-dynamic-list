package com.example.students_dynamic_list.addapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.students_dynamic_list.addapter.StudentRowViewHolder
import com.example.students_dynamic_list.databinding.StudentRowLayoutBinding
import com.example.students_dynamic_list.model.Student

class StudentAdapter (private val students: List<Student>) : RecyclerView.Adapter<StudentRowViewHolder>(){
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
        holder.bind(students[position])
    }

    override fun getItemCount(): Int {
        return students.size

    }

}