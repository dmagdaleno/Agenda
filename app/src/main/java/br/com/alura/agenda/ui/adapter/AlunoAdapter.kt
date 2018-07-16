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
        exibeFoto(view, aluno)

        return view
    }

    private fun exibeFoto(view: View, aluno: Aluno) {
        aluno.foto?.let {

            val bitmap: Bitmap = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeFile(it), 100, 100, true)

            view.itemFoto.setImageBitmap(bitmap)
            view.itemFoto.scaleType = ImageView.ScaleType.FIT_XY
            view.itemFoto.tag = it
        }
    }

    override fun getItem(position: Int) = alunos[position]

    override fun getItemId(position: Int) = alunos[position].id ?: 0

    override fun getCount() = alunos.size
}