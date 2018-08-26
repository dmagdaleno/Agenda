package br.com.alura.agenda.api

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import br.com.alura.agenda.dao.AlunoDAO

class EnviaAlunoTask(val context: Context): AsyncTask<Unit, Unit, String>() {

    private var dialog: ProgressDialog? = null

    override fun onPreExecute() {
        dialog = ProgressDialog.show(
                context,
                "Aguarde",
                "Enviando alunos...",
                true,
                true)
    }

    override fun doInBackground(vararg u: Unit): String {
        val dao = AlunoDAO(context)
        val alunos = dao.buscaAlunos()
        dao.close()

        val conversor = AlunoConverter()
        val json = conversor.paraJson(alunos)

        val webClient = WebClient()
        return webClient.envia(json)
    }

    override fun onPostExecute(result: String) {
        dialog?.dismiss()
        Toast.makeText(context, "Notas enviadas: $result", Toast.LENGTH_LONG).show()
    }
}