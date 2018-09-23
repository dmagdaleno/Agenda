package br.com.alura.agenda.preferences

import android.content.Context

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


}
