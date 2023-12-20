package com.example.banksampah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterBaruActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail:EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var tvLogin: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_baru)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        etUsername = findViewById(R.id.usernameReg)
        etEmail = findViewById(R.id.emailReg)
        etPassword = findViewById(R.id.passwordReg)
        btnRegister = findViewById(R.id.signup)
        tvLogin = findViewById(R.id.tvToLogin)

        btnRegister.setOnClickListener {
            if (etEmail.text.isEmpty() || etPassword.text.isEmpty() || etUsername.text.isEmpty()){
                Toast.makeText(this, "Field Harus Diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(etPassword.text.length <= 6){
                Toast.makeText(this, "Password Terlalu Pendek", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val username = etUsername.text.toString()
                Registrasi(email,username,password)
            }

        }
        tvLogin.setOnClickListener {
            val toLogin = Intent(this,LoginActivity::class.java)
            startActivity(toLogin)
        }

    }

    fun Registrasi(email: String,nama: String,password: String){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            val data = hashMapOf(
                "nama" to nama,
                "email" to email
            )
            firestore.collection("user").add(data).addOnSuccessListener {
                Toast.makeText(this, "registrasi berhasil", Toast.LENGTH_SHORT).show()
                val toHome = Intent(this,HomeActivity::class.java)
                startActivity(toHome)
            }
        }

    }
}