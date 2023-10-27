package edu.uw.ischool.shiina12.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var inputPrice: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var submitButton: Button
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputPrice = findViewById(R.id.input_price)
        seekBar = findViewById(R.id.seekBar)
        submitButton = findViewById(R.id.submit_tip_button)
        result = findViewById(R.id.tip_result)

        inputPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateButton()
            }
        })

        inputPrice.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                roundInput()
                true
            } else {
                false
            }
        }

        submitButton.setOnClickListener {
            updateResult()
        }
    }

    private fun validateButton() {
        val inputText = inputPrice.text.toString()
        submitButton.isEnabled = inputText.isNotEmpty()
    }

    private fun roundInput() {
        val inputText = inputPrice.text.toString()
        if (inputText.isNotEmpty()) {
            val inputNumber = inputText.toDouble()
            val decimalFormat = DecimalFormat("$#.##")
            val roundedNumber = decimalFormat.format(inputNumber)
            inputPrice.setText(roundedNumber)
            inputPrice.setSelection(roundedNumber.length)
        }
    }

    private fun updateResult() {
        val tipValues = arrayOf(0.1, 0.15, 0.18, 0.2)
        val prog = seekBar.progress
        val tipValue = tipValues[prog]
        val inputText = inputPrice.text.toString().replace("$", "")

        val inputAmount = inputText.toDouble()
        val tipAmount = inputAmount * tipValue
        val decimalFormat = DecimalFormat("$#.##")
        val res = decimalFormat.format(tipAmount)
        result.text = "Tip Amount: $res"
    }


}

