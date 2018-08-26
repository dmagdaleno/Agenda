package br.com.alura.agenda.ui.fragment

import android.location.Geocoder
import android.os.Bundle
import br.com.alura.agenda.dao.AlunoDAO
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment: SupportMapFragment(), OnMapReadyCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val endereco = "R. Ibitirama, 1409 - Vila Prudente, São Paulo - SP"
        val posicao = recuperaPosicaoPelo(endereco)
        posicao?.let {
            val update = CameraUpdateFactory.newLatLngZoom(posicao, 17f)
            googleMap?.moveCamera(update)
        }

        val dao = AlunoDAO(context!!)
        val alunos = dao.buscaAlunos()
        alunos.forEach{ aluno ->
            val posicao = recuperaPosicaoPelo(aluno.endereco)
            posicao?.let {
                val marcador = MarkerOptions()
                marcador.position(it)
                marcador.title(aluno.nome)
                marcador.snippet(aluno.nota.toString())
                googleMap?.addMarker(marcador)
            }
        }
        dao.close()
    }

    private fun recuperaPosicaoPelo(endereco: String): LatLng? {
        val geocoder = Geocoder(context)
        val resultados = geocoder.getFromLocationName(endereco, 1)
        if(!resultados.isEmpty())
            return LatLng(resultados[0].latitude, resultados[0].longitude)

        return null
    }

}