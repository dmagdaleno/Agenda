package br.com.alura.modelo

import java.io.Serializable

data class Aluno (
    val id: Long? = null,
    val nome: String,
    val endereco: String,
    val telefone: String,
    val site: String,
    val nota: Double,
    val foto: String?): Serializable {

        override fun toString(): String {
            return id.toString() + " - " + nome
        }
}