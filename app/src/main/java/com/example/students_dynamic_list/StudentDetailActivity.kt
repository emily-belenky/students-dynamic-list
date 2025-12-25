package com.example.students_dynamic_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.students_dynamic_list.databinding.ActivityStudentDetailBinding
import com.example.students_dynamic_list.model.Model

class StudentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailBinding

    private val editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val position = data?.getIntExtra("STUDENT_POSITION", -1) ?: -1
            val newName = data?.getStringExtra("UPDATED_STUDENT_NAME") ?: ""
            val newId = data?.getStringExtra("UPDATED_STUDENT_ID") ?: ""
            val newPhone = data?.getStringExtra("UPDATED_STUDENT_PHONE") ?: ""
            val newAddress = data?.getStringExtra("UPDATED_STUDENT_ADDRESS") ?: ""
            val newIsChecked = data?.getBooleanExtra("UPDATED_STUDENT_IS_CHECKED", false) ?: false

            // Update the central Model so the list screen will be correct later
            if (position != -1) {
                val studentToUpdate = Model.shared.students[position]
                studentToUpdate.name = newName
                studentToUpdate.id = newId
                studentToUpdate.phone = newPhone
                studentToUpdate.address = newAddress
                studentToUpdate.isChecked = newIsChecked
            }

            // Update the TextViews on THIS screen to show the new data immediately
            binding.activityStudentDetailsName.text = newName
            binding.activityStudentDetailsId.text = newId
            binding.activityStudentDetailsPhone.text = newPhone
            binding.activityStudentDetailsAddress.text = newAddress
            binding.activityAddNewStudentIsCheckedBox.isChecked = newIsChecked
        }
        else if (result.resultCode == Activity.RESULT_FIRST_USER) {
            val data: Intent? = result.data
            val position = data?.getIntExtra("STUDENT_POSITION", -1) ?: -1

            // Remove the student from the Model
            if (position != -1) {
                Model.shared.students.removeAt(position)
            }

            // Finish the detail activity to go back to the main list
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Get the data passed from the student list
        val name = intent.getStringExtra("STUDENT_NAME")
        val id = intent.getStringExtra("STUDENT_ID")
        val phone = intent.getStringExtra("STUDENT_PHONE")
        val address = intent.getStringExtra("STUDENT_ADDRESS")
        val isChecked = intent.getBooleanExtra("STUDENT_IS_CHECKED", false)
        // VERY IMPORTANT: Get the student's position in the list
        val position = intent.getIntExtra("STUDENT_POSITION", -1)

        // 2. Display the initial data
        binding.activityStudentDetailsName.text = name
        binding.activityStudentDetailsId.text = id
        binding.activityStudentDetailsPhone.text = phone
        binding.activityStudentDetailsAddress.text = address
        binding.activityAddNewStudentIsCheckedBox.isChecked = isChecked

        // 3. Back button logic: just close this screen
        binding.activityStudentDetailsBackBtn.setOnClickListener {
            finish()
        }

        // 4. Edit button logic: launch the edit screen for a result
        binding.activityStudentDetailsEditBtn.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java).apply {
                // Pass the current data to the edit screen
                putExtra("STUDENT_NAME", name)
                putExtra("STUDENT_ID", id)
                putExtra("STUDENT_PHONE", phone)
                putExtra("STUDENT_ADDRESS", address)
                putExtra("STUDENT_IS_CHECKED", isChecked)
                // Pass the position so the EditActivity knows which student it is
                putExtra("STUDENT_POSITION", position)
            }
            // Use the launcher to start the activity
            editLauncher.launch(intent)
        }
    }
}
