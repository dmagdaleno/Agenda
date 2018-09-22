package br.com.alura.agenda.firebase

import android.util.Log
import br.com.alura.agenda.dao.AlunoDAO
import br.com.alura.agenda.event.AtualizaAlunosEvent
import br.com.alura.agenda.retrofit.service.dto.ListaAlunoDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus

class AgendaMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        remoteMessage?.let {
            val mensagem = it.data
            Log.d("FirebaseMessage", "mensagem: $mensagem")

            atualizaListaAlunos(mensagem)
        }
    }

    private fun atualizaListaAlunos(mensagem: Map<String, String>) {
        val chave = "alunoSync"
        if(mensagem.containsKey(chave)){
            val json = mensagem[chave]
            val mapper = ObjectMapper()
            val alunosDTO = mapper.readValue(json, ListaAlunoDTO::class.java)

            if (alunosDTO != null) {
                salva(alunosDTO)
                EventBus.getDefault().post(AtualizaAlunosEvent())
            }
        }
    }

    private fun salva(alunosDTO: ListaAlunoDTO) {
        val dao = AlunoDAO(this)
        dao.sincroniza(alunosDTO.alunos)
        dao.close()
    }
}