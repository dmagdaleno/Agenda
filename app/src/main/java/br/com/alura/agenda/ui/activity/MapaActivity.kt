package br.com.alura.agenda.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.alura.agenda.R
import br.com.alura.agenda.ui.fragment.MapaFragment

class MapaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.frame_mapa, MapaFragment())
        tx.commit()
    }
}
