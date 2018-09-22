package br.com.alura.agenda.firebase

import android.util.Log
import br.com.alura.agenda.retrofit.RetrofitInicializador
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaInstanceIDService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("FirebaseService", "Refreshed token: $refreshedToken")

        if(refreshedToken != null)
            enviaTokenParaServidor(refreshedToken)
    }

    private fun enviaTokenParaServidor(token: String) {
        Log.d("FirebaseService", ">> Enviando token $token para o servidor")

        val call = RetrofitInicializador().dispositivoService.enviaToken(token)
        call.enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>?, t: Throwable?) {
                Log.e("FirebaseService", "<< Falha ao enviar token $token", t)
            }

            override fun onResponse(call: Call<Unit>?, response: Response<Unit>?) {
                Log.i("FirebaseService", "<< Sucesso ao enviar token $token")
            }

        })
    }
}