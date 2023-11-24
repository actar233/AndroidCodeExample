package io.github.zhaofanzhe.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.zhaofanzhe.example.view.recyclerview.recyclerViewAdapter


class SelectUserActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user)

        val recyclerView = findViewById<RecyclerView>(R.id.list)

        val list = mutableListOf("张三", "李四", "赵五", "王六")

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = recyclerViewAdapter(list) {
            onCreateView { parent, _ ->
                TextView(parent.context).also {
                    it.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    it.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                }
            }
            onInitView {
                (this.itemView as TextView).setOnClickListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra("user", getBindingModel())
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
            onBindViewHolder {
                (this.itemView as TextView).text = it
            }
        }

    }

}