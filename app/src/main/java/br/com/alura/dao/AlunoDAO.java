package br.com.alura.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "Agenda";
    private static final int DB_VERSION = 2;

    public AlunoDAO (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(@NotNull Aluno aluno)  {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos", null, dados );
    }

    public List<Aluno> buscaAlunos()  {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()) {
            Long id = c.getLong(c.getColumnIndex("id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            String endereco = c.getString(c.getColumnIndex("endereco"));
            String telefone = c.getString(c.getColumnIndex("telefone"));
            String site = c.getString(c.getColumnIndex("site"));
            Double nota = c.getDouble(c.getColumnIndex("nota"));
            Aluno aluno = new Aluno(id, nome, endereco, telefone, site, nota);
            alunos.add(aluno);
        }
        c.close();

        return alunos;

    }

    public void remover(@NotNull Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String [] params = {String.valueOf(aluno.getId())};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(@NotNull Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params ={aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome()); dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }
}
