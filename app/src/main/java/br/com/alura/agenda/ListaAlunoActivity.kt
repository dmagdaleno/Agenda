package br.com.alura.agenda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lista_aluno.*

class ListaAlunoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_aluno)

        val alunos = arrayOf("Daniel", "Paulo", "Camila", "Fernando", "Roberta", "Joana",
                "Renato", "Marcos", "Natanael", "Fernanda", "Julia", "Ana", "Lucio", "Rogério")

        val adapter = ArrayAdapter < String >(this, android.R.layout.simple_list_item_1, alunos)

        listaAluno.adapter = adapter

        botaoSalvar.setOnClickListener{ openForm(this) }
    }

    fun openForm(ctx: Context) {
        val intent = Intent(ctx, FormularioAlunoActivity::class.java)
        startActivity(intent)
    }
}
