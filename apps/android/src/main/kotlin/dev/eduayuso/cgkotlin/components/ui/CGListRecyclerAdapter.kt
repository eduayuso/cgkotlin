package dev.eduayuso.cgkotlin.components.ui

import androidx.recyclerview.widget.RecyclerView
import dev.eduayuso.cgkotlin.shared.domain.entities.IEntity

abstract class CGListRecyclerAdapter<EntityType: IEntity, VH: CGViewHolder>:

    RecyclerView.Adapter<VH>() {

    private var dataList: MutableList<EntityType> = mutableListOf()
    val items: List<EntityType> get() = dataList

    override fun onBindViewHolder(viewHolder: VH, i: Int) {
        viewHolder.onBind(i)
    }

    override fun getItemCount(): Int {

        return dataList.size
    }

    fun addItems(data: List<EntityType>) {

        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}