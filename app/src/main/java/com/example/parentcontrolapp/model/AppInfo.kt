package com.example.parentcontrolapp.model

data class AppInfo (
    val name: String,
    val packageName: String,
    val icon: String,
    val timeUsage: Long,
    val lockStatus: Boolean,
    val androidId: String,
)