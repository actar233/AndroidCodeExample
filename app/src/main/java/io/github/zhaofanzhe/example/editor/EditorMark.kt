package io.github.zhaofanzhe.example.editor

data class EditorMark(
    var start: Int = -1,
    var end: Int = -1,
    var metadata: Map<String, Any?> = mapOf()
)