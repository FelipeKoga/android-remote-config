package com.koga.pocremoteconfig

data class HomeConfig(
    val toolbar: Widget,
    val firstText: Text,
    val secondText: Text,
    val primaryButton: Widget,
    val animationUrl: String,
)

data class Text(
    val text: String,
    val color: String,
)

data class Widget(
    val text: String,
    val backgroundColor: String,
    val textColor: String
)