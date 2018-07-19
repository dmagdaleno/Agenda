package br.com.alura.agenda.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import br.com.alura.agenda.R
import br.com.alura.agenda.modelo.Prova
import kotlinx.android.synthetic.main.activity_prova.*

class ProvaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prova)

        val prova = intent.getSerializableExtra("prova") as Prova?
        prova?.let {
            provaMateria.text = it.materia
            provaData.text = it.data

            val adapter = ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, it.topicos)

            provaListaTopico.adapter = adapter
        }
    }
}
