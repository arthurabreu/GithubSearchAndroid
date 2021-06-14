package com.arthur.github.core.data.network.api

import com.arthur.github.core.data.network.model.Repos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FakeGithubApi {

    fun getRepos(): Repos {
        val type = object: TypeToken<Repos>() {}.type
        val myFile =
                ClassLoader.getSystemResource("repos.json").readText()
        return Gson().fromJson(myFile, type)
    }
}