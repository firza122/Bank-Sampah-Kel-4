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
            _harga.value = 1500 * counter.value!!
        }else if (kategori.value == "Botol Sabun / Sampo"){
            _harga.value = 4000 * counter.value!!
        }else if (kategori.value == "Jerigen"){
            _harga.value = 5000 * counter.value!!
        }else if (kategori.value == "Toples Makanan"){
            _harga.value = 6000 * counter.value!!
        }else {
            _harga.value = 0 * counter.value!!
        }
    }
    fun penjumlahanbtnberat() {
        _counter.value =+ 1

    }
    fun penguranganbtnberat(context: Context) {
        if (_counter.value!! <= 1) {
            Toast.makeText(context, "Tidak bisa dikurangi lagi", Toast.LENGTH_SHORT).show()
        }else {
            _counter.value =-1
        }
    }

    fun setCounter(counter: Int){
        _counter.value = counter
    }
}