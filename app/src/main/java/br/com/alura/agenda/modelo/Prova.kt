package br.com.alura.agenda.modelo

import java.io.Serializable

class Prova(
        val materia: String,
        val data: String,
        val topicos: List<String>): Serializable {

    override fun toString(): String {
        return materia
    }
}