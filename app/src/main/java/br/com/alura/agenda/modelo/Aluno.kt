package br.com.alura.agenda.modelo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Aluno (
    val id: String? = null,
    val nome: String = "undefined",
    val endereco: String = "undefined",
    val telefone: String = "undefined",
    val site: String = "undefined",
    val nota: Double = 0.0,
    val foto: String? = null,
    val desativado: Int = 0,
    val sincronizado: Int = 0

): Serializable {

    val estaDesativado
        get() = desativado == 1

    val naoEstaSincronizado
        get() = sincronizado == 0

    fun sincroniza() = if(this.naoEstaSincronizado) {
            this.copy(sincronizado = 1)
        } else {
            this
        }

    fun desativa() = if(this.estaDesativado) {
        this
    } else {
        this.copy(desativado = 1, sincronizado = 0)
    }
}