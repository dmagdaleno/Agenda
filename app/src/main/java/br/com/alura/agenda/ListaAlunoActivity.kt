package br.com.alura.agenda

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.alura.dao.AlunoDAO
import br.com.alura.modelo.Aluno
import kotlinx.android.synthetic.main.activity_lista_aluno.*

class ListaAlunoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_aluno)

        listaAluno.setOnItemClickListener { parent, view, position, id ->
            val aluno: Aluno = pegaAlunoDaLista(position)
            abrirFormulario(this, aluno)
        }

        botaoSalvar.setOnClickListener{ abrirFormulario(this, null) }

        registerForContextMenu(listaAluno)
    }

    private fun toast(mensagem: String, duracao: Int) {
        Toast.makeText(this, mensagem, duracao).show()
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

    fun abrirFormulario(ctx: Context, aluno: Aluno?) {
        val intent = Intent(ctx, FormularioAlunoActivity::class.java)
        intent.putExtra("aluno", aluno)
        startActivity(intent)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo)
        val aluno = alunoSelecionado(menuInfo)

        val itemSite: MenuItem = menu.add("Abrir site")
        itemSite.setOnMenuItemClickListener{ abrirView(httpUrl(aluno.site!!), it) }

        val remover: MenuItem = menu.add("Remover")
        remover.setOnMenuItemClickListener{ remover(aluno) }
    }

    private fun alunoSelecionado(menuInfo: ContextMenu.ContextMenuInfo): Aluno {
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        return pegaAlunoDaLista(info.position)
    }

    fun remover(aluno: Aluno): Boolean {
        val dao = AlunoDAO(this)
        dao.remover(aluno)
        dao.close()

        carregaListaAlunos()

        return false
    }

    fun abrirView(uri: Uri, menuItem: MenuItem): Boolean {
        intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = uri
        menuItem.intent = intent

        return false
    }

    private fun httpUrl(url: String): Uri {
        if(url.startsWith("http://"))
            return Uri.parse(url)

        return Uri.parse("http://$url")
    }


    fun pegaAlunoDaLista(posicao: Int): Aluno{
        val itemNaPosicao = listaAluno.getItemAtPosition(posicao)
        if(itemNaPosicao is Aluno) return itemNaPosicao
        else throw IllegalArgumentException()
    }
}
