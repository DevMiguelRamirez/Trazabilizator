package com.mgr.trazabilizator.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.mgr.trazabilizator.R

class aboutFragment : Fragment() {

    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        root.findViewById<TextView>(R.id.textView3).setOnClickListener() {
            count++
            if (count >= 9) {
                root.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.lucro)
            }
        }
        return root
    }
}



