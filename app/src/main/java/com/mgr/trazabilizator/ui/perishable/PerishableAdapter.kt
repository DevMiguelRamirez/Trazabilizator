package com.mgr.trazabilizator.ui.perishable

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


open class PerishableAdapter(options: FirestoreRecyclerOptions<PerishableModel>) :
    FirestoreRecyclerAdapter<PerishableModel, PerishableAdapter.PerishableVH>(options) {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    class PerishableVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout = itemView.findViewById<LinearLayout>(R.id.LinearLayoutFood)
        var perishableDate = itemView.findViewById<TextView>(R.id.textViewDateEdit)
        var perishableProduct = itemView.findViewById<TextView>(R.id.textViewProductName)
        var perishableProvider = itemView.findViewById<TextView>(R.id.textViewProviderEdit)
        var perishableDelivery = itemView.findViewById<TextView>(R.id.textViewDeliveryEdit)
        var perishableLot = itemView.findViewById<TextView>(R.id.textViewLotEdit)
        var perishableAmount = itemView.findViewById<TextView>(R.id.textViewAmountEdit)
        var perishableTemperature = itemView.findViewById<TextView>(R.id.textViewTemperatureEdit)
        var perishableCondition = itemView.findViewById<TextView>(R.id.textViewConditionEdit)
        var perishableExpiry =
            itemView.findViewById<TextView>(R.id.textViewExpEdit)
        var perishableStart = itemView.findViewById<TextView>(R.id.textViewStartEdit)
        var perishableEnd = itemView.findViewById<TextView>(R.id.textViewEndEdit)
        var perishableResponsable = itemView.findViewById<TextView>(R.id.textViewResponsableEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerishableVH {

        return PerishableVH(
            LayoutInflater.from(parent.context).inflate(R.layout.row_foodperishable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PerishableVH, position: Int, model: PerishableModel) {
        holder.perishableProduct.text = model.Product
        holder.perishableDate.text = model.Date
        holder.perishableLot.text = model.Lot
        holder.perishableStart.text = model.StartDate
        holder.perishableProvider.text = model.Provider
        holder.perishableDelivery.text = model.Delivery
        holder.perishableAmount.text = model.Amount
        holder.perishableTemperature.text = model.Temperature
        holder.perishableCondition.text = model.Condition
        holder.perishableExpiry.text = model.ExpiryDate
        holder.perishableEnd.text = model.EndDate
        holder.perishableResponsable.text = model.Responsable
        holder.itemView.findViewById<CardView>(R.id.cardView).setOnClickListener { view ->
            db.collection("perishable")
                .whereEqualTo("Product", holder.perishableProduct.text)
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
                (holder.perishableProduct.text.toString() + " selected."),
                Toast.LENGTH_LONG).show()
            Thread.sleep(200)
            view.findNavController().navigate(R.id.nav_editPerishableFragment)
        }
    }
}