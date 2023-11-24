package io.github.zhaofanzhe.example.view.recyclerview

fun <T : Any> recyclerViewAdapter(
    list: MutableList<T> = mutableListOf(),
    init: RecyclerViewAdapterBuilder<T>.() -> Unit
): RecyclerViewAdapter<T> {
    return RecyclerViewAdapterBuilder<T>().apply(init).builder(list)
}