package io.github.zhaofanzhe.example

import android.content.Intent
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.widget.Button
import android.widget.Toast
import io.github.zhaofanzhe.example.base.BaseActivity
import io.github.zhaofanzhe.example.editor.AtomSpan
import io.github.zhaofanzhe.example.editor.EditorContent
import io.github.zhaofanzhe.example.editor.EditorMark
import io.github.zhaofanzhe.example.editor.EditorView


class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editor = findViewById<EditorView>(R.id.editor)

        editor.setContent(
            EditorContent(
                text = "Hello World @小明 #今日热榜",
                marks = listOf(
                    EditorMark(
                        start = 12,
                        end = 15,
                        metadata = mapOf("type" to "mention")
                    ),
                    EditorMark(
                        start = 16,
                        end = 21,
                        metadata = mutableMapOf("type" to "hashtag")
                    ),
                )
            )
        )

        editor.setMarkOnClickListener {
            Log.d(TAG, "setMarkOnClickListener: $it")
        }

        editor.addKeywordTriggerListener('@') {
            startActivityForResult(Intent(this, SelectUserActivity::class.java)) { _, data ->
                data?.getStringExtra("user")?.let {
                    editor.insertMark(
                        """@${it}""",
                        EditorMark(),
                        editor.selectionStart - 1,
                        editor.selectionStart
                    )
                }
            }
        }

        findViewById<Button>(R.id.print).setOnClickListener {
            val content = editor.getContent()
            Log.d(TAG, "setOnClickListener: $content")
        }

        findViewById<Button>(R.id.select_user).setOnClickListener {
            startActivityForResult(Intent(this, SelectUserActivity::class.java)) { _, data ->
                data?.getStringExtra("user")?.let {
                    editor.insertMark(
                        """@${it}""",
                        EditorMark(),
                        editor.selectionStart,
                        editor.selectionStart
                    )
                }
            }
        }

    }

}
