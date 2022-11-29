package uz.gita.my_taxi_jamshid.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.gita.my_taxi_jamshid.presentation.MainActivity
import uz.gita.my_taxi_jamshid.presentation.screens.dialogs.ErrorDialog
import uz.gita.my_taxi_jamshid.presentation.screens.dialogs.MessageDialog

// Created by Jamshid Isoqov on 11/29/2022
fun Fragment.showError(error: String) {
    val dialog = ErrorDialog(requireContext(), error)
    dialog.show()
}

fun Fragment.showMessage(message: String) {
    val dialog = MessageDialog(requireContext(), message)
    dialog.show()
}

fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}