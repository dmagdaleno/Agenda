package br.com.alura.agenda.retrofit

import br.com.alura.agenda.retrofit.service.AlunoService
import br.com.alura.agenda.retrofit.service.DispositivoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitInicializador {

    private val retrofit: Retrofit

    val alunoService: AlunoService
        get() = retrofit.create(AlunoService::class.java)

    val dispositivoService: DispositivoService
        get() = retrofit.create(DispositivoService::class.java)

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.15.17:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient)
                .build()
    }
}