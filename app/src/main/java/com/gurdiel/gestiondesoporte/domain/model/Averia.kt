package com.gurdiel.gestiondesoporte.domain.model

data class Averia(

    val id: String = System.currentTimeMillis().hashCode().toString(),
    val titulo: String,
    val descripcion: String,
    val tipoAveria: TipoAveria,
    val date: Long,
    val prioridad: Prioridad,
    val estado: String,
    val usuarioCreador: String,
    val tecnico: String

)

enum class Prioridad {
    BAJA, MEDIA, ALTA
}

enum class TipoAveria {
    HARDWARE, SOFTWARE, RED
}
