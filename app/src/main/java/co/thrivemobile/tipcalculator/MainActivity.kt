package co.thrivemobile.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.thrivemobile.tipcalculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    companion object {
        private val tips = listOf(
            .10, // 10%
            .15, // 15%
            .20, // 20%
            .25 // 25%
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tips.forEachIndexed { index, d ->
            val button = MaterialButton(this, null, R.attr.materialButtonOutlinedStyle).apply {
                val percent = (d * 100).toInt()
                val buttonText = "$percent%"
                id = index
                text = buttonText
            }
            binding.tipPercentage.addView(button)
        }

        binding.submit.setOnClickListener {
            val amount = binding.checkAmount
                .editText
                ?.text
                ?.toString()
                ?.toDouble()
                ?: 0.0
            val tipIndex = binding.tipPercentage.checkedButtonId
            if (tipIndex != View.NO_ID) {
                val calculatedAmount = calculateTip(amount, tipIndex)
                val tipAmountText = "$%.2f".format(calculatedAmount.first)
                val totalAmountText = "$%.2f".format(calculatedAmount.second)
                binding.tipAmount.text = tipAmountText
                binding.totalAmount.text = totalAmountText
            }
        }
    }

    private fun calculateTip(amount: Double, tipIndex: Int): Pair<Double, Double> {
        val tip = tips[tipIndex]
        val tipAmount = amount * tip
        val total = amount + tipAmount
        return tipAmount to total
    }
}