package com.mgr.trazabilizator.ui.responsable

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

open class ResponsableAdapter(options: FirestoreRecyclerOptions<ResponsableModel>) :
    FirestoreRecyclerAdapter<ResponsableModel, ResponsableAdapter.ResponsableVH>(options) {

    class ResponsableVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var responsableName = itemView.findViewById<TextView>(R.id.textViewProviderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponsableVH {
        return ResponsableVH(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_responsable, parent, false))
    }

    override fun onBindViewHolder(holder: ResponsableVH, position: Int, model: ResponsableModel) {
        holder.responsableName.text = model.Name
        holder.itemView.setOnClickListener { view ->
            var responsableName: String = holder.responsableName.text.toString()
            var clipboard: ClipboardManager =
                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clip: ClipData = ClipData.newPlainText("copy text", responsableName)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(view.context, (responsableName + " selected."), Toast.LENGTH_LONG).show()
            view.findNavController().popBackStack()
        }
    }
}