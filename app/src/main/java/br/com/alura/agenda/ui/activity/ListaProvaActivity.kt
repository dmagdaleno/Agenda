package br.com.alura.agenda.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.alura.agenda.R
import br.com.alura.agenda.ui.fragment.ListaProvaFragment

class ListaProvaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_prova)

        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.frame_principal, ListaProvaFragment())
        tx.commit()
    }
}
