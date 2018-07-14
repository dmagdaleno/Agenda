package br.com.alura.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.alura.modelo.Aluno
import java.util.ArrayList

class AlunoDAO(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_NAME = "Agenda"
        private val DB_VERSION = 3
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE Alunos (" +
                    "id INTEGER PRIMARY KEY, " +
                    "nome TEXT NOT NULL, " +
                    "endereco TEXT, " +
                    "telefone TEXT, " +
                    "site TEXT, " +
                    "nota REAL, " +
                    "foto TEXT);"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql: String =
            when(oldVersion){
                2,3 -> "ALTER TABLE Alunos ADD COLUMN foto TEXT"
                else -> ""
            }

        db?.execSQL(sql)
    }

    fun insere(aluno: Aluno) {
        val db = writableDatabase

        val dados = pegaDadosDoAluno(aluno)

        db.insert("Alunos", null, dados)
    }

    fun buscaAlunos(): List<Aluno> {
        val sql = "SELECT * FROM Alunos;"
        val db = readableDatabase
        val c = db.rawQuery(sql, null)

        val alunos = ArrayList<Aluno>()
        while (c.moveToNext()) {
            val id = c.getLong(c.getColumnIndex("id"))
            val nome = c.getString(c.getColumnIndex("nome"))
            val endereco = c.getString(c.getColumnIndex("endereco"))
            val telefone = c.getString(c.getColumnIndex("telefone"))
            val site = c.getString(c.getColumnIndex("site"))
            val nota = c.getDouble(c.getColumnIndex("nota"))
            val foto = c.getString(c.getColumnIndex("foto"))
            val aluno = Aluno(id, nome, endereco, telefone, site, nota, foto)
            alunos.add(aluno)
        }
        c.close()

        return alunos

    }

    fun remover(aluno: Aluno) {
        val db = writableDatabase

        val params = arrayOf(aluno.id.toString())
        db.delete("Alunos", "id = ?", params)
    }

    fun altera(id: Long, aluno: Aluno) {
        val db = writableDatabase

        val dados = pegaDadosDoAluno(aluno)

        val params = arrayOf(id.toString())
        db.update("Alunos", dados, "id = ?", params)
    }

    private fun pegaDadosDoAluno(aluno: Aluno): ContentValues {
        val dados = ContentValues()
        dados.put("nome", aluno.nome)
        dados.put("endereco", aluno.endereco)
        dados.put("telefone", aluno.telefone)
        dados.put("site", aluno.site)
        dados.put("nota", aluno.nota)
        dados.put("foto", aluno.foto)
        return dados
    }
}