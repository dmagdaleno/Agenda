package br.com.alura.agenda.retrofit.service.dto

import br.com.alura.agenda.modelo.Aluno

data class ListaAlunoDTO(
        val alunos: List<Aluno> = emptyList(),
        val momentoDaUltimaModificacao: String = "undefined")