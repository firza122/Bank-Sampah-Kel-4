package com.example.banksampah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RiwayatViewModel : ViewModel() {
    var _listRiwayat: MutableLiveData<MutableList<RiwayatModel>> = MutableLiveData(listRiwayat)
    val listRiwayatBaru: LiveData<MutableList<RiwayatModel>>
        get() = _listRiwayat

    var _harga: MutableLiveData<Int> = MutableLiveData(0)
    val harga : LiveData<Int>
        get() = _harga
    fun getHarga(): Int{
        for (i in _listRiwayat.value!!){
            _harga.value = _harga.value?.plus(i.harga)
        }
        return _harga.value!!
    }
    fun updateHarga(indeks:Int){
        _harga.value=_harga.value!! - _listRiwayat.value!![indeks].harga
    }
}