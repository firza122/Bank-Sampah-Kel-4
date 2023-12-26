package com.example.banksampah

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class RiwayatAdapter(val list: MutableList<RiwayatModel>, val konteks: Context, val riwayat:RiwayatViewModel): RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {
    class RiwayatViewHolder(baris: View): RecyclerView.ViewHolder(baris){
        val tvNama = baris.findViewById<TextView>(R.id.tvNama)
        val tvDate = baris.findViewById<TextView>(R.id.tvDate)
        val tvKategori = baris.findViewById<TextView>(R.id.tvKategori)
        val tvBerat = baris.findViewById<TextView>(R.id.tvBerat)
        val tvSaldo = baris.findViewById<TextView>(R.id.tvSaldo)
        val tvStatus = baris.findViewById<TextView>(R.id.tvStatus)
        val ivDelete = baris.findViewById<ImageView>(R.id.imageDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_riwayat,parent,false)
        return RiwayatViewHolder(layout)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val bind = list[position]


        holder.tvNama.text = bind.nama
        holder.tvDate.text = bind.tanggal
        holder.tvKategori.text = bind.kategori
        holder.tvBerat.text = "Berat: ${bind.berat} Kg"
        holder.tvSaldo.text = "Rp. ${bind.harga}"
        holder.ivDelete.setOnClickListener {
            val firestoreDelete = FirebaseFirestore.getInstance()
            val dialogBuilder = AlertDialog.Builder(konteks)
            dialogBuilder.setTitle("Hapus Riwayat")
            dialogBuilder.setMessage("Apakah Anda Ingin Menghapus Riwayat Ini?")
            dialogBuilder.setPositiveButton("Hapus"){dialog, which ->
                firestoreDelete.collection("dataJemputSampah").document(bind.id).delete().addOnSuccessListener {
                    Toast.makeText(konteks,"Berhasil Hapus Data", Toast.LENGTH_SHORT).show()
                    riwayat.updateHarga(position)
                    list.removeAt(position)
                    notifyDataSetChanged()
                }
            }
            dialogBuilder.setNegativeButton("Batal"){dialog,which ->
                dialog.cancel()
            }
            dialogBuilder.show()
        }
        if (bind.status == "Masih dalam proses"){
            holder.tvStatus.setTextColor(Color.GREEN)
            holder.tvStatus.text = bind.status
        }else{
            holder.tvStatus.setTextColor(Color.YELLOW)
            holder.tvStatus.text = bind.status
        }

    }



}