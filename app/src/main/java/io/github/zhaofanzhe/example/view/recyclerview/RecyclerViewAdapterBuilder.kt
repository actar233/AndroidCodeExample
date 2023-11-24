package io.github.zhaofanzhe.example.view.recyclerview

import android.view.LayoutInflater
import androidx.annotation.LayoutRes

class RecyclerViewAdapterBuilder<T> {

    private var onCreateView: RecyclerViewAdapterOnCreateView? = null

    private var onInitView: RecyclerViewAdapterOnInitView<T>? = null

    private var onBindViewHolder: RecyclerViewAdapterOnBindViewHolder<T>? = null

    fun inflateCreateView(@LayoutRes resource: Int) {
        onCreateView { parent, viewType ->
            LayoutInflater.from(parent.context).inflate(resource, parent, false)
        }
    }

    fun onCreateView(onCreateView: RecyclerViewAdapterOnCreateView) {
        this.onCreateView = onCreateView
    }

    fun onInitView(onInitView: RecyclerViewAdapterOnInitView<T>) {
        this.onInitView = onInitView
    }

    fun onBindViewHolder(onBindViewHolder: RecyclerViewAdapterOnBindViewHolder<T>) {
        this.onBindViewHolder = onBindViewHolder
    }

    fun builder(list: MutableList<T>): RecyclerViewAdapter<T> {
        if (onCreateView == null) {
            throw NullPointerException("onCreateView is null")
        }
        if (onBindViewHolder == null) {
            throw NullPointerException("onBindViewHolder is null")
        }
        return RecyclerViewAdapter(list, onCreateView!!, onInitView, onBindViewHolder!!)
    }

}