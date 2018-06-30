package br.com.alura.agenda

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alura.modelo.Aluno
import kotlinx.android.synthetic.main.activity_formulario_aluno.*
import br.com.alura.dao.AlunoDAO



class FormularioAlunoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aluno)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_aluno, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuFormularioAlunoOk -> salvaAluno()
        }
        return super.onOptionsItemSelected(item)
    }

    fun salvaAluno() {
        var aluno: Aluno = constroiAluno()
        val dao = AlunoDAO(this)
        dao.insere(aluno)
        dao.close()

        Toast.makeText(this, "Aluno " + aluno.nome + " salvo", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun constroiAluno(): Aluno {
        var aluno = Aluno()
        aluno.nome = nome.text.toString()
        aluno.endereco = endereco.text.toString()
        aluno.telefone = telefone.text.toString()
        aluno.site = site.text.toString()
        aluno.nota = nota.progress.toDouble()

        return aluno
    }

}

