package br.com.alura.agenda.api

import java.io.PrintStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class WebClient {

    fun envia(json: String): String {
        val endereco = "https://www.caelum.com.br/mobile"
        return post(endereco, json)
    }

    private fun post(endereco: String, json: String): String {
        try{
            val url = URL(endereco)
            val connection = url.openConnection() as HttpURLConnection

            connection.addRequestProperty("Content-type", "application/json")
            connection.addRequestProperty("Accept", "application/json")
            connection.doOutput = true

            val output = PrintStream(connection.getOutputStream())
            output.println(json)

            val scanner = Scanner(connection.getInputStream())
            return scanner.next()
        } catch (e: Exception) {
            System.out.println("Erro ao conectar com o servidor: ${e.message}")
        }
        return ""
    }
}