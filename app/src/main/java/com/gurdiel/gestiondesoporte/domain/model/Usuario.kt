package com.gurdiel.gestiondesoporte.domain.model

data class Usuario(

    val id: String,
    val name: String,
    val email: String,
    val date: Long = System.currentTimeMillis(),
    val rol: Rol,
    val averiasCreadas: List<String> = emptyList(),
    val averiasAsignadas: List<String> = emptyList()

)
enum class Rol {
    ADMINISTRADOR, TECNICO, EMPRESA
}

