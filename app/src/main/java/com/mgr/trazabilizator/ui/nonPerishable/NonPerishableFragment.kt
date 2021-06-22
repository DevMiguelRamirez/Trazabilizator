package com.mgr.trazabilizator.ui.nonPerishable

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

class NonPerishableFragment : Fragment() {

    private lateinit var nav: NavController
    private lateinit var root: View
    private var adapter: NonPerishableAdapter? = null
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var nonPerishableRef: CollectionReference = db.collection("nonPerishable")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        root = inflater.inflate(R.layout.fragment_recyclerfood, container, false)
        setUpRecyclerView()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        view.findViewById<FloatingActionButton>(R.id.buttonAdd)?.setOnClickListener() {
            nav.navigate(R.id.nav_newNonPerishableFragment)
        }
    }

    private fun setUpRecyclerView() {
        var query: Query = nonPerishableRef.orderBy("EndDate", Query.Direction.ASCENDING)
        var options: FirestoreRecyclerOptions<NonPerishableModel> =
            FirestoreRecyclerOptions.Builder<NonPerishableModel>()
                .setQuery(query, NonPerishableModel::class.java).build()
        adapter = NonPerishableAdapter(options)
        var product: RecyclerView = root.findViewById(R.id.recyclerViewFood)
        product.layoutManager = LinearLayoutManager(context)
        product.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}