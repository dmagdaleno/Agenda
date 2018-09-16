package br.com.alura.agenda.retrofit.service

import br.com.alura.agenda.modelo.Aluno
import br.com.alura.agenda.retrofit.service.dto.ListaAlunoDTO
import retrofit2.Call
import retrofit2.http.*

interface AlunoService {

    @POST("aluno")
    fun insere(@Body aluno: Aluno): Call<ListaAlunoDTO>

    @GET("aluno")
    fun lista(): Call<ListaAlunoDTO>

    @DELETE("aluno/{id}")
    fun remove(@Path("id") id: String): Call<ListaAlunoDTO>
}