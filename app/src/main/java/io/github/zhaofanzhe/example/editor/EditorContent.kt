package io.github.zhaofanzhe.example.editor

data class EditorContent(
    var text: String,
    var marks: List<EditorMark> = listOf()
)