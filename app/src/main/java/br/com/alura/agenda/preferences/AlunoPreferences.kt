package br.com.alura.agenda.preferences

import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat

class AlunoPreferences(val context: Context) {

    companion object {
        private const val CHAVE_PREFERENCES = "br.com.alura.agenda.preferences.AlunoPreferences"
        private const val CHAVE_VERSAO = "versao"
    }

    val versao: String
        get() {
            val preferences = sharedPreferences()
            return preferences.getString(CHAVE_VERSAO, "")
        }

    val existeVersao: Boolean
        get() {
            return versao.isNotEmpty()
        }

    fun salvaVersao(versao: String) {
        val preferences = sharedPreferences()
        val editor = preferences.edit()
        editor.putString(CHAVE_VERSAO, versao)
        editor.apply()
    }

    private fun sharedPreferences() =
            context.getSharedPreferences(CHAVE_PREFERENCES, Context.MODE_PRIVATE)

    fun comparaComVersaoInterna(versaoExterna: String): Boolean {
        if(!existeVersao)
            return true

        try{
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val dataVersaoExterna = format.parse(versaoExterna)
            val dataVersaoAtual = format.parse(versao)
            return dataVersaoExterna.after(dataVersaoAtual)
        } catch(e: ParseException) {
            e.printStackTrace()
        }

        return false
    }


}
