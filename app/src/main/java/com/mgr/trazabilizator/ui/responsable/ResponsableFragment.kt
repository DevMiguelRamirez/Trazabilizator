package com.mgr.trazabilizator.ui.responsable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import com.mgr.trazabilizator.R

class ResponsableFragment() : Fragment() {

    private lateinit var root: View
    private var adapter: ResponsableAdapter? = null
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var resposableRef: CollectionReference = db.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        root = inflater.inflate(R.layout.fragment_recyclerresponsable, container, false)
        setUpRecyclerView()
        return root
    }

    private fun setUpRecyclerView() {
        var query: Query = resposableRef.orderBy("Name")
        var options: FirestoreRecyclerOptions<ResponsableModel> =
            FirestoreRecyclerOptions.Builder<ResponsableModel>()
                .setQuery(query, ResponsableModel::class.java).build()
        adapter = ResponsableAdapter(options)
        var responsables: RecyclerView = root.findViewById(R.id.recyclerView)
        responsables.layoutManager = LinearLayoutManager(context)
        responsables.adapter = adapter
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
