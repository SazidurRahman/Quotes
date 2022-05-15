package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding : ActivityMainBinding




    private lateinit var viewModel: MainViewModel
    private lateinit var quote : Quote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[MainViewModel::class.java]

        binding.lifecycleOwner = this
        viewModel.factsLiveData.observe(this) {
            quote = it
            setQuote()
        }

        binding.next.setOnClickListener {
            viewModel.updateLiveData(1)
        }

        binding.previous.setOnClickListener {
            viewModel.updateLiveData(-1)
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, quote.toString())
            startActivity(intent)
        }
    }

    private fun setQuote() {
        binding.quoteText.text = quote.text
        binding.author.text = quote.author
    }
}

