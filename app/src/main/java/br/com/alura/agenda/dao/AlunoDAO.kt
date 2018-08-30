package br.com.alura.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.alura.agenda.modelo.Aluno
import java.util.*

class AlunoDAO(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_NAME = "Agenda"
        private val DB_VERSION = 6
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE Alunos (" +
                    "id CHAR(36) PRIMARY KEY NOT NULL, " +
                    "nome TEXT NOT NULL, " +
                    "endereco TEXT, " +
                    "telefone TEXT, " +
                    "site TEXT, " +
                    "nota REAL, " +
                    "foto TEXT);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        when(oldVersion){
            1,2 -> {
                val sql = "ALTER TABLE Alunos ADD COLUMN foto TEXT"
                db.execSQL(sql)
            }
            3 -> {
                val criaTabelaTemporaria =
                        "CREATE TABLE Alunos_tmp (" +
                            "id CHAR(36) PRIMARY KEY NOT NULL, " +
                            "nome TEXT NOT NULL, " +
                            "endereco TEXT, " +
                            "telefone TEXT, " +
                            "site TEXT, " +
                            "nota REAL, " +
                            "foto TEXT);"
                db.execSQL(criaTabelaTemporaria)

                val copiaTabelaAlunosParaTabelaTemporaria =
                        "INSERT INTO Alunos_tmp " +
                            "(id, nome, endereco, telefone, site, nota, foto) "+
                            "SELECT id, nome, endereco, telefone, site, nota, foto " +
                            "FROM Alunos"
                db.execSQL(copiaTabelaAlunosParaTabelaTemporaria)

                val removeTabelaAntiga = "DROP TABLE Alunos"
                db.execSQL(removeTabelaAntiga)

                val renomeiaTabelaTemporaria = "ALTER TABLE Alunos_tmp RENAME TO Alunos"
                db.execSQL(renomeiaTabelaTemporaria)
            }
            4 -> {
                val select = "SELECT * FROM Alunos;"
                val cursor = db.rawQuery(select, null)
                val alunos = populaAlunos(cursor)
                cursor.close()
                val update = "UPDATE Alunos SET id=? WHERE id=?"
                alunos.forEach { aluno ->
                    db.execSQL(update, arrayOf(geraUUID(), aluno.id))
                }
            }
            5 -> {
                val delete = "DELETE FROM Alunos WHERE id = null"
                db.execSQL(delete)
            }
        }
    }

    private fun geraUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun insere(aluno: Aluno): Aluno {
        val db = writableDatabase

        val id: String = if(aluno.id == null) geraUUID() else aluno.id
        val dados = pegaDadosDoAluno(id, aluno)

        db.insert("Alunos", null, dados)

        return aluno.copy(id = id)
    }

    fun buscaAlunos(): List<Aluno> {
        val sql = "SELECT * FROM Alunos;"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)
        val alunos = populaAlunos(cursor)
        cursor.close()

        return alunos

    }

    private fun populaAlunos(c: Cursor): ArrayList<Aluno> {
        val alunos = ArrayList<Aluno>()
        while (c.moveToNext()) {
            val id = c.getString(c.getColumnIndex("id"))
            val nome = c.getString(c.getColumnIndex("nome"))
            val endereco = c.getString(c.getColumnIndex("endereco"))
            val telefone = c.getString(c.getColumnIndex("telefone"))
            val site = c.getString(c.getColumnIndex("site"))
            val nota = c.getDouble(c.getColumnIndex("nota"))
            val foto = c.getString(c.getColumnIndex("foto"))
            val aluno = Aluno(id, nome, endereco, telefone, site, nota, foto)
            alunos.add(aluno)
        }
        return alunos
    }

    fun remover(aluno: Aluno) {
        val db = writableDatabase

        val params = arrayOf(aluno.id)
        db.delete("Alunos", "id = ?", params)
    }

    fun altera(id: String, aluno: Aluno): Aluno {
        val db = writableDatabase

        val dados = pegaDadosDoAluno(id, aluno)

        val params = arrayOf(id.toString())
        db.update("Alunos", dados, "id = ?", params)

        return aluno
    }

    fun existeAlunoCom(telefone: String): Boolean {
        val db = readableDatabase
        val c = db.rawQuery("SELECT id FROM Alunos WHERE telefone = ?", arrayOf(telefone))
        val quantidade = c.count
        c.close()
        return quantidade > 0
    }

    fun existe(aluno: Aluno): Boolean {
        val db = readableDatabase
        val c = db.rawQuery("SELECT id FROM Alunos WHERE id = ?", arrayOf(aluno.id))
        val quantidade = c.count
        c.close()
        return quantidade > 0
    }

    private fun pegaDadosDoAluno(id: String, aluno: Aluno): ContentValues {
        val dados = ContentValues()
        dados.put("id", id)
        dados.put("nome", aluno.nome)
        dados.put("endereco", aluno.endereco)
        dados.put("telefone", aluno.telefone)
        dados.put("site", aluno.site)
        dados.put("nota", aluno.nota)
        dados.put("foto", aluno.foto)
        return dados
    }

    fun sincroniza(alunos: List<Aluno>) {
        alunos.forEach { aluno ->
            if(existe(aluno)){
                altera(aluno.id!!, aluno)
            } else {
                insere(aluno)
            }
        }
    }
}