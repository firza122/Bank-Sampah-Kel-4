package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        riwayatViewModel.listRiwayatBaru.observe(this){newValue ->
            if (newValue.size == 0){
                rvRiwayat.layoutManager = LinearLayoutManager(this)
                rvRiwayat.adapter = RiwayatAdapter(newValue,this,riwayatViewModel)
            }else{
                rvRiwayat.layoutManager = LinearLayoutManager(this)
                rvRiwayat.adapter = RiwayatAdapter(newValue,this,riwayatViewModel)
                tvSaldo.text = "Rp. ${riwayatViewModel.getHarga()}"
            }
        }
        riwayatViewModel.harga.observe(this){newValue ->
            tvSaldo.text = newValue.toString()
        }

        GlobalScope.launch { getDataRiwayat() }
    }

    }
