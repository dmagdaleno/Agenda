package br.com.alura.modelo

class Aluno {
    var id: Long? = null
    var nome: String? = null
    var endereco: String? = null
    var telefone: String? = null
    var site: String? = null
    var nota: Double = 0.toDouble()

    override fun toString(): String {
        return id.toString() + " - " + nome
    }
}