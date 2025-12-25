package com.example.students_dynamic_list

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.students_dynamic_list.databinding.ActivityAddNewStudentBinding
import com.example.students_dynamic_list.databinding.ActivityStudentRecycleViewBinding
import com.example.students_dynamic_list.model.Student

class AddNewStudentActivity : AppCompatActivity() {
    var binding: ActivityAddNewStudentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddNewStudentBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.activityAddNewStudentCancelBtn?.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding?.activityAddNewStudentSubmitBtn?.setOnClickListener {
            val name = binding?.activityAddNewStudentName?.text.toString()
            val id = binding?.activityAddNewStudentId?.text.toString()
            val phone = binding?.activityAddNewStudentPhone?.text.toString()
            val address = binding?.activityAddNewStudentAddress?.text.toString()
            val isChecked = binding?.activityAddNewStudentIsCheckedBox?.isChecked

            val newStudent = Student(name, id, phone, address, isChecked ?: false)
            val resultIntent = Intent()

            resultIntent.putExtra("NEW_STUDENT_NAME", newStudent.name)
            resultIntent.putExtra("NEW_STUDENT_ID", newStudent.id)
            resultIntent.putExtra("NEW_STUDENT_PHONE", newStudent.phone)
            resultIntent.putExtra("NEW_STUDENT_ADDRESS", newStudent.address)
            resultIntent.putExtra("NEW_STUDENT_IS_CHECKED", newStudent.isChecked)

            setResult(RESULT_OK, resultIntent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}