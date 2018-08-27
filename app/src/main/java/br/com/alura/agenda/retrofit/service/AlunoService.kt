package br.com.alura.agenda.retrofit.service

import br.com.alura.agenda.modelo.Aluno
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AlunoService {

    @POST("aluno")
    fun insere(@Body aluno: Aluno): Call<Unit>
}