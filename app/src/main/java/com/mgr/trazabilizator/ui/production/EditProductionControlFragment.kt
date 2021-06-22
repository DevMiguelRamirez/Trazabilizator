package com.mgr.trazabilizator.ui.production

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgr.trazabilizator.R
import java.lang.Exception

class EditProductionControlFragment : Fragment() {


    private lateinit var db: FirebaseFirestore
    private lateinit var ID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_editproduction, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        var clipboardManager =
            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipData = clipboardManager.primaryClip?.getItemAt(0)?.text as String
        ID = clipData
        if (ID.contains("ID:")) {
            db.collection("productionControl").document(ID.drop(3)).get()
                .addOnSuccessListener { document ->
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate)
                        .setText(document?.data?.get("Date").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextProduct)
                        .setText(document?.data?.get("Product").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextLotNumber)
                        .setText(document?.data?.get("Lot").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextAmount)
                        .setText(document?.data?.get("Amount").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextIngredients)
                        .setText(document?.data?.get("Ingredients").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextIncidents)
                        .setText(document?.data?.get("Incidents").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextCorrectives)
                        .setText(document?.data?.get("Correctives").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextObservations)
                        .setText(document?.data?.get("Observations").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable)
                        .setText(document?.data?.get("Responsable").toString())
                }
        }
        var date: String
        var product: String
        var lot: String
        var amount: String
        var ingredients: String
        var incidents: String
        var correctives: String
        var observations: String
        var responsable: String
        view.findViewById<Button>(R.id.buttonUpdate).setOnClickListener() {
            date =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate).text.toString()
            product =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextProduct).text.toString()
            lot =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextLotNumber).text.toString()
            amount =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextAmount).text.toString()
            ingredients =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextIngredients).text.toString()
            incidents =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextIncidents).text.toString()
            correctives =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextCorrectives).text.toString()
            observations =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextObservations).text.toString()
            responsable =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable).text.toString()
            if (checkEmpty(date, product, amount, responsable)) {
                try {
                    db.collection("productionControl").document(ID.drop(3)).update(
                        mapOf("Date" to date,
                            "Product" to product,
                            "Lot" to lot,
                            "Amount" to amount,
                            "Ingredients" to ingredients,
                            "Incidents" to incidents,
                            "Correctives" to correctives,
                            "Observations" to observations,
                            "Responsable" to responsable))
                    Toast.makeText(context, R.string.successfullyUpdate, Toast.LENGTH_LONG).show()
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
        date: String,
        product: String,
        amount: String,
        responsable: String,
    ): Boolean {
        return date.isNotEmpty() && product.isNotEmpty() && amount.isNotEmpty() && responsable.isNotEmpty()
    }
}