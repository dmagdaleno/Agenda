package br.com.alura.modelo

import java.io.Serializable

data class Aluno (
    var id: Long?,
    var nome: String?,
    var endereco: String?,
    var telefone: String?,
    var site: String?,
    var nota: Double ): Serializable {

        override fun toString(): String {
            return id.toString() + " - " + nome
        }
}