package com.hyunprise.android.ui.admin.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import com.hyunprise.android.databinding.ActivityNumberPickerBinding

class NumberPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNumberPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val npMonths=binding.numberPickerMonthsNp
        val npWeeks=binding.numberPickerWeeksNp
        val npDays=binding.numberPickerDaysNp

        val range: Array<Int> = arrayOf(0,1,2,3,4,5,6,7,8,9)

        for (i in 0..9) {
            range[i] = i
        }

        npMonths.apply {
            this.wrapSelectorWheel = false
            this.maxValue = 9
            this.minValue = 0
            this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            this.setOnValueChangedListener { picker, oldVal, newVal ->
                Log.d("log.numberPicker", "$newVal")
            }
        }

        npWeeks.apply {
            this.wrapSelectorWheel = false
            this.maxValue = 9
            this.minValue = 0
            this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }

        npDays.apply {
            this.wrapSelectorWheel = false
            this.maxValue = 9
            this.minValue = 0
            this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }

    }
}