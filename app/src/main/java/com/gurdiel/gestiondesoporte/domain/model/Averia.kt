package com.gurdiel.gestiondesoporte.domain.model

data class Averia(

    val id: String = System.currentTimeMillis().hashCode().toString(),
    val titulo: String,
    val descripcion: String,
    val tipoAveria: TipoAveria = TipoAveria.SOFTWARE,
    val date: Long = System.currentTimeMillis(),
    val prioridad: Prioridad = Prioridad.BAJA,
    val estado: Estado = Estado.CREADA,
    val usuarioCreador: String = "EJEMPLO",
    val tecnico: String = "EJEMPLOTECNICO"

)

enum class Prioridad {
    BAJA, MEDIA, ALTA
}

enum class TipoAveria {
    HARDWARE, SOFTWARE, RED
}

enum class Estado {
    CREADA, ASIGNADA, EN_PROGRESO, RESUELTA, CANCELADA
}
