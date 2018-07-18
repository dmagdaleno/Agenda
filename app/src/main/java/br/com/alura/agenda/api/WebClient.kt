package br.com.alura.agenda.api

import java.io.PrintStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class WebClient {

    fun post(json: String): String {
        val url = URL("https://www.caelum.com.br/mobile")
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