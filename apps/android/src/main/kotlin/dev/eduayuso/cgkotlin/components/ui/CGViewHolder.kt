package dev.eduayuso.cgkotlin.components.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class CGViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(position: Int)
}