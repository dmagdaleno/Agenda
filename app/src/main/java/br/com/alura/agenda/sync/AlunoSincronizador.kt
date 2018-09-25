package br.com.alura.agenda.sync

import android.content.Context
import android.util.Log
import br.com.alura.agenda.dao.AlunoDAO
import br.com.alura.agenda.event.AtualizaAlunosEvent
import br.com.alura.agenda.modelo.Aluno
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
    private val service = RetrofitInicializador().alunoService

    fun sincroniza() {
        if(preferences.existeVersao)
            buscaNovos()
        else
            buscaTodos()
    }


    private fun buscaNovos() {
        val call = service.listaNovos(preferences.versao)
        call.enqueue(atualizaAlunos())
    }

    private fun buscaTodos(){
        val call = service.lista()
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
            sincronizaAlunosLocais()
        }

        override fun onFailure(call: Call<ListaAlunoDTO>?, t: Throwable?) {
            Log.e("AlunoSincronizador", "Erro ao recuperar alunos", t)
            eventBus.post(AtualizaAlunosEvent())
        }
    }

    private fun sincronizaAlunosLocais(){
        val dao = AlunoDAO(context)
        val alunosNaoSincronizados = dao.buscaAlunosNaoSincronizados()
        val call = service.atualiza(alunosNaoSincronizados)
        call.enqueue(object: Callback<ListaAlunoDTO>{
            override fun onResponse(call: Call<ListaAlunoDTO>?, response: Response<ListaAlunoDTO>?) {
                response?.body()?.alunos?.let {
                    dao.sincroniza(it)
                }
                dao.close()
            }

            override fun onFailure(call: Call<ListaAlunoDTO>?, t: Throwable?) {
                Log.e("AlunoSincronizador", "Erro ao enviar alunos", t)
                dao.close()
            }
        })
    }

    fun removeDoServidor(aluno: Aluno) {
        aluno.id?.let {
            val call = service.remove(it)
            call.enqueue(object : Callback<ListaAlunoDTO> {
                override fun onResponse(call: Call<ListaAlunoDTO>?, response: Response<ListaAlunoDTO>?) {
                    val dao = AlunoDAO(context)
                    dao.remove(aluno)
                    dao.close()
                    Log.d("AlunoSincronizador", "Aluno ${aluno.nome} removido com sucesso")
                }

                override fun onFailure(call: Call<ListaAlunoDTO>?, t: Throwable?) {
                    Log.e("AlunoSincronizador", "Erro ao remover ${aluno.nome}", t)
                }
            })
        }
    }
}