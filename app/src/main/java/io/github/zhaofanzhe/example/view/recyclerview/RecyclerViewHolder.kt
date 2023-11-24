package io.github.zhaofanzhe.example.view.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder<T>(
    view: View,
    private val getBindingModel: (position: Int) -> T,
) : RecyclerView.ViewHolder(view) {

    private val views = mutableMapOf<Int, View?>()

    fun <T : View?> findViewById(@IdRes id: Int): T {
        if (!views.containsKey(id)) {
            views[id] = this.itemView.findViewById<T>(id)
        }
        @Suppress("UNCHECKED_CAST")
        return views[id] as T
    }

    fun getBindingModel(): T {
        return getBindingModel(bindingAdapterPosition)
    }

}