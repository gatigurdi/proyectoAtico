package com.gurdiel.gestiondesoporte.presentacion.model

data class Usuario(

    val id: String,
    val nombre: String,
    val email: String,
    val date: Long = System.currentTimeMillis(),
    val rol: String = Rol.EMPRESA.toString(),
    val averiasCreadas: List<String> = emptyList(),
    val averiasAsignadas: List<String> = emptyList()

)


enum class Rol {
    ADMINISTRADOR, TECNICO, EMPRESA
}

