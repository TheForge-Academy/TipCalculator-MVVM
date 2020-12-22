package co.thrivemobile.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import co.thrivemobile.tipcalculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainViewModel.tips.forEachIndexed { index, d ->
            val button = MaterialButton(this, null, R.attr.materialButtonOutlinedStyle).apply {
                val percent = (d * 100).toInt()
                val buttonText = "$percent%"
                id = index
                text = buttonText
            }
            binding.tipPercentage.addView(button)
        }

        val mainViewModel: MainViewModel by viewModels()

        binding.submit.setOnClickListener {
            val amount = binding.checkAmount
                .editText
                ?.text
                ?.toString()
                ?.toDouble()
                ?: 0.0
            val tipIndex = binding.tipPercentage.checkedButtonId
            if (tipIndex != View.NO_ID) {
                val calculatedAmount = mainViewModel.calculateTip(amount, tipIndex)
                binding.tipAmount.text = calculatedAmount.first
                binding.totalAmount.text = calculatedAmount.second
            }
        }
    }
}