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


    private var aluno: Aluno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aluno)

        val extra = intent.getSerializableExtra("aluno")
        if(extra != null && extra is Aluno) {
            aluno = extra
            preencheFormulario(aluno!!)
        }
    }

    private fun preencheFormulario(aluno: Aluno) {
        nome.setText(aluno.nome)
        endereco.setText(aluno.endereco)
        telefone.setText(aluno.telefone)
        site.setText(aluno.site)
        nota.progress = aluno.nota.toInt()
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
        val aluno: Aluno = constroiAluno()
        val dao = AlunoDAO(this)
        if (aluno.id != null)  {
            dao.altera(aluno)
        } else {
            dao.insere(aluno)
        }
        dao.close()

        Toast.makeText(this, "Aluno " + aluno.nome + " salvo", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun constroiAluno(): Aluno {
        val fnome     = nome.text    .toString()
        val fendereco = endereco.text.toString()
        val ftelefone = telefone.text.toString()
        val fsite     = site.text    .toString()
        val fnota     = nota.progress.toDouble()

        if(aluno != null) {
            aluno!!.nome = fnome
            aluno!!.endereco = fendereco
            aluno!!.telefone = ftelefone
            aluno!!.site = fsite
            aluno!!.nota = fnota
        } else {
            aluno = Aluno(null, fnome, fendereco, ftelefone, fsite, fnota)
        }

        return aluno!!
    }

}

