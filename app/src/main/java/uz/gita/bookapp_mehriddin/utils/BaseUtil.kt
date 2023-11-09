package uz.gita.bookapp_mehriddin.utils

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun myLog(message:String, tag:String = "YYY"){
    Log.d(tag, message)
}

fun Fragment.showToast(ms:String){
    Toast.makeText(requireContext(), ms, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(ms:String){
    Toast.makeText(this, ms, Toast.LENGTH_SHORT).show()
}