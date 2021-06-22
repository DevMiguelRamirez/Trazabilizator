package com.mgr.trazabilizator.ui.ingredients

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.mgr.trazabilizator.R


open class NonPerishableAdapter(options: FirestoreRecyclerOptions<IngredientsModel>) :
    FirestoreRecyclerAdapter<IngredientsModel, NonPerishableAdapter.NonPerishableVH>(options) {

    class NonPerishableVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nonPerishableProduct = itemView.findViewById<TextView>(R.id.textViewIngredientName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonPerishableVH {
        return NonPerishableVH(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_ingredients, parent, false))
    }

    override fun onBindViewHolder(holder: NonPerishableVH, position: Int, model: IngredientsModel) {
        holder.nonPerishableProduct.text = model.Product
        holder.itemView.setOnClickListener { view ->
            var productName: String = holder.nonPerishableProduct.text.toString()

            var clipboardManager =
                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var readClip: String = clipboardManager.primaryClip?.getItemAt(0)?.text as String
            var setClip: ClipData =
                ClipData.newPlainText("copy text", productName + ", " + readClip)
            clipboardManager.setPrimaryClip(setClip)
            Toast.makeText(view.context, (productName + " selected."), Toast.LENGTH_SHORT).show()
        }
    }
}