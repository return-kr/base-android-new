package com.dev.daggerapp.repository

import com.dev.daggerapp.model.response.newsresponse.NewsResponse

interface AppRepository {
    fun getAllNews(): NewsResponse
}