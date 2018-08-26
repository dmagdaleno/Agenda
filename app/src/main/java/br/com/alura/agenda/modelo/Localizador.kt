package br.com.alura.agenda.modelo

import android.content.Context
import android.location.Location
import android.os.Bundle
import br.com.alura.agenda.ui.fragment.MapaFragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class Localizador(
        val context: Context,
        val mapa: MapaFragment
    ) : ConnectionCallbacks, LocationListener {

    val client = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .build()

    init {
        client.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        val request = LocationRequest()
        //request.smallestDisplacement = 50f
        request.interval = 1000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this)
    }

    override fun onConnectionSuspended(i: Int) {
        TODO("not implemented")
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            val coordenada = LatLng(it.latitude, it.longitude)
            mapa.centralizaEm(coordenada)
        }
    }
}
