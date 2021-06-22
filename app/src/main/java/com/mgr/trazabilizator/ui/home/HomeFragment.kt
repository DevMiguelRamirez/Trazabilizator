package com.mgr.trazabilizator.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mgr.trazabilizator.R

class HomeFragment : Fragment() {

    private lateinit var nav: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var clipboardManager =
            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var setClip: ClipData = ClipData.newPlainText("copy text", "")
        clipboardManager.setPrimaryClip(setClip)
        nav = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.buttonPerishable)?.setOnClickListener() {
            nav.navigate(R.id.nav_perishable)
        }

        view.findViewById<Button>(R.id.buttonNonPerishable)?.setOnClickListener() {
            nav.navigate(R.id.nav_nonperishable)
        }

        view.findViewById<Button>(R.id.buttonProduction)?.setOnClickListener() {
            nav.navigate(R.id.nav_production)
        }
    }
}



