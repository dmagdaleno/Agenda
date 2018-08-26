package br.com.alura.agenda.task

import android.os.AsyncTask
import br.com.alura.agenda.api.AlunoConverter
import br.com.alura.agenda.api.WebClient
import br.com.alura.agenda.modelo.Aluno

class InsereAlunoTask(val aluno: Aluno): AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg u: Unit?) {
        val json = AlunoConverter().paraJson(aluno)
        WebClient().insere(json)
    }

}
