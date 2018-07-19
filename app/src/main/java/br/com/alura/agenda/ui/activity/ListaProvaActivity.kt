package br.com.alura.agenda.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import br.com.alura.agenda.R
import br.com.alura.agenda.modelo.Prova
import kotlinx.android.synthetic.main.activity_lista_prova.*

class ListaProvaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_prova)

        val topicos1 = listOf("Sujeito", "Objeto direto", "Objeto indireto")
        val prova1 = Prova("Portugues", "25/05/2016", topicos1)

        val topicos2 = listOf("Equacoes de segundo grau", "Trigonometria")
        val prova2 = Prova("Matematica", "27/05/2016", topicos2)

        val provas = listOf(prova1, prova2)

        val adapter = ArrayAdapter<Prova>(
                this, android.R.layout.simple_list_item_1, provas)

        listaProva.adapter = adapter
        
        listaProva.setOnItemClickListener { parent, _, posicao, _ ->
            val prova: Prova = parent.getItemAtPosition(posicao) as Prova
            val intent = Intent(this, ProvaActivity::class.java)
            intent.putExtra("prova", prova)
            startActivity(intent)
        }
    }
}
