package br.com.alura.agenda.modelo

import java.io.Serializable

data class Aluno (
    val id: Long? = null,
    val nome: String,
    val endereco: String,
    val telefone: String,
    val site: String,
    val nota: Double,
    val foto: String?): Serializable