package com.gurdiel.gestiondesoporte.data.response

import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Prioridad
import com.gurdiel.gestiondesoporte.domain.model.TipoAveria

data class AveriaResponse(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val tipoAveria: TipoAveria = TipoAveria.SOFTWARE,
    val date: Long = 1,
    val prioridad: Prioridad = Prioridad.BAJA,
    val estado: String = "",
    val usuarioCreador: String = "",
    val tecnico: String = ""
){
    fun toDomain(): Averia {
        return Averia(
            id = id,
            titulo = titulo,
            descripcion = descripcion,
            tipoAveria = tipoAveria,
            date = date,
            prioridad = prioridad,
            estado = estado,
            usuarioCreador = usuarioCreador,
            tecnico = tecnico
        )
    }
}
