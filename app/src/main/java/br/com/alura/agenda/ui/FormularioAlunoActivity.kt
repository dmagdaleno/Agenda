package br.com.alura.agenda.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import br.com.alura.agenda.R
import br.com.alura.agenda.dao.AlunoDAO
import br.com.alura.agenda.modelo.Aluno
import br.com.alura.agenda.modelo.RequestCode
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
                dirFoto?.let { exibeFoto(it) }
            }
        }
    }

    private fun exibeFoto(caminhoFoto: String) {
        val bitmap: Bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeFile(caminhoFoto), 300, 300, true)

        imgFoto.setImageBitmap(bitmap)
        imgFoto.scaleType = ImageView.ScaleType.FIT_XY
        imgFoto.tag = caminhoFoto
    }

    private fun preencheFormulario(aluno: Aluno) {
        nome.setText(aluno.nome)
        endereco.setText(aluno.endereco)
        telefone.setText(aluno.telefone)
        site.setText(aluno.site)
        nota.progress = aluno.nota.toInt()
        aluno.foto?.let { exibeFoto(it) }
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
            foto = imgFoto.tag as String)

}

