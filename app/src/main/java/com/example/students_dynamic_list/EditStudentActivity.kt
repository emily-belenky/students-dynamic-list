package com.example.students_dynamic_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.students_dynamic_list.databinding.ActivityAddNewStudentBinding
import com.example.students_dynamic_list.databinding.ActivityEditStudentBinding

class EditStudentActivity : AppCompatActivity() {

    var binding: ActivityEditStudentBinding? = null
    var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val name = intent.getStringExtra("STUDENT_NAME")
        val id = intent.getStringExtra("STUDENT_ID")
        val phone = intent.getStringExtra("STUDENT_PHONE")
        val address = intent.getStringExtra("STUDENT_ADDRESS")
        val isChecked = intent.getBooleanExtra("STUDENT_IS_CHECKED", false)
        studentPosition = intent.getIntExtra("STUDENT_POSITION", -1)

        // --- Display the current data in the EditText fields ---
        binding?.activityEditStudentName?.setText(name)
        binding?.activityEditStudentId?.setText(id)
        binding?.activityEditStudentPhone?.setText(phone)
        binding?.activityEditStudentAddress?.setText(address)
        binding?.activityEditStudentIsCheckedBox?.isChecked = isChecked


        binding?.activityEditStudentCancelBtn?.setOnClickListener {
            finish()
        }

        binding?.activityEditStudentDeleteBtn?.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("STUDENT_POSITION", studentPosition)
            setResult(Activity.RESULT_FIRST_USER, resultIntent)
            finish()
        }

        binding?.activityEditStudentSubmitBtn?.setOnClickListener {
            // Read the (possibly updated) text from all the EditText fields
            val updatedName = binding?.activityEditStudentName?.text.toString()
            val updatedId = binding?.activityEditStudentId?.text.toString()
            val updatedPhone = binding?.activityEditStudentPhone?.text.toString()
            val updatedAddress = binding?.activityEditStudentAddress?.text.toString()
            val updatedIsChecked = binding?.activityEditStudentIsCheckedBox?.isChecked

            // Create a new Intent to hold the result
            val resultIntent = Intent()

            // Put the updated data into the intent
            resultIntent.putExtra("UPDATED_STUDENT_NAME", updatedName)
            resultIntent.putExtra("UPDATED_STUDENT_ID", updatedId)
            resultIntent.putExtra("UPDATED_STUDENT_PHONE", updatedPhone)
            resultIntent.putExtra("UPDATED_STUDENT_ADDRESS", updatedAddress)
            resultIntent.putExtra("UPDATED_STUDENT_IS_CHECKED", updatedIsChecked)
            resultIntent.putExtra("STUDENT_POSITION", studentPosition) // Very important!

            // Set the result to OK and send the data back
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close the edit screen
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}