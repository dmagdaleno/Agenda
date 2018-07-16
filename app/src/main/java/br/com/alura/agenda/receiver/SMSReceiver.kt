package br.com.alura.agenda.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SMSReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_SHORT).show()
    }
}