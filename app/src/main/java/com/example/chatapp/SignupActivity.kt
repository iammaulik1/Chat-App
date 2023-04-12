package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.buttonSignup.setOnClickListener {


            val name = binding.editFirstName.text.toString()
            val email = binding.editUserEmail.text.toString()
            val password = binding.editUserPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){

            signUp(name ,email , password)
            }else{
                Toast.makeText(this,"Enter Required Fields",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(name : String ,email:String , password:String){

        ///logic to create user

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener (this){ task ->
                if (task.isSuccessful){
                    /////////code to jump to home
                    addUserToDatabase(name , email ,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignupActivity , LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this@SignupActivity , "Some Error Occured" , Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name:String,email: String,uid:String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }

}