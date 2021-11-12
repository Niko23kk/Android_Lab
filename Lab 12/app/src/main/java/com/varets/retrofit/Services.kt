package com.varets.retrofit

import com.varets.models.Todos
import com.varets.models.Superhero
import com.varets.models.User
import retrofit2.Call
import retrofit2.http.GET

interface Services {
    @GET("/demos/marvel")
    fun getSuperheroes(): Call<MutableList<Superhero>>

    @GET("/todos")
    fun getPosts(): Call<MutableList<Todos>>

    @GET("/users")
    fun getUsers(): Call<MutableList<User>>
}