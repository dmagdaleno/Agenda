package br.com.alura.agenda.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AgendaMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val mensagem = remoteMessage?.data
        Log.d("FirebaseMessage", "mensagem: $mensagem")
    }
}