package io.github.zhaofanzhe.example.editor

import android.text.Editable
import android.text.NoCopySpan
import android.text.SpannableStringBuilder
import android.text.Spanned

class EditorEditableFactory(private vararg val spans: NoCopySpan): Editable.Factory() {

    override fun newEditable(source: CharSequence): Editable {
        return SpannableStringBuilder.valueOf(source).apply {
            spans.forEach {
                setSpan(it, 0, source.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            }
        }
    }

}