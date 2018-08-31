package br.com.alura.agenda.funcoes

fun <T> Collection<T>.joinToString(
        separador: String = ", ",
        prefixo: String = "[ ",
        sufixo: String = " ]"
): String {
    val resultado = StringBuilder(prefixo)

    for((index, element) in this.withIndex()) {
        if(index > 0)
            resultado.append(separador)

        resultado.append(element)
    }

    resultado.append(sufixo)

    return resultado.toString()
}