package com.dev.daggerapp.model.response.newsresponse

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)