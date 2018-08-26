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

    fun insere(json: String): String {
        val endereco = "http://192.168.15.17:8080/api/aluno"
        return post(endereco, json)
    }

    private fun post(endereco: String, json: String): String {
        val url = URL(endereco)
        val connection = url.openConnection() as HttpURLConnection

        connection.addRequestProperty("Content-type", "application/json")
        connection.addRequestProperty("Accept", "application/json")
        connection.doOutput = true

        val output = PrintStream(connection.getOutputStream())
        output.println(json)

        val scanner = Scanner(connection.getInputStream())
        return scanner.next()
    }
}