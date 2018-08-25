package br.com.alura.agenda.modelo

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
        return "Aluno(id=$id, nome='$nome', endereco='$endereco', telefone='$telefone', site='$site', nota=$nota, foto=$foto)"
    }

}