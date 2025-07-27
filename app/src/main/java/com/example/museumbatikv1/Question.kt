package com.example.museumbatikv1 // Ganti dengan package Anda

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)