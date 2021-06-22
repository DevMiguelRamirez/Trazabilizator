package com.mgr.trazabilizator.ui.provider

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.mgr.trazabilizator.R


open class ProviderAdapter(
    options: FirestoreRecyclerOptions<ProviderModel>,
) :
    FirestoreRecyclerAdapter<ProviderModel, ProviderAdapter.ProviderVH>(options) {

    class ProviderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var providerName = itemView.findViewById<TextView>(R.id.textViewProviderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderVH {
        return ProviderVH(
            LayoutInflater.from(parent.context).inflate(R.layout.row_provider, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProviderVH, position: Int, model: ProviderModel) {
        holder.providerName.text = model.Name
        holder.itemView.setOnClickListener { view ->
            var providerName: String = holder.providerName.text.toString()
            var clipboard: ClipboardManager =
                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clip: ClipData = ClipData.newPlainText("copy text", providerName)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(view.context, (providerName + " selected."), Toast.LENGTH_LONG).show()
            view.findNavController().popBackStack()
        }
    }
}



