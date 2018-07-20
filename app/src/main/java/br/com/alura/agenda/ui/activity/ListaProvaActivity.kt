package br.com.alura.agenda.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.alura.agenda.R
import br.com.alura.agenda.funcoes.isLandscapeMode
import br.com.alura.agenda.ui.fragment.ListaProvaFragment
import br.com.alura.agenda.ui.fragment.ProvaFragment
import br.com.alura.agenda.modelo.Prova



class ListaProvaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_prova)

        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.frame_principal, ListaProvaFragment())
        if(isLandscapeMode())
            tx.replace(R.id.frame_secundario, ProvaFragment())

        tx.commit()
    }

    fun selecionaProva(prova: Prova) {
        val manager = supportFragmentManager
        if(isLandscapeMode()){
            val provaFragment = manager.findFragmentById(R.id.frame_secundario) as ProvaFragment
            provaFragment.populaCamposCom(prova)
        }
        else {
            val tx = manager.beginTransaction()

            val provaFragment = ProvaFragment()
            val parametros = Bundle()
            parametros.putSerializable("prova", prova)
            provaFragment.setArguments(parametros)

            tx.replace(R.id.frame_principal, provaFragment)
            tx.commit()
        }
    }

}
