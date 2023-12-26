package com.example.banksampah

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputDataViewModel : ViewModel() {
    private var _kategori:MutableLiveData<String> = MutableLiveData("test")
    private var _harga: MutableLiveData<Int> = MutableLiveData(0)
    private var _counter: MutableLiveData<Int> = MutableLiveData(0)
    private var _hargaSatuan: MutableLiveData<Int> = MutableLiveData(0)
    val kategori: LiveData<String>
        get() = _kategori

    val harga: LiveData<Int>
        get() = _harga

    val counter: LiveData<Int>
        get() = _counter

    fun setKategori(kategoribaru: String){
        _kategori.value = kategoribaru
    }

    fun setharga(){
        if (kategori.value == "Botol Air Mineral"){
            _hargaSatuan.value = 1500
        }else if (kategori.value == "Botol Sabun / Sampo"){
            _hargaSatuan.value = 4000
        }else if (kategori.value == "Jerigen"){
            _hargaSatuan.value = 5000
        }else if (kategori.value == "Toples Makanan"){
            _hargaSatuan.value = 6000
        }else {
            _hargaSatuan.value = 1500
        }
    }
    fun penjumlahanbtnberat() {
        _counter.value = _counter.value!! + 1
        updateHarga()

    }
    fun penguranganbtnberat(context: Context) {
        if (_counter.value!! <= 1) {
            Toast.makeText(context, "Tidak bisa dikurangi lagi", Toast.LENGTH_SHORT).show()
        }else {
            _counter.value = -1
            updateHarga()
        }
    }

    fun setUlangCounter(){
        _counter.value = 1
    }
    fun updateHarga(){
        _harga.value = _hargaSatuan.value!!* _counter.value!!
    }
}