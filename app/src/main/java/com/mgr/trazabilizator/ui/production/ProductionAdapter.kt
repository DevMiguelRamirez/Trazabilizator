package com.mgr.trazabilizator.ui.production

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.mgr.trazabilizator.R

open class ProductionAdapter(options: FirestoreRecyclerOptions<ProductionModel>) :
    FirestoreRecyclerAdapter<ProductionModel, ProductionAdapter.ProductionVH>(options) {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    class ProductionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productionProduct = itemView.findViewById<TextView>(R.id.textViewProductName)
        var productionDate = itemView.findViewById<TextView>(R.id.textViewDateEdit)
        var productionLot = itemView.findViewById<TextView>(R.id.textViewLotEdit)
        var productionAmount = itemView.findViewById<TextView>(R.id.textViewAmountEdit)
        var productionIngredients = itemView.findViewById<TextView>(R.id.textViewIngredientsEdit)
        var productionIncidents = itemView.findViewById<TextView>(R.id.textViewIncidentsEdit)
        var productionCorrectives = itemView.findViewById<TextView>(R.id.textViewCorrectivesEdit)
        var productionObservations =
            itemView.findViewById<TextView>(R.id.textViewObservationsEdit)
        var productionResponsable = itemView.findViewById<TextView>(R.id.textViewResponsableEdit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionVH {

        return ProductionVH(
            LayoutInflater.from(parent.context).inflate(R.layout.row_production, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductionVH, position: Int, model: ProductionModel) {
        holder.productionProduct.text = model.Product
        holder.productionLot.text = model.Lot
        holder.productionAmount.text = model.Amount
        holder.productionIngredients.text = model.Ingredients
        holder.productionIncidents.text = model.Incidents
        holder.productionCorrectives.text = model.Correctives
        holder.productionObservations.text = model.Observations
        holder.productionResponsable.text = model.Responsable
        holder.productionDate.text = model.Date
        holder.itemView.findViewById<CardView>(R.id.cardView).setOnClickListener { view ->
            db.collection("productionControl")
                .whereEqualTo("Product", holder.productionProduct.text)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        var clipboard: ClipboardManager =
                            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        var clip: ClipData = ClipData.newPlainText("copy text", "ID:" + document.id)
                        clipboard.setPrimaryClip(clip)
                    }
                }
            Toast.makeText(view.context,
                (holder.productionProduct.text.toString() + " selected."),
                Toast.LENGTH_LONG).show()
            Thread.sleep(200)
            view.findNavController().navigate(R.id.nav_editProductionControlFragment)
        }
    }
}