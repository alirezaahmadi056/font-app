package info.ahmadi.fontwriter.controller

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import info.ahmadi.fontwriter.databinding.DialogNetworkErrorBinding

interface Controller {

    fun startActivityFromController(context: Context? = null, intent: Intent){
        context?.startActivity(intent)
    }
    fun networkError(context: Context,customBinding:(binding:DialogNetworkErrorBinding)->Unit = {},retry:()->Unit,exit:()->Unit = {}){
        val binding = DialogNetworkErrorBinding.inflate(LayoutInflater.from(context))
        customBinding(binding)
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .create()
        binding.retry.setOnClickListener {
            retry()
        }
        binding.exit.setOnClickListener {
            dialog.dismiss()
            exit()
        }
        dialog.show()
    }

    fun finishFromController() {

    }
    fun changeFragment(fragment: Fragment){

    }
    fun changeTextFont(typeface: Typeface){

    }
}