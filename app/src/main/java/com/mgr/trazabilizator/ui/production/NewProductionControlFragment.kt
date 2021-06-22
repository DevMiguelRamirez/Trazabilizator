package com.mgr.trazabilizator.ui.production

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgr.trazabilizator.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewProductionControlFragment : Fragment() {

    private lateinit var nav: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_newproduction, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var clipboardManager =
            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        db = Firebase.firestore
        auth = Firebase.auth
        var date: String
        var product: String
        var lot: String
        var amount: String
        var ingredients: String
        var incidents: String
        var correctives: String
        var observations: String
        var responsable: String = ""
        db.collection("users")
            .whereEqualTo("Email", auth.currentUser.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    responsable = document.data.get("Name").toString()
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable)
                        .setText(responsable)
                }
            }
            .addOnFailureListener { _ ->
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable)
                    .setText("")
            }
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate)
            .setText(SimpleDateFormat("dd/MM/yyy").format(Date()))
        nav = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.buttonChooseDate).setOnClickListener() {
            nav.navigate(R.id.nav_dateFragment)
        }
        view.findViewById<Button>(R.id.buttonReset).setOnClickListener() {
            reset(view, responsable)
        }
        view.findViewById<Button>(R.id.buttonChooseResponsable).setOnClickListener() {
            view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable).setText("")
            nav.navigate(R.id.nav_responsableFragment)
        }
        view.findViewById<Button>(R.id.buttonChooseIngredients).setOnClickListener() {
            view.findViewById<TextInputEditText>(R.id.TextInputEditTextIngredients).setText("")
            var setClip: ClipData = ClipData.newPlainText("copy text", "")
            clipboardManager.setPrimaryClip(setClip)
            nav.navigate(R.id.nav_ingredientsFragment)
        }
        view.findViewById<Button>(R.id.buttonUpload).setOnClickListener() {
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
            val newProductionControl = hashMapOf(
                "Date" to date,
                "Product" to product,
                "Lot" to lot,
                "Amount" to amount,
                "Ingredients" to ingredients,
                "Incidents" to incidents,
                "Correctives" to correctives,
                "Observations" to observations,
                "Responsable" to responsable
            )
            if (checkEmpty(date, product, amount, responsable)) {
                try {
                    db.collection("productionControl")
                        .add(newProductionControl)
                    Toast.makeText(context, R.string.successfully, Toast.LENGTH_LONG).show()
                    view.findNavController().popBackStack()
                } catch (e: Exception) {
                    Toast.makeText(context, R.string.Error, Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(context, R.string.missing, Toast.LENGTH_LONG).show()
            }
            parentFragmentManager.setFragmentResultListener(
                "date",
                this,
                FragmentResultListener { _, result ->
                    var datetoday: String? = result.getString("date")
                    view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate)
                        .setText(datetoday)
                })
        }
    }

    fun checkEmpty(
        date: String,
        product: String,
        amount: String,
        responsable: String,
    ): Boolean {
        return date.isNotEmpty() && product.isNotEmpty() && amount.isNotEmpty() && responsable.isNotEmpty()
    }

    fun reset(view: View, responsable: String) {
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate)
            .setText(SimpleDateFormat("dd/MM/yyy").format(Date()))
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextProduct).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextLotNumber).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextAmount).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextIngredients).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextIncidents).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextCorrectives).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextObservations).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable)
            .setText(responsable)
    }
}