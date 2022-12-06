package com.example.farmmanagment.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmmanagment.R
import com.example.farmmanagment.models.EmployeeInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_screen.*


class MainScreen : AppCompatActivity() {
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    var employeeInfo: EmployeeInfo? = null
    private var sendDatabtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase?.getReference("EmployeeInfo");
        employeeInfo = EmployeeInfo()
        // adding on click listener for our button.
        // adding on click listener for our button.
        idBtnSendData?.setOnClickListener(object : View.OnClickListener{
           override fun onClick(v: View?) {

                // getting text from our edittext fields.
                val name: String = idEdtEmployeeName.getText().toString()
                val phone: String = idEdtEmployeePhoneNumber.getText().toString()
                val address: String = idEdtEmployeeAddress.getText().toString()

                // below line is for checking whether the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(this@MainScreen, "Please add some data.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(name, phone, address)
                }
            }
        })
    }


    private fun addDatatoFirebase(name: String, phone: String, address: String) {
        // below 3 lines of code is used to set
        // data in our object class.
        employeeInfo?.employeeName= name
        employeeInfo?.employeeContactNumber=phone
        employeeInfo?.employeeAddress= address

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference!!.setValue(employeeInfo)

                // after adding this data we are showing toast message.
                Toast.makeText(this@MainScreen, "data added", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(this@MainScreen, "Fail to add data $error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}