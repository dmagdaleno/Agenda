package br.com.alura.agenda.api

import br.com.alura.agenda.modelo.Aluno
import org.json.JSONStringer


class AlunoConverter {

    fun paraJson(alunos: List<Aluno>): String {
        val js = JSONStringer()

        js.`object`().key("list").array().`object`().key("aluno").array()

        for (aluno in alunos) {
            js.`object`()
            js.key("nome").value(aluno.nome)
            js.key("nota").value(aluno.nota)
            js.endObject()
        }
        js.endArray().endObject().endArray().endObject()


        return js.toString()
    }

    fun paraJson(aluno: Aluno): String {
        val js = JSONStringer()

        js.`object`()
            .key("nome").value(aluno.nome)
            .key("endereco").value(aluno.endereco)
            .key("telefone").value(aluno.telefone)
            .key("site").value(aluno.site)
            .key("nota").value(aluno.nota)
            .endObject()

        return js.toString()
    }

}
