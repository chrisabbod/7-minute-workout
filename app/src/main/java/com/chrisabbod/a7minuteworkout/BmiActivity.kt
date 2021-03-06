package com.chrisabbod.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.chrisabbod.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        showMetricUnits()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rb_metric_units) {
                showMetricUnits()
            } else {
                showUsUnits()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun showMetricUnits() {
        currentVisibleView = METRIC_UNITS_VIEW

        binding?.apply {
            tilMetricUnitWeight.visibility = View.VISIBLE
            tilMetricUnitHeight.visibility = View.VISIBLE
            tilUsUnitWeight.visibility = View.GONE
            tilUsUnitHeightFeet.visibility = View.GONE
            tilUsUnitHeightInch.visibility = View.GONE

            etMetricUnitHeight.text!!.clear()
            etMetricUnitWeight.text!!.clear()

            llDisplayBmiReult.visibility = View.INVISIBLE
        }
    }

    private fun showUsUnits() {
        currentVisibleView = US_UNITS_VIEW

        binding?.apply {
            tilMetricUnitWeight.visibility = View.INVISIBLE
            tilMetricUnitHeight.visibility = View.INVISIBLE
            tilUsUnitWeight.visibility = View.VISIBLE
            tilUsUnitHeightFeet.visibility = View.VISIBLE
            tilUsUnitHeightInch.visibility = View.VISIBLE

            etUsUnitWeight.text!!.clear()
            etUsUnitHeightFeet.text!!.clear()
            etUsUnitHeightInch.text!!.clear()

            llDisplayBmiReult.visibility = View.INVISIBLE
        }
    }

    private fun displayBmiResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself! workout more!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! workout more!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class | (Severaly obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! workout more!"
        } else {
            bmiLabel = "Obese Class | (Very severaly obese)"
            bmiDescription = "You are in very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBmiReult?.visibility = View.VISIBLE

        binding?.tvBmiValue?.text = bmiValue
        binding?.tvBmiType?.text = bmiLabel
        binding?.tvBmiDescription?.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue: Float =
                    binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBmiResult(bmi)
            } else {
                Toast.makeText(
                    this@BmiActivity,
                    "Please enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            if (validateUsUnits()) {
                val usUnitHeightValueFeet: String =
                    binding?.etUsUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String =
                    binding?.etUsUnitHeightInch?.text.toString()
                val usUnitWeightValue: Float =
                    binding?.etUsUnitWeight?.text.toString().toFloat()

                val heightValue = usUnitHeightValueFeet.toFloat() * 12 + usUnitHeightValueInch.toFloat()
                val bmi = (703 * usUnitWeightValue) / (heightValue * heightValue)

                displayBmiResult(bmi)
            } else {
                Toast.makeText(
                    this@BmiActivity,
                    "Please enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }
}