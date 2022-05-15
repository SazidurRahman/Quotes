package com.example.myapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class MainViewModel(private val context: Context) : ViewModel() {

    private var value = 0

    private var quotes: Array<Quote>

    private var factsLiveDataObject: MutableLiveData<Quote> = MutableLiveData()

    init {
        quotes = loadData()
        factsLiveDataObject.value = quotes[value]
    }

    private fun loadData(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return  gson.fromJson(json, Array<Quote>::class.java)
    }

    val factsLiveData : LiveData<Quote>
        get() = factsLiveDataObject

    fun updateLiveData(count : Int) {
        value = (value + count + quotes.size) % quotes.size
        factsLiveDataObject.value =  quotes[value]
    }

}