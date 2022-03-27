package com.coroutine.switchnightmodepractice

import android.app.UiModeManager
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatDelegate
import com.coroutine.switchnightmodepractice.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private lateinit var uiModeManager:UiModeManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager

        binding.switchMode.setOnClickListener {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                try {
                    binding.editText.removeTextChangedListener(this)
                    val textValue: String = binding.editText.text.toString()
                    if (textValue != null || textValue != "") {
                        if (textValue.startsWith("."))
                            binding.editText.setText("0.")

                        if (textValue.startsWith("0") && !textValue.startsWith("0."))
                            binding.editText.setText("")
                        val str: String = binding.editText.text.toString().replace(",", "")
                        if (textValue != "")
                            binding.editText.setText(getDecimalFormattedText(str))
                        binding.editText.setSelection(binding.editText.text.toString().length)
                    }
                    "string without comma is : ${returnStringWithoutComma(binding.editText.text.toString())}"
                        .also { binding.showValue.text = it }
                    binding.editText.addTextChangedListener(this)
                    return
                } catch (e: Exception) {
                    e.printStackTrace()
                    "string without comma is : ${returnStringWithoutComma(binding.editText.toString())}"
                        .also { binding.showValue.text = it }
                    binding.editText.addTextChangedListener(this)
                }
            }

        })
    }
    private fun returnStringWithoutComma(inputText:String):String{
        return if (inputText.contains(","))
            inputText.replace(",","")
        else
            inputText
    }

    private fun getDecimalFormattedText(str: String): String {
        val lst = StringTokenizer(str,".")
        var string1 = str
        var string2 = ""
        if (lst.countTokens() > 1){
            string1 = lst.nextToken()
            string2 = lst.nextToken()
        }
        var string3 = ""
        var i = 0
        var j = -1 + string1.length
        if (string1[-1+string1.length]=='.'){
            j--
            string3 = "."
        }
        var k = j
        while (true) {
            if (k < 0) {
                if (string2.isNotEmpty()) string3 = "$string3.$string2"
                return string3
            }
            if (i == 3) {
                string3 = ",$string3"
                i = 0
            }
            string3 = string1[k].toString() + string3
            i++
            k--
        }
    }
}