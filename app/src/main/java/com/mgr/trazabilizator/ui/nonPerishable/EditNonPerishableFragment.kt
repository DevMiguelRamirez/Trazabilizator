package com.mgr.trazabilizator.ui.nonPerishable

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

class EditNonPerishableFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var ID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_editnonperishable, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var clipboardManager =
            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipData = clipboardManager.primaryClip?.getItemAt(0)?.text as String
        ID = clipData
        db = Firebase.firestore
        if (ID.contains("ID:")) {
            db.collection("nonPerishable").document(ID.drop(3)).get()
                .addOnSuccessListener { document ->
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate)
                        .setText(document?.data?.get("Date").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextProduct)
                        .setText(document?.data?.get("Product").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextProvider)
                        .setText(document?.data?.get("Provider").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextDelivery)
                        .setText(document?.data?.get("Delivery").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextLotNumber)
                        .setText(document?.data?.get("Lot").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextAmount)
                        .setText(document?.data?.get("Amount").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextCondition)
                        .setText(document?.data?.get("Condition").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextPreferentialDate)
                        .setText(document?.data?.get("PreferentialDate").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditEndDate)
                        .setText(document?.data?.get("EndDate").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEdiStartDate)
                        .setText(document?.data?.get("StartDate").toString())
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable)
                        .setText(document?.data?.get("Responsable").toString())
                }
        }
        var date: String
        var product: String
        var provider: String
        var delivery: String
        var lot: String
        var amount: String
        var condition: String
        var preferentialDate: String
        var startDate: String
        var endDate: String
        var responsable: String = ""
        view.findViewById<Button>(R.id.buttonUpdate).setOnClickListener() {
            date =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate).text.toString()
            product =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextProduct).text.toString()
            provider =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextProvider).text.toString()
            delivery =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextDelivery).text.toString()
            lot =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextLotNumber).text.toString()
            amount =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextAmount).text.toString()
            condition =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextCondition).text.toString()
            preferentialDate =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextPreferentialDate).text.toString()
            startDate =
                view.findViewById<TextInputEditText>(R.id.TextInputEdiStartDate).text.toString()
            endDate =
                view.findViewById<TextInputEditText>(R.id.TextInputEditEndDate).text.toString()
            responsable =
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable).text.toString()
            if (checkEmpty(date, product, amount, responsable, condition)) {
                try {
                    db.collection("nonPerishable").document(ID.drop(3)).update(
                        mapOf(
                            "Date" to date,
                            "Product" to product,
                            "Provider" to provider,
                            "Delivery" to delivery,
                            "Lot" to lot,
                            "Amount" to amount,
                            "Condition" to condition,
                            "PreferentialDate" to preferentialDate,
                            "StartDate" to startDate,
                            "EndDate" to endDate,
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
        condition: String,
        responsable: String,
    ): Boolean {
        return date.isNotEmpty() && product.isNotEmpty() && amount.isNotEmpty() && condition.isNotEmpty() && responsable.isNotEmpty()
    }
}