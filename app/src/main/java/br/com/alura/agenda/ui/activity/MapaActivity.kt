package br.com.alura.agenda.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import br.com.alura.agenda.R
import br.com.alura.agenda.modelo.Localizador
import br.com.alura.agenda.ui.fragment.MapaFragment

class MapaActivity : AppCompatActivity() {

    private val REQUEST_PERMISSOES: Int = 1

    private val mapaFragment = MapaFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        val tx = supportFragmentManager.beginTransaction()
        tx.replace(R.id.frame_mapa, mapaFragment)
        tx.commit()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(possuiPermissaoDeMovimentacao() || possuiPermissaoDeLocalizacao()) {
                val permissoes = arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissoes, REQUEST_PERMISSOES)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_PERMISSOES){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Localizador(this, mapaFragment)
            }
        }
    }

    private fun possuiPermissaoDeMovimentacao() =
            ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED

    private fun possuiPermissaoDeLocalizacao() =
            ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
}
