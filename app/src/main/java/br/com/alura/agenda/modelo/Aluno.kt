package br.com.alura.agenda.modelo

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Aluno (
    @field:JsonProperty("idCliente") val id: Long? = null,
    val nome: String,
    val endereco: String,
    val telefone: String,
    val site: String,
    val nota: Double,
    val foto: String?): Serializable