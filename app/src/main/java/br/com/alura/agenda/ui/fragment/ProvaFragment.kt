package br.com.alura.agenda.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import br.com.alura.agenda.R
import br.com.alura.agenda.modelo.Prova
import kotlinx.android.synthetic.main.fragment_prova.view.*

class ProvaFragment : Fragment() {

    private var campoMateria: TextView? = null
    private var campoData: TextView? = null
    private var listaTopicos: ListView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_prova, container, false)
        campoMateria = view.provaMateria
        campoData = view.provaData
        listaTopicos = view.provaListaTopico

        val prova = arguments?.getSerializable("prova") as Prova?
        println(prova)
        prova?.let { populaCamposCom(it) }

        return view
    }

    fun populaCamposCom(prova: Prova) {
        campoMateria?.text = prova.materia
        campoData?.text = prova.data

        val adapter = ArrayAdapter<String>(
                context, android.R.layout.simple_list_item_1, prova.topicos)

        listaTopicos?.adapter = adapter
    }

}
