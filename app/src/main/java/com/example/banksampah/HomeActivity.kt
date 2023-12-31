package com.example.banksampah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var btnJemputSampah: CardView
    private lateinit var btnRiwayat: CardView
    private lateinit var btnCatatan: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        btnCatatan = findViewById(R.id.cvKategori)
        btnRiwayat = findViewById(R.id.cvHistory)
        btnJemputSampah = findViewById(R.id.cvInput)

        btnJemputSampah.setOnClickListener {
            val toInputData = Intent(this,ActivityInputData::class.java)
            startActivity(toInputData)
        }
        btnCatatan.setOnClickListener {
            val intent = Intent(this,ActivityCatatan::class.java)
            startActivity(intent)
        }
        btnRiwayat.setOnClickListener {
            val intent =   Intent(this,RiwayatActivity::class.java)
            startActivity(intent)
        }
    }


}