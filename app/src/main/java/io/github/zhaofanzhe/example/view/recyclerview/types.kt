package io.github.zhaofanzhe.example.view.recyclerview

import android.view.View
import android.view.ViewGroup

typealias RecyclerViewAdapterOnCreateView = (parent: ViewGroup, viewType: Int) -> View

typealias RecyclerViewAdapterOnInitView<T> = RecyclerViewHolder<T>.() -> Unit

typealias RecyclerViewAdapterOnBindViewHolder<T> = RecyclerViewHolder<T>.(model: T) -> Unit