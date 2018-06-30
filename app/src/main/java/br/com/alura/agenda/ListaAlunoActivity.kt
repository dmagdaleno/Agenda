package br.com.alura.agenda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lista_aluno.*
import br.com.alura.modelo.Aluno
import br.com.alura.dao.AlunoDAO



class ListaAlunoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_aluno)

        botaoSalvar.setOnClickListener{ openForm(this) }
    }

    override fun onResume() {
        super.onResume()
        carregaListaAlunos()
    }

    private fun carregaListaAlunos() {
        val dao = AlunoDAO(this)
        val alunos = dao.buscaAlunos()
        System.out.println(alunos)
        dao.close ()

        val adapter = ArrayAdapter<Aluno>(
                this,
                android.R.layout.simple_list_item_1,
                alunos)

        listaAluno.adapter = adapter
    }

    fun openForm(ctx: Context) {
        val intent = Intent(ctx, FormularioAlunoActivity::class.java)
        startActivity(intent)
    }
}
