package com.mgr.trazabilizator.ui.provider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgr.trazabilizator.R

class NewProviderFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_newprovider, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        var name: TextView =
            view.findViewById<TextInputEditText>(R.id.TextInputEditTextProviderName)
        view.findViewById<Button>(R.id.buttonUpload).setOnClickListener() {
            if (checkEmpty(name.text.toString())) {
                val provider = hashMapOf(
                    "Name" to name.text.toString(),
                )
                try {
                    db.collection("provider")
                        .add(provider)
                    Toast.makeText(context, R.string.successfully, Toast.LENGTH_LONG).show()
                    view.findNavController().popBackStack()
                } catch (e: Exception) {
                    Toast.makeText(context, R.string.Error, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, R.string.missing, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkEmpty(
        name: String,
    ): Boolean {
        return name.isNotEmpty()
    }
}