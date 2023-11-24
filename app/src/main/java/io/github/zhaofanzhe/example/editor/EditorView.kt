package io.github.zhaofanzhe.example.editor

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.widget.EditText
import androidx.core.text.getSpans
import androidx.core.widget.addTextChangedListener

@SuppressLint("AppCompatCustomView")
class EditorView : EditText {

    private var markOnClickListener: ((EditorMark) -> Unit)? = null

    private val keywordTriggerListeners: MutableMap<Char, () -> Unit> = mutableMapOf()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener KeyCodeDeleteHelper.onDelDown((v as EditText).text)
            }
            return@setOnKeyListener false
        }
        addTextChangedListener(onTextChanged = { text: CharSequence?, start: Int, before: Int, count: Int ->
            if (count != 1) {
                return@addTextChangedListener
            }
            val keyword = text?.get(start)
            if (keyword != null) {
                keywordTriggerListeners[keyword]?.invoke()
            }
        })
        setEditableFactory(EditorEditableFactory(SelectionSpanWatcher(AtomSpan::class)))
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return object : InputConnectionWrapper(super.onCreateInputConnection(outAttrs), true) {
            override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
                if (beforeLength == 1 && afterLength == 0) {
                    if (KeyCodeDeleteHelper.onDelDown(text)) {
                        return false
                    }
                    return super.deleteSurroundingText(beforeLength, afterLength)
                }
                return super.deleteSurroundingText(beforeLength, afterLength)
            }
        }
    }

    fun setContent(content: EditorContent) {
        val spannable = SpannableStringBuilder()
        spannable.append(content.text)
        content.marks.forEach { mark ->
            val span = AtomSpan(mark)
            span.setOnClickListener {
                markOnClickListener?.invoke(mark)
            }
            spannable.setSpan(span, mark.start, mark.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        text = spannable
    }

    fun getContent(): EditorContent {
        val content = text.toString()
        val marks = text.getSpans<AtomSpan>().map {
            val mark = it.mark.copy()
            mark.start = text.getSpanStart(it)
            mark.end = text.getSpanEnd(it)
            mark
        }
        return EditorContent(content, marks)
    }

    fun insertMark(content: String, mark: EditorMark, start: Int, end: Int) {
        text.replace(start, end, content)
        text.setSpan(
            AtomSpan(mark),
            start,
            start + content.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    fun setMarkOnClickListener(listener: (EditorMark) -> Unit) {
        markOnClickListener = listener
    }

    fun addKeywordTriggerListener(keyword: Char, listener: () -> Unit) {
        keywordTriggerListeners[keyword] = listener
    }

}