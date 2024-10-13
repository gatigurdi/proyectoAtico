package com.gurdiel.gestiondesoporte.data.response

import com.gurdiel.gestiondesoporte.presentacion.model.Averia
import com.gurdiel.gestiondesoporte.presentacion.model.Usuario

data class UsuarioResponse (
    val id: String = "",
    val nombre: String = "",
    val email: String = "",
    val date: Long = 1,
    val rol: String = "",
    val averiasCreadas: List<String> = emptyList(),
    val averiasAsignadas: List<String> = emptyList()
){
    fun toDomain(): Usuario {
        return Usuario(
            id = id,
            nombre = nombre,
            email = email,
            date = date,
            rol = rol,
            averiasCreadas = averiasCreadas,
            averiasAsignadas = averiasAsignadas
        )
    }
}