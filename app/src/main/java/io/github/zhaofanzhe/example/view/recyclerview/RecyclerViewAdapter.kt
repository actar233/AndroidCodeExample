package io.github.zhaofanzhe.example.view.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter<T>(
    var list: MutableList<T>,
    private var onCreateView: RecyclerViewAdapterOnCreateView,
    private var onInitView: RecyclerViewAdapterOnInitView<T>?,
    private var onBindViewHolder: RecyclerViewAdapterOnBindViewHolder<T>,
) : RecyclerView.Adapter<RecyclerViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<T> {
        val view = onCreateView(parent, viewType)
        val holder = RecyclerViewHolder(view, this::getBindingModel)
        onInitView?.let { holder.it() }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<T>, position: Int) {
        holder.onBindViewHolder(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getBindingModel(position: Int): T {
        return this.list[position]
    }

}