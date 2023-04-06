package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard

data class NoticeBoardData(
    val text : String,
    val content : String,
    val uri : String,
    val key : Long
) {
    constructor() : this("", "", "", 0)
}
