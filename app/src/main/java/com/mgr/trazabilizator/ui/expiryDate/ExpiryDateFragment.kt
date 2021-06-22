package com.mgr.trazabilizator.ui.expiryDate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mgr.trazabilizator.R
import java.text.SimpleDateFormat
import java.util.*


class ExpiryDateFragment : Fragment() {

    private lateinit var nav: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_date, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        var date: String = SimpleDateFormat("dd/MM/yyyy").format(Date()).toString()
        view.findViewById<CalendarView>(R.id.calendarView)
            .setOnDateChangeListener { _, year, month, dayOfMonth ->
                var trueMonth = month + 1
                date = "$dayOfMonth/$trueMonth/$year"
            }
        view.findViewById<Button>(R.id.buttonChoose).setOnClickListener {
            var bundle: Bundle = Bundle()
            bundle.putString("expiryDate", date.trim())
            parentFragmentManager.setFragmentResult("expiryDate", bundle)
            nav.popBackStack()
        }
    }
}