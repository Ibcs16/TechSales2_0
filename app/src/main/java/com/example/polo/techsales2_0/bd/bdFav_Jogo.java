package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.polo.techsales2_0.bean.Fav_Jogo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maycon on 03/06/2017.
 */

public class bdFav_Jogo {

    public SQLiteDatabase db,dbr;

    public bdFav_Jogo(Context context) {
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
    public long insertFav_Jogo(Fav_Jogo fav_jogo) {
        ContentValues values = new ContentValues();
        values.put("fj_cont_id", fav_jogo.getFj_cont_id());
        values.put("fj_jog_id", fav_jogo.getFj_jog_id());


        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("fav_jogo", null, values);
    }

    /**
     * Método para deletar
     */
    public void deleteAllFav_Jogo() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM fav_jogo;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteFav_Jogo(int fav_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{fav_id+""};

        // Delete query
        db.delete("fav_jogo",// Nome da tabela
                "fj_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Fav_Jogo> listarFav_Jogo() {
        // Cria lista
        List<Fav_Jogo> listaFav_Jogo = new LinkedList<Fav_Jogo>();
        // Query do banco
        String query = "SELECT * FROM fav_jogo";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Fav_Jogo fj = new Fav_Jogo();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            fj.setFj_id(cursor.getInt(0));
            fj.setFj_cont_id(cursor.getInt(1));
            fj.setFj_jog_id(cursor.getInt(2));

            // Adiciona as informacoes
            listaFav_Jogo.add(fj);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaFav_Jogo;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  fav_jogo;";

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


    public List<Fav_Jogo> buscarFav_Jogo(Integer id) {
        // Cria lista
        List<Fav_Jogo> listaFav_Jogo = new LinkedList<>();
        // Query do banco
        Cursor cursor = db.query("fav_jogo",
                new String[]{"fj_cont_id", "fj_jog_id"},
                "fj_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Fav_Jogo fj = new Fav_Jogo();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                fj.setFj_id(cursor.getInt(0));
                fj.setFj_cont_id(cursor.getInt(1));
                fj.setFj_jog_id(cursor.getInt(2));


                // Adiciona as informacoes
                listaFav_Jogo.add(fj);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaFav_Jogo;
    }

    //-------------------------------------------------------------------------
    public List<Fav_Jogo> getAllSql(){
        return findBySql("SELECT * FROM fav_jogo;");
    }
    // Consulta por sql s
    public List<Fav_Jogo> findBySql(String sql) {
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
    private List<Fav_Jogo> toList(Cursor c) {
        List<Fav_Jogo> fav_jogos = new ArrayList<Fav_Jogo>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Fav_Jogo fj = new Fav_Jogo();
                fav_jogos.add(fj);

                // recupera os atributos de jogos

                fj.setFj_id(c.getInt(c.getColumnIndex("fj_id")));
                fj.setFj_cont_id(c.getInt(c.getColumnIndex("fj_cont_id")));
                fj.setFj_jog_id(c.getInt(c.getColumnIndex("fj_jog_id")));

            } while (c.moveToNext());
        }
        return fav_jogos;
    }

    public List<String> getAllIds(){
        return findBySql2("SELECT * FROM fav_jogo;");
    }
    // Consulta por sql s
    public List<String> findBySql2(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[Praetech]", "SQL: " + sql);
        try {
            Log.d("[Praetech]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[Praetech]", "Consultou...");
            return toList2(c);
        } finally {

            //dbr.close();
        }
    }

    // Lê o cursor e cria a lista de jogos
    private List<String> toList2(Cursor c) {
        List<String> fav_jogos = new ArrayList<String>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                String s = c.getString(0);
                fav_jogos.add(s);

            } while (c.moveToNext());
        }
        return fav_jogos;
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
