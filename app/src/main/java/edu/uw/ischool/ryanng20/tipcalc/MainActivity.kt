package edu.uw.ischool.ryanng20.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private var tipAmount = 1.15
    private lateinit var tipButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipButton = findViewById<Button>(R.id.button)
        val edittext = findViewById<EditText>(R.id.editText)

        edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(arg0: Editable) {
                tipButton.setEnabled(edittext.getText().toString().isNotEmpty());
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        tipButton.setOnClickListener {
            Log.i(TAG, "Tip Button pushed")
            val amount = edittext.text.toString().toDouble()
            val total = "%.2f".format(amount * tipAmount)
            val toast = Toast.makeText(this, "$${total}", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}