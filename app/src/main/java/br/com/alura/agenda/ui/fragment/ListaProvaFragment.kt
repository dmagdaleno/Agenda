package br.com.alura.agenda.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.alura.agenda.R
import br.com.alura.agenda.modelo.Prova
import br.com.alura.agenda.ui.activity.ProvaActivity
import kotlinx.android.synthetic.main.fragment_lista_prova.view.*

class ListaProvaFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_lista_prova, container, false)

        val topicos1 = listOf("Sujeito", "Objeto direto", "Objeto indireto")
        val prova1 = Prova("Portugues", "25/05/2016", topicos1)

        val topicos2 = listOf("Equacoes de segundo grau", "Trigonometria")
        val prova2 = Prova("Matematica", "27/05/2016", topicos2)

        val provas = listOf(prova1, prova2)

        val adapter = ArrayAdapter<Prova>(context, android.R.layout.simple_list_item_1, provas)

        view.listaProva.adapter = adapter

        view.listaProva.setOnItemClickListener { parent, _, posicao, _ ->
            val prova: Prova = parent.getItemAtPosition(posicao) as Prova
            val intent = Intent(context, ProvaActivity::class.java)
            intent.putExtra("prova", prova)
            startActivity(intent)
        }

        return view
    }
}