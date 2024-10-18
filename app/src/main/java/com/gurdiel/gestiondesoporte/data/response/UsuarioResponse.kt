package com.gurdiel.gestiondesoporte.data.response

import com.gurdiel.gestiondesoporte.domain.model.Rol
import com.gurdiel.gestiondesoporte.domain.model.Usuario

data class UsuarioResponse(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val date: Long = 1,
    val rol: Rol = Rol.EMPRESA,
    val averiasCreadas: List<String> = emptyList(),
    val averiasAsignadas: List<String> = emptyList()
){
    fun toDomain(): Usuario {
        return Usuario(
            id = id,
            name = name,
            email = email,
            date = date,
            rol = rol,
            averiasCreadas = averiasCreadas,
            averiasAsignadas = averiasAsignadas
        )
    }
}


