package com.mgr.trazabilizator.ui.provider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import com.mgr.trazabilizator.R


class ProviderFragment() : Fragment() {

    private lateinit var nav: NavController
    private lateinit var root: View
    private var adapter: ProviderAdapter? = null
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var providerRef: CollectionReference = db.collection("provider")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        root = inflater.inflate(R.layout.fragment_recyclerprovider, container, false)
        setUpRecyclerView()
        return root
    }

    private fun setUpRecyclerView() {
        var query: Query = providerRef.orderBy("Name")
        var options: FirestoreRecyclerOptions<ProviderModel> =
            FirestoreRecyclerOptions.Builder<ProviderModel>()
                .setQuery(query, ProviderModel::class.java).build()
        adapter = ProviderAdapter(options)
        var providers: RecyclerView = root.findViewById(R.id.recyclerView)
        providers.layoutManager = LinearLayoutManager(context)
        providers.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        view.findViewById<FloatingActionButton>(R.id.buttonAddPerishable).setOnClickListener() {
            nav.navigate(R.id.nav_newProviderFragment)
        }
    }
}


