package com.example.students_dynamic_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.students_dynamic_list.addapter.StudentAdapter
import com.example.students_dynamic_list.databinding.ActivityStudentRecycleViewBinding
import com.example.students_dynamic_list.model.Model
import com.example.students_dynamic_list.model.Student

class StudentRecycleViewActivity : AppCompatActivity() {

    var binding: ActivityStudentRecycleViewBinding? = null
    var adapter: StudentAdapter? = null

    private val addNewStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            val name = data?.getStringExtra("NEW_STUDENT_NAME") ?: ""
            val id = data?.getStringExtra("NEW_STUDENT_ID") ?: ""
            val phone = data?.getStringExtra("NEW_STUDENT_PHONE") ?: ""
            val address = data?.getStringExtra("NEW_STUDENT_ADDRESS") ?: ""
            val isChecked = data?.getBooleanExtra("NEW_STUDENT_IS_CHECKED", false) ?: false

            val newStudent = Student(name, id, phone, address, isChecked)

            Model.shared.students.add(newStudent)

            adapter?.notifyItemInserted(Model.shared.students.size - 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStudentRecycleViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layout = LinearLayoutManager(this)
        binding?.studentsRecyclerView?.layoutManager = layout
        binding?.studentsRecyclerView?.setHasFixedSize(true)

        adapter = StudentAdapter(Model.shared.students)
        binding?.studentsRecyclerView?.adapter = adapter

        binding?.studentRecycleViewAddButton?.setOnClickListener {
            val intent = Intent(this, AddNewStudentActivity::class.java)
            addNewStudentLauncher.launch(intent)
        }

    }
    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }
}