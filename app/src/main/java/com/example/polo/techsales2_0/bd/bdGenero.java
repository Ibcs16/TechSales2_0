package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.polo.techsales2_0.bean.Genero;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maycon on 03/06/2017.
 */

public class bdGenero {
    public SQLiteDatabase db,dbr;

    public bdGenero(Context context) {
        //objeto obrigatório para todas as classes
        bdCore auxBd = new bdCore(context);

        //acesso para escrita no bd
        db = auxBd.getWritableDatabase();
        //acesso para leitura do bd
        dbr = auxBd.getReadableDatabase();
    }


    /**
     * Método para inserir
     */
    public long insertGenero(Genero genero) {
        ContentValues values = new ContentValues();
        values.put("gen_Nome", genero.getGen_Nome());


        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("genero", null, values);
    }

    /**
     * Método para deletar
     */
    public void deleteAllGenero() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM genero;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteGenero(int gen_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{gen_id+""};

        // Delete query
        db.delete("genero",// Nome da tabela
                "gen_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Genero> listarGeneros() {
        // Cria lista
        List<Genero> listaGeneros = new LinkedList<Genero>();
        // Query do banco
        String query = "SELECT * FROM genero";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Genero genero = new Genero();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            genero.setGen_id(cursor.getInt(0));
            genero.setGen_Nome(cursor.getString(1));


            // Adiciona as informacoes
            listaGeneros.add(genero);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaGeneros;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  genero;";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public List<Genero> buscarGenero(Integer id) {
        // Cria lista
        List<Genero> listaGenero = new LinkedList<Genero>();
        // Query do banco
        Cursor cursor = db.query("genero",
                new String[]{"gen_Nome"},
                "gen_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Genero genero = new Genero();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                genero.setGen_id(cursor.getInt(0));
                genero.setGen_Nome(cursor.getString(1));


                // Adiciona as informacoes
                listaGenero.add(genero);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaGenero;
    }

    //-------------------------------------------------------------------------
    public List<Genero> getAllSql(){
        return findBySql("SELECT * FROM genero;");
    }
    // Consulta por sql s
    public List<Genero> findBySql(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[Praetech]", "SQL: " + sql);
        try {
            Log.d("[Praetech]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[Praetech]", "Consultou...");
            return toList(c);
        } finally {

            //dbr.close();
        }
    }

    // Lê o cursor e cria a lista de jogos
    private List<Genero> toList(Cursor c) {
        List<Genero> generos = new ArrayList<Genero>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Genero genero = new Genero();
                generos.add(genero);

                // recupera os atributos de jogos

                genero.setGen_id(c.getInt(c.getColumnIndex("gen_id")));
                genero.setGen_Nome(c.getString(c.getColumnIndex("gen_Nome")));

            } while (c.moveToNext());
        }
        return generos;
    }

    /**
     * método que somente executa sql de manipulação de dados
     * @param sql
     */
    public void executeSQL(String sql) {
        try {
            Log.d("Praetech", "Executando: " + sql);
            db.execSQL(sql);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
