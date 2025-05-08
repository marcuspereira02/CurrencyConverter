package com.marcuspereira.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.marcuspereira.currencyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = SpinnerAdapter(this, listOfCoins)
    private lateinit var binding: ActivityMainBinding

    private lateinit var selectedOriginCoin: CoinData
    private lateinit var selectedTargetCoin: CoinData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(/* view = */ findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.spinnerAmount.adapter = adapter
        binding.spinnerConvertedAmount.adapter = adapter

        binding.spinnerAmount.setSelection(0)
        binding.spinnerConvertedAmount.setSelection(6)

        binding.spinnerAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedOriginCoin = listOfCoins[position]
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerConvertedAmount.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedTargetCoin = listOfCoins[position]
                    convertCurrency()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.tieValueForConversion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertCurrency()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnClean.setOnClickListener {
            binding.tieValueConverted.setText("")
            binding.tieValueForConversion.setText("")
        }

        binding.imageButton.setOnClickListener{
            val temp = selectedOriginCoin
            selectedOriginCoin = selectedTargetCoin
            selectedTargetCoin = temp

            val originIndex = listOfCoins.indexOf(selectedOriginCoin)
            val targetIndex = listOfCoins.indexOf(selectedTargetCoin)

            binding.spinnerAmount.setSelection(originIndex)
            binding.spinnerConvertedAmount.setSelection(targetIndex)

            convertCurrency()

        }
    }

    private fun convertCurrency() {
        val inputText = binding.tieValueForConversion.text.toString().trim()
        val input = inputText.toDoubleOrNull()

        if ( input != null) {
            val result = input * (selectedTargetCoin.coin / selectedOriginCoin.coin)

            val formatNum = String.format("%.2f", result)
            binding.tieValueConverted.setText(formatNum)

            Log.d("Result", "Resultado: $result")
        } else {
            binding.tieValueConverted.setText("")
        }

    }

}
    private val listOfCoins = listOf(
        CoinData(
            name = "BRL",
            coin = 1.0,
            icon = R.drawable.flag_brazil,
            isSelected = false
        ),

        CoinData(
            name = "CAD",
            coin = 0.25,
            icon = R.drawable.flag_canada,
            isSelected = false
        ),

        CoinData(
            name = "CNY",
            coin = 1.276,
            icon = R.drawable.flag_china,
            isSelected = false
        ),
        CoinData(
            name = "EUR",
            coin = 0.1569,
            icon = R.drawable.flag_euro,
            isSelected = false
        ),
        CoinData(
            name = "GBP",
            coin = 0.1328,
            icon = R.drawable.flag_united_kingdom,
            isSelected = false
        ),
        CoinData(
            name = "INR",
            coin = 15.15,
            icon = R.drawable.flag_india,
            isSelected = false
        ),

        CoinData(
            name = "USD",
            coin = 0.18,
            icon = R.drawable.flag_united_states,
            isSelected = false
        )

    )
