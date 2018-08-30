package br.com.alura.agenda.retrofit.service

import br.com.alura.agenda.modelo.Aluno
import br.com.alura.agenda.retrofit.service.dto.ListaAlunoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlunoService {

    @POST("aluno")
    fun insere(@Body aluno: Aluno): Call<Unit>

    @GET("aluno")
    fun lista(): Call<ListaAlunoDTO>
}