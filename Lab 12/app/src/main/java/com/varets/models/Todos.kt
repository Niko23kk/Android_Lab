package com.varets.models

data class Todos (
    override var id: Int,
    val userId: Int,
    val title: String,
    val completed: String
) : GenericEntity {
    override fun toString(): String {
        return "${this.userId} - ${this.title} - ${this.completed} !!!"
    }
}