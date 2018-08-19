package br.com.alura.agenda.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import br.com.alura.agenda.R
import br.com.alura.agenda.api.EnviaAlunoTask
import br.com.alura.agenda.dao.AlunoDAO
import br.com.alura.agenda.modelo.Aluno
import br.com.alura.agenda.modelo.RequestCode
import br.com.alura.agenda.ui.adapter.AlunoAdapter
import kotlinx.android.synthetic.main.activity_lista_aluno.*

class ListaAlunoActivity : AppCompatActivity() {

    private var ultimoTelefone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_aluno)

        listaAluno.setOnItemClickListener { _, _, position, _ ->
            val aluno: Aluno = pegaAlunoDaLista(position)
            abrirFormulario(this, aluno)
        }

        botaoSalvar.setOnClickListener { abrirFormulario(this, null) }

        registerForContextMenu(listaAluno)
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

        val adapter = AlunoAdapter(this, alunos)

        listaAluno.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lista_aluno, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_enviar_notas -> {
                EnviaAlunoTask(this).execute()
            }
            R.id.menu_carregar_provas -> {
                val intent = Intent(this, ListaProvaActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_mapa -> {
                val intent = Intent(this, MapaActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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

        val itemLigar = menu.add("Ligar")
        itemLigar.setOnMenuItemClickListener { ligarPara(aluno) }

        val itemSMS = menu.add("Enviar SMS")
        val uri = "sms:${aluno.telefone}"
        itemSMS.setOnMenuItemClickListener { abrirView(Uri.parse(uri), it) }

        val itemMapa = menu.add("Visualizar no mapa")
        val uriMapa = "geo:0,0?q=${aluno.endereco}"
        itemMapa.setOnMenuItemClickListener { abrirView(Uri.parse(uriMapa), it) }

        val itemSite: MenuItem = menu.add("Abrir site")
        itemSite.setOnMenuItemClickListener{ abrirView(httpUrl(aluno.site), it) }

        val remover: MenuItem = menu.add("Remover")
        remover.setOnMenuItemClickListener{ remover(aluno) }
    }

    private fun ligarPara(aluno: Aluno): Boolean {
        val permissao = Manifest.permission.CALL_PHONE
        if (appTemPermissaoPara(permissao)){
            efetuaLigacao(aluno.telefone)
        } else {
            val permissoes = arrayOf(permissao)
            ultimoTelefone = aluno.telefone
            ActivityCompat.requestPermissions(this, permissoes, RequestCode.CALL)
        }

        return false
    }

    private fun efetuaLigacao(telefone: String) {
        intent = Intent(Intent.ACTION_CALL)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse("tel:$telefone")
        startActivity(intent)
    }

    private fun appTemPermissaoPara(permission: String) =
            (ActivityCompat.checkSelfPermission(this, permission)
                    == PackageManager.PERMISSION_GRANTED)

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

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == RequestCode.CALL) efetuaLigacao(ultimoTelefone)
    }
}
