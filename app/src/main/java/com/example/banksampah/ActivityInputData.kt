package com.example.banksampah

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import java.security.Provider
import java.text.SimpleDateFormat
import java.util.*

class ActivityInputData : AppCompatActivity() {
    private lateinit var dropdown: Spinner
    private lateinit var inputViewModel: InputDataViewModel
    private lateinit var etNama: EditText
    private lateinit var etBerat: EditText
    private lateinit var etTanggal : EditText
    private lateinit var etCatatan: EditText
    private lateinit var etHarga: EditText
    private lateinit var etAlamat : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data2)
        inputViewModel = ViewModelProvider(this).get(InputDataViewModel::class.java)
        dropdown = findViewById(R.id.spKategori)
        etNama = findViewById(R.id.inputNama)
        etHarga = findViewById(R.id.inputHarga)
        etBerat = findViewById(R.id.inputBerat)
        etAlamat = findViewById(R.id.inputAlamat)
        etTanggal = findViewById(R.id.inputTanggal)
        etCatatan = findViewById(R.id.inputTambahan)

        dropdown.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                inputViewModel.setKategori(resources.getStringArray(R.array.kategori_sampah)[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


        inputViewModel.kategori.observe(this){newValue ->
            inputViewModel.setCounter(1)
            etBerat.text = inputViewModel.counter.value.toString().toEditable()
            inputViewModel.setharga()
            etHarga.text = inputViewModel.harga.value.toString().toEditable()


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





        inputViewModel.counter.observe(this){newValue ->
            inputViewModel.setharga()
            etBerat.text = newValue.toString().toEditable()

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
