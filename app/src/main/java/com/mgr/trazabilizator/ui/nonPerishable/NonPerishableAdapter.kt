package com.mgr.trazabilizator.ui.nonPerishable

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.mgr.trazabilizator.R

open class NonPerishableAdapter(options: FirestoreRecyclerOptions<NonPerishableModel>) :
    FirestoreRecyclerAdapter<NonPerishableModel, NonPerishableAdapter.NonPerishableVH>(options) {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    class NonPerishableVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout = itemView.findViewById<LinearLayout>(R.id.LinearLayoutFood)
        var nonPerishableDate = itemView.findViewById<TextView>(R.id.textViewDateEdit)
        var nonPerishableProduct = itemView.findViewById<TextView>(R.id.textViewProductName)
        var nonPerishableProvider = itemView.findViewById<TextView>(R.id.textViewProviderEdit)
        var nonPerishableDelivery = itemView.findViewById<TextView>(R.id.textViewDeliveryEdit)
        var nonPerishableLot = itemView.findViewById<TextView>(R.id.textViewLotEdit)
        var nonPerishableAmount = itemView.findViewById<TextView>(R.id.textViewAmountEdit)
        var nonPerishableCondition = itemView.findViewById<TextView>(R.id.textViewConditionEdit)
        var nonPerishablePreferential =
            itemView.findViewById<TextView>(R.id.textViewPreferentialEdit)
        var nonPerishableStart = itemView.findViewById<TextView>(R.id.textViewStartEdit)
        var nonPerishableEnd = itemView.findViewById<TextView>(R.id.textViewEndEdit)
        var nonPerishableResponsable = itemView.findViewById<TextView>(R.id.textViewResponsableEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonPerishableVH {
        return NonPerishableVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_foodnonperishable, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: NonPerishableVH,
        position: Int,
        model: NonPerishableModel
    ) {
        holder.nonPerishableProduct.text = model.Product
        holder.nonPerishableDate.text = model.Date
        holder.nonPerishableLot.text = model.Lot
        holder.nonPerishableStart.text = model.StartDate
        holder.nonPerishableProvider.text = model.Provider
        holder.nonPerishableDelivery.text = model.Delivery
        holder.nonPerishableAmount.text = model.Amount
        holder.nonPerishableCondition.text = model.Condition
        holder.nonPerishablePreferential.text = model.PreferentialDate
        holder.nonPerishableEnd.text = model.EndDate
        holder.nonPerishableResponsable.text = model.Responsable
        holder.itemView.findViewById<CardView>(R.id.cardView).setOnClickListener{ view ->
            db.collection("nonPerishable")
                .whereEqualTo("Product", holder.nonPerishableProduct.text)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        var clipboard: ClipboardManager =
                            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        var clip: ClipData = ClipData.newPlainText("copy text", "ID:" + document.id)
                        clipboard.setPrimaryClip(clip)
                    }
                }
            Toast.makeText(view.context, (holder.nonPerishableProduct.text.toString() + " selected."), Toast.LENGTH_LONG).show()
            Thread.sleep(200)
            view.findNavController().navigate(R.id.nav_editNonPerishableFragment)
        }
    }
}