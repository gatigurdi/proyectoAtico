package com.gurdiel.gestiondesoporte.domain.model

data class Averia(

    val id: String = System.currentTimeMillis().hashCode().toString(),
    val titulo: String,
    val descripcion: String,
    val tipoAveria: TipoAveria,
    val date: String,
    val prioridad: String,
    val estado: String,
    val usuarioCreador: Usuario,
    val tecnico: Usuario

)

enum class Prioridad {
    BAJA, MEDIA, ALTA
}

enum class TipoAveria {
    HARDWARE, SOFTWARE, RED
}
