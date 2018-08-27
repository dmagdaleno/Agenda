package br.com.alura.agenda.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsereAlunoCallback: Callback<Unit> {
    override fun onResponse(call: Call<Unit>?, response: Response<Unit>?) {
        Log.i("Sucesso", "Requisição realizada com sucesso")
    }

    override fun onFailure(call: Call<Unit>?, t: Throwable?) {
        Log.e("Falha", "Falha na requisição", t)
    }
}