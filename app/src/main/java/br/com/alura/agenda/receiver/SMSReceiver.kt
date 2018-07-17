package br.com.alura.agenda.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast
import br.com.alura.agenda.dao.AlunoDAO

class SMSReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val telefone = recuperaTelefoneDoSms(intent)

        val dao = AlunoDAO(context)

        if(dao.existeAlunoCom(telefone)){
            Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_SHORT).show()
        }

        dao.close()
    }

    private fun recuperaTelefoneDoSms(intent: Intent): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pdus = intent.getSerializableExtra("pdus") as Array<Any>
            val pdu = pdus[0] as ByteArray
            val formato = intent.getSerializableExtra("format") as String
            val sms: SmsMessage = SmsMessage.createFromPdu(pdu, formato)
            sms.displayOriginatingAddress as String
        }
        else {
            ""
        }
}