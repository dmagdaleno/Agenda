package br.com.alura.agenda

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

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
        Toast.makeText(this, "Aluno salvo", Toast.LENGTH_SHORT).show()
        finish()
    }

}

