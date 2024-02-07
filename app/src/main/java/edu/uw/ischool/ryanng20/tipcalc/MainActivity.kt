package edu.uw.ischool.ryanng20.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private var tipAmount = 1.15
    private lateinit var tipButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipButton = findViewById<Button>(R.id.button)
        val edittext = findViewById<EditText>(R.id.editText)
        val tipSpinner = findViewById<Spinner>(R.id.tipAmount)

        val tipPercent = listOf("10%", "15%", "18%", "20%")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipPercent)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tipSpinner.adapter = adapter

        edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable) {
                tipButton.isEnabled = edittext.text.toString().isNotEmpty()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val textString = s.toString()
                var changedText = textString
                if(textString.isNotEmpty()) {
                    if(textString.startsWith("$").not()) {
                        changedText = "$${textString}"
                    } else if(textString == "$") {
                        changedText = ""
                    } else {
                        val formatString = Regex("^\\\$?\\d*.?\\d{0,2}")
                        if (formatString.find(textString) != null) {
                            changedText = formatString.find(textString)!!.value
                        }
                    }
                }
                if(changedText != textString) {
                    edittext.setText(changedText)
                    edittext.setSelection(edittext.text.length)
                }
            }
        })

        tipSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,  view: android.view.View?, pos: Int, id: Long) {
                when(pos) {
                    0 -> tipAmount = 1.1
                    1 -> tipAmount = 1.15
                    2 -> tipAmount = 1.18
                    3 -> tipAmount = 1.2
                }
                Log.i(TAG, tipAmount.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        tipButton.setOnClickListener {
            Log.i(TAG, "Tip Button pushed")
            val money = edittext.text.toString().substring(1, edittext.text.length).toDoubleOrNull() ?: 0.0
            val total = "%.2f".format(money * tipAmount)
            val toast = Toast.makeText(this, "$${total}", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

}