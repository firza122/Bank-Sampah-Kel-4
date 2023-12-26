package com.example.banksampah

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.security.Provider
import java.text.SimpleDateFormat
import java.util.*

class ActivityInputData : AppCompatActivity() {
    private lateinit var dropdown: Spinner
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var inputViewModel: InputDataViewModel
    private lateinit var etNama: EditText
    private lateinit var tvBerat: TextView
    private lateinit var etTanggal : EditText
    private lateinit var etCatatan: EditText
    private lateinit var etHarga: EditText
    private lateinit var etAlamat : EditText
    private lateinit var etbuttontambah : Button
    private lateinit var etbuttonkurang : Button
    private lateinit var btncheckout : MaterialButton
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data2)
        inputViewModel = ViewModelProvider(this).get(InputDataViewModel::class.java)
        firebaseFirestore = FirebaseFirestore.getInstance()
        dropdown = findViewById(R.id.spKategori)
        etNama = findViewById(R.id.inputNama)
        etHarga = findViewById(R.id.inputHarga)
        firebaseAuth = FirebaseAuth.getInstance()
        tvBerat = findViewById(R.id.BeratSampah)
        etAlamat = findViewById(R.id.inputAlamat)
        etTanggal = findViewById(R.id.inputTanggal)
        etCatatan = findViewById(R.id.inputTambahan)
        btncheckout = findViewById(R.id.btnCheckout)
        etbuttontambah = findViewById(R.id.buttontambah)
        etbuttonkurang = findViewById(R.id.buttonkurang)

        etbuttontambah.setOnClickListener {
            inputViewModel.penjumlahanbtnberat()
        }
        etbuttonkurang.setOnClickListener {
            inputViewModel.penguranganbtnberat(this)
        }


        dropdown.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                inputViewModel.setKategori(resources.getStringArray(R.array.kategori_sampah)[p2])
                inputViewModel.setUlangCounter()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        tvBerat.text = inputViewModel.counter.value.toString()

        inputViewModel.kategori.observe(this) { newValue ->
            inputViewModel.setharga()
            inputViewModel.updateHarga()
            etHarga.text = "Rp.${inputViewModel.harga.value.toString()}".toEditable()
            tvBerat.text = inputViewModel.counter.value.toString().toEditable()
        }
        inputViewModel.counter.observe(this) { newValue ->
            inputViewModel.updateHarga()
            tvBerat.text = newValue.toString().toEditable()
            etHarga.text = "Rp.${inputViewModel.harga.value.toString()}".toEditable()

        }
        etTanggal.setOnClickListener { view: View? ->
            val tanggalJemput = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    tanggalJemput[Calendar.YEAR] = year
                    tanggalJemput[Calendar.MONTH] = monthOfYear
                    tanggalJemput[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val strFormatDefault = "d MMMM yyyy"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    etTanggal.setText(simpleDateFormat.format(tanggalJemput.time))
                }
            DatePickerDialog(
                this@ActivityInputData, date,
                tanggalJemput[Calendar.YEAR],
                tanggalJemput[Calendar.MONTH],
                tanggalJemput[Calendar.DAY_OF_MONTH]
            ).show()
        }



        btncheckout.setOnClickListener {
            if (etNama.text.isEmpty() || etTanggal.text.isEmpty() || etAlamat.text.isEmpty() || (etCatatan.text.isEmpty()) || (tvBerat.text.isEmpty()) || (etHarga.text.isEmpty())) {
                Toast.makeText(this, "Data tidak boleh ada yang kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val aNama = etNama.text.toString()
                val cCatatan = etCatatan.text.toString()
                val dBerat = tvBerat.text.toString()
                val eHarga = inputViewModel.harga.value
                val fTanggal = etTanggal.text.toString()
                val gAlamat = etAlamat.text.toString()
                inputFirebase(aNama, cCatatan, dBerat, eHarga!!, fTanggal, gAlamat)
            }
        }
    }
    fun inputFirebase (aNama:String, cCatatan: String, dBerat:String, eHarga:Int, fTanggal: String,gAlamat : String){
        val data = hashMapOf(
            "aNama" to aNama,
            "bKategori" to inputViewModel.kategori.value,
            "cCatatan" to cCatatan,
            "dBerat" to dBerat,
            "eHarga" to eHarga,
            "fTanggal" to fTanggal,
            "gAlamat" to gAlamat,
            "status" to "masih dalam proses",
            "email" to firebaseAuth.currentUser!!.email
        )
        firebaseFirestore.collection("dataJemputSampah").add(data).addOnSuccessListener {
            Toast.makeText(this,"Pesanan sedang diproses,cek di menu riwayat!",Toast.LENGTH_SHORT).show()
            val toHome = Intent(this,HomeActivity::class.java)
            startActivity(toHome)
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}
//        etBerat.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun afterTextChanged(editable: Editable) {
//                etBerat.removeTextChangedListener(this)
//                if (editable.length > 0) {
//                    inputViewModel.setCounter(editable.toString().toInt())
//                    inputViewModel.setharga()
//                } else {
//                    inputViewModel.setCounter(0)
//                    inputViewModel.setharga()
//                }
//                etBerat.addTextChangedListener(this)
//            }
//        })
