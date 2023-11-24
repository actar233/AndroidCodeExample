package io.github.zhaofanzhe.example.editor

import android.text.style.ClickableSpan
import android.view.View

class AtomSpan(val mark: EditorMark) : ClickableSpan() {

    private var onClickListener: View.OnClickListener? = null

    fun setOnClickListener(listener: View.OnClickListener) {
        onClickListener = listener
    }

    override fun onClick(widget: View) {
        onClickListener?.onClick(widget)
    }

}