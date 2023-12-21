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

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnGambarLogin: MaterialButton
    private lateinit var tvPindahRegister: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        firebaseAuth = FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.emaillogin)
        etPassword = findViewById(R.id.passwordlogin)
        btnGambarLogin = findViewById(R.id.loginbtn)
        tvPindahRegister = findViewById(R.id.tvToRegister)

        btnGambarLogin.setOnClickListener {
            if (etEmail.text.isEmpty() || etPassword.text.isEmpty()){
                Toast.makeText(this, "Email dan Password Harus Diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (etPassword.text.length <= 6){
                Toast.makeText(this, "Password Kurang panjang", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                loginFirebase(email,password)
            }
        }

        tvPindahRegister.setOnClickListener {
            val toRegistasi = Intent(this,RegisterBaruActivity::class.java)
            startActivity(toRegistasi)
        }
    }

    fun loginFirebase(email: String,password: String){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
        }
    }
}