package com.mgr.trazabilizator.ui.nonPerishable

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
import java.text.SimpleDateFormat
import java.util.*

class NewNonPerishableFragment : Fragment() {

    private lateinit var nav: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_newnonperishable, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        auth = Firebase.auth
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
        view.findViewById<Button>(R.id.buttonChoosePreferentialDate).setOnClickListener() {
            nav.navigate(R.id.nav_preferentialDateFragment)
        }
        view.findViewById<Button>(R.id.buttonChooseStartDate).setOnClickListener() {
            nav.navigate(R.id.nav_startDateFragment)
        }
        view.findViewById<Button>(R.id.buttonChooseEndDate).setOnClickListener() {
            nav.navigate(R.id.nav_endDateFragment)
        }
        view.findViewById<Button>(R.id.buttonReset).setOnClickListener() {
            reset(view, responsable)
        }
        view.findViewById<Button>(R.id.buttonChooseProvider).setOnClickListener() {
            view.findViewById<TextInputEditText>(R.id.TextInputEditTextProvider).setText("")
            nav.navigate(R.id.nav_providerFragment)
        }
        view.findViewById<Button>(R.id.buttonChooseResponsable).setOnClickListener() {
            view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable).setText("")
            nav.navigate(R.id.nav_responsableFragment)
        }
        view.findViewById<Button>(R.id.buttonUpload).setOnClickListener() {
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

            val newNonPerishable = hashMapOf(
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
                "Responsable" to responsable
            )
            if (checkEmpty(date, product, amount, responsable, condition)) {
                try {
                    db.collection("nonPerishable")
                        .add(newNonPerishable)
                    Toast.makeText(context, R.string.successfully, Toast.LENGTH_LONG).show()
                    view.findNavController().popBackStack()
                } catch (e: Exception) {
                    Toast.makeText(context, R.string.Error, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, R.string.missing, Toast.LENGTH_LONG).show()
            }
        }
        parentFragmentManager.setFragmentResultListener(
            "date",
            this,
            FragmentResultListener { _, result ->
                var datetoday: String? = result.getString("date")
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate).setText(datetoday)
            })
        parentFragmentManager.setFragmentResultListener(
            "preferentialDate",
            this,
            FragmentResultListener { _, result ->
                var preferentialdate: String? = result.getString("preferentialDate")
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextPreferentialDate)
                    .setText(preferentialdate)
            })
        parentFragmentManager.setFragmentResultListener(
            "startDate",
            this,
            FragmentResultListener { _, result ->
                var startdate: String? = result.getString("startDate")
                view.findViewById<TextInputEditText>(R.id.TextInputEdiStartDate)
                    .setText(startdate)
            })
        parentFragmentManager.setFragmentResultListener(
            "endDate",
            this,
            FragmentResultListener { _, result ->
                var enddate: String? = result.getString("endDate")
                view.findViewById<TextInputEditText>(R.id.TextInputEditEndDate)
                    .setText(enddate)
            })

        parentFragmentManager.setFragmentResultListener(
            "provider",
            this,
            FragmentResultListener { _, result ->
                var provider: String? = result.getString("provider")
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextProvider)
                    .setText(provider)
            })

        parentFragmentManager.setFragmentResultListener(
            "responsable",
            this,
            FragmentResultListener { _, result ->
                var responsable: String? = result.getString("responsable")
                view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable)
                    .setText(responsable)
            })
    }

    fun reset(view: View, responsable: String) {
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextDate)
            .setText(SimpleDateFormat("dd/MM/yyy").format(Date()))
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextProduct).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextProvider).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextDelivery).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextLotNumber).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextAmount).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextCondition).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextPreferentialDate)
            .setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEdiStartDate).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditEndDate).setText("")
        view.findViewById<TextInputEditText>(R.id.TextInputEditTextResponsable).setText(responsable)
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