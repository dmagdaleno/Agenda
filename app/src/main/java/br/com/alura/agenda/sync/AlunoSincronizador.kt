package br.com.alura.agenda.sync

import android.content.Context
import android.util.Log
import br.com.alura.agenda.dao.AlunoDAO
import br.com.alura.agenda.event.AtualizaAlunosEvent
import br.com.alura.agenda.preferences.AlunoPreferences
import br.com.alura.agenda.retrofit.RetrofitInicializador
import br.com.alura.agenda.retrofit.service.dto.ListaAlunoDTO
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlunoSincronizador(val context: Context) {

    private val eventBus = EventBus.getDefault()
    private val preferences = AlunoPreferences(context)

    fun sincroniza() {
        if(preferences.existeVersao)
            buscaNovos()
        else
            buscaTodos()
    }

    private fun buscaNovos() {
        val call = RetrofitInicializador().alunoService.listaNovos(preferences.versao)
        call.enqueue(atualizaAlunos())
    }

    private fun buscaTodos(){
        val call = RetrofitInicializador().alunoService.lista()
        call.enqueue(atualizaAlunos())
    }

    private fun atualizaAlunos() = object : Callback<ListaAlunoDTO> {
        override fun onResponse(call: Call<ListaAlunoDTO>?, response: Response<ListaAlunoDTO>?) {
            response?.body()?.let {
                val versao = it.momentoDaUltimaModificacao

                preferences.salvaVersao(versao)

                val alunos = it.alunos
                if (alunos.isNotEmpty()) {
                    val dao = AlunoDAO(context)
                    dao.sincroniza(alunos)
                    dao.close()
                }
            }
            eventBus.post(AtualizaAlunosEvent())
        }

        override fun onFailure(call: Call<ListaAlunoDTO>?, t: Throwable?) {
            Log.e("AlunoSincronizador", "Erro ao recuperar alunos", t)
            eventBus.post(AtualizaAlunosEvent())
        }
    }
}