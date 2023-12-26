package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RiwayatActivity : AppCompatActivity() {
    private lateinit var rvRiwayat: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var riwayatViewModel: RiwayatViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var tvSaldo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        rvRiwayat = findViewById(R.id.rvRiwayat)
        tvSaldo = findViewById(R.id.tvSaldo)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        riwayatViewModel = ViewModelProvider(this)[RiwayatViewModel::class.java]



    }
    }
