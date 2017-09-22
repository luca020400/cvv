package com.luca020400.cvv.models

data class Lesson(
        val evtId: Int,
        val evtDate: String,
        val evtCode: String,
        val evtHPos: Int,
        val evtDuration: Int,
        val classDesc: String,
        val authorName: String,
        val subjectId: Int,
        val subjectCode: String,
        val subjectDesc: String,
        val lessonType: String,
        val lessonArg: String
)
