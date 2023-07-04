package com.hyunprise.android.ui.admin.coupon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.hyunprise.android.R
import com.hyunprise.android.databinding.FragmentAdminHomeBinding
import com.hyunprise.android.databinding.FragmentNumberPickerBinding

class NumberPickerFragment : Fragment() {


    var _binding: FragmentNumberPickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNumberPickerBinding.inflate(inflater, container, false)

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}