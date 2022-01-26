package com.example.onlineshop.utils.ktx

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.onlineshop.R
import es.dmoral.toasty.Toasty


fun Activity.errorToast(message: String) {
    val toast = Toasty.custom(
        this, message,
        R.drawable.ic_error,
        R.color.red, Toast.LENGTH_SHORT, true, true
    )
    toast.setGravity(Gravity.TOP, 0, 80)
    toast.show()
}

fun Activity.successToast(message: String) {
    val toast = Toasty.custom(
        this, message,
        R.drawable.ic_check,
        R.color.green, Toast.LENGTH_SHORT, true, true
    )
    toast.setGravity(Gravity.TOP, 0, 80)
    toast.show()
}

fun Fragment.errorToast(message: String) {
    val toast = Toasty.custom(
        requireContext(), message,
        R.drawable.ic_error,
        R.color.red, Toast.LENGTH_SHORT, true, true
    )
    toast.setGravity(Gravity.TOP, 0, 80)
    toast.show()
}

fun Fragment.successToast(message: String) {
    val toast = Toasty.custom(
        requireContext(), message,
        R.drawable.ic_check,
        R.color.green, Toast.LENGTH_SHORT, true, true
    )
    toast.setGravity(Gravity.TOP, 0, 80)
    toast.show()
}