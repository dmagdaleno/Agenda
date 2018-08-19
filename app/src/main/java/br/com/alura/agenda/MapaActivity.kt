package br.com.alura.agenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.SupportMapFragment

class MapaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.frame_mapa, SupportMapFragment())
        tx.commit()
    }
}
