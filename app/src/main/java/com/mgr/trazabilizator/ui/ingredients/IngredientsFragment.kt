package com.mgr.trazabilizator.ui.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mgr.trazabilizator.R


class IngredientsFragment : Fragment() {

    private lateinit var root: View
    private var adapter: PerishableAdapter? = null
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var perishableRef: CollectionReference = db.collection("perishable")
    private var adapter2: NonPerishableAdapter? = null
    private var nonPerishableRef: CollectionReference = db.collection("nonPerishable")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        root = inflater.inflate(R.layout.fragment_recycleringredients, container, false)
        setUpRecyclerView()
        setUpRecyclerView2()
        return root
    }

    private fun setUpRecyclerView() {
        var query: Query = perishableRef.orderBy("Product").whereEqualTo("EndDate", "")
        var options: FirestoreRecyclerOptions<IngredientsModel> =
            FirestoreRecyclerOptions.Builder<IngredientsModel>()
                .setQuery(query, IngredientsModel::class.java).build()

        adapter = PerishableAdapter(options)
        var product: RecyclerView = root.findViewById(R.id.recyclerView)
        product.layoutManager = LinearLayoutManager(context)
        product.adapter = adapter
    }

    private fun setUpRecyclerView2() {
        var query: Query = nonPerishableRef.orderBy("Product").whereEqualTo("EndDate", "")
        var options: FirestoreRecyclerOptions<IngredientsModel> =
            FirestoreRecyclerOptions.Builder<IngredientsModel>()
                .setQuery(query, IngredientsModel::class.java).build()

        adapter2 = NonPerishableAdapter(options)
        var product: RecyclerView = root.findViewById(R.id.recyclerView2)
        product.layoutManager = LinearLayoutManager(context)
        product.adapter = adapter2
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
        adapter2!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
        adapter2!!.stopListening()
    }

}