package br.com.alura.agenda.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alura.agenda.R
import br.com.alura.agenda.dao.AlunoDAO
import br.com.alura.agenda.funcoes.carregaFoto
import br.com.alura.agenda.modelo.Aluno
import br.com.alura.agenda.modelo.RequestCode
import br.com.alura.agenda.retrofit.InsereAlunoCallback
import br.com.alura.agenda.retrofit.RetrofitInicializador
import kotlinx.android.synthetic.main.activity_formulario_aluno.*
import java.io.File

class FormularioAlunoActivity : AppCompatActivity() {
    private val aluno: Aluno? by lazy {intent.getSerializableExtra("aluno") as Aluno?}

    private var dirFoto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aluno)

        if(aluno != null)
            preencheFormulario(aluno as Aluno)

        btnFoto.setOnClickListener{ tiraFoto() }
    }

    private fun tiraFoto() {
        dirFoto = "${getExternalFilesDir(null).toString()}/${System.currentTimeMillis()}.jpg"

        val arquivoFoto = File(dirFoto)

        val uri = FileProvider.getUriForFile(this,
                "${applicationContext.packageName}.provider", arquivoFoto)

        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivityForResult(intentCamera, RequestCode.CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.CAMERA){
                exibeFoto(dirFoto)
            }
        }
    }

    private fun exibeFoto(caminhoFoto: String?) {
        if(caminhoFoto != null) {
            val bitmap = carregaFoto(caminhoFoto)
            imgFoto.setImageBitmap(bitmap)
            imgFoto.tag = caminhoFoto
        }
    }

    private fun preencheFormulario(aluno: Aluno) {
        nome.setText(aluno.nome)
        endereco.setText(aluno.endereco)
        telefone.setText(aluno.telefone)
        site.setText(aluno.site)
        nota.progress = aluno.nota.toInt()
        exibeFoto(aluno.foto)
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
            dao.altera(aluno.id, aluno)
        } else {
            dao.insere(aluno)
        }
        dao.close()

        val call = RetrofitInicializador().alunoService.insere(aluno)
        call.enqueue(InsereAlunoCallback())

        Toast.makeText(this, "Aluno ${aluno.nome} salvo", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun constroiAluno() = Aluno(
            id = aluno?.id,
            nome = nome.text.toString(),
            endereco = endereco.text.toString(),
            telefone = telefone.text.toString(),
            site = site.text.toString(),
            nota = nota.progress.toDouble(),
            foto = getImageTag())

    private fun getImageTag() = if (imgFoto.tag != null) imgFoto.tag as String else null

}

