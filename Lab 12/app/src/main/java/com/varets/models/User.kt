package com.varets.models

data class User (
    override var id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
) : GenericEntity {
    override fun toString(): String {
        return "${this.username} - ${this.name} - ${this.email} - ${this.phone} - ${this.website}"
    }
}