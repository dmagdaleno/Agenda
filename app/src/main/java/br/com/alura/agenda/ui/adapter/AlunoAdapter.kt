package br.com.alura.agenda.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import br.com.alura.agenda.R
import br.com.alura.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.list_item_aluno.view.*

class AlunoAdapter(
        private val context: Context,
        private val alunos: List<Aluno> ) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
                        .inflate(R.layout.list_item_aluno, parent, false)

        val aluno = alunos[position]
        view.itemNome.text = aluno.nome
        view.itemTelefone.text = aluno.telefone

        view.itemEndereco?.let{
            it.text = aluno.endereco
        }
        view.itemSite?.let{
            it.text = aluno.site
        }
        exibeFoto(view, aluno)

        return view
    }

    private fun exibeFoto(view: View, aluno: Aluno) {
        if(aluno.foto != null) {
            val bitmap = BitmapFactory.decodeFile(aluno.foto)
            val bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true)

            view.itemFoto.setImageBitmap(bitmapReduzido)
            view.itemFoto.scaleType = ImageView.ScaleType.FIT_XY
            view.itemFoto.tag = aluno.foto
        }
    }

    override fun getItem(position: Int) = alunos[position]

    override fun getItemId(position: Int) = alunos[position].id ?: 0

    override fun getCount() = alunos.size
}