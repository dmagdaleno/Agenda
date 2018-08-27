package br.com.alura.agenda.retrofit

import br.com.alura.agenda.retrofit.service.AlunoService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitInicializador {

    val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.17:8080/api/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

    val alunoService: AlunoService
        get() = retrofit.create(AlunoService::class.java)
}