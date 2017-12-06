package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.polo.techsales2_0.bean.Jogo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maycon on 03/06/2017.
 */

public class bdJogo {

    public SQLiteDatabase db,dbr;

    static final String COLUNA_JOGO_NOME = "jog_Nome";
    static final String COLUNA_JOGO_ID = "jog_id";
    static final String COLUNA_JOGO_MINIATURA = "jog_Miniatura";
    static final String COLUNA_JOGO_FUNDO = "jog_Fundo";
    static final String COLUNA_JOGO_DATALANC = "jog_DataLanc";
    static final String COLUNA_JOGO_RESUMO = "jog_resumo";
    static final String COLUNA_JOGO_GEN_ID = "jog_gen_id";
    static final String COLUNA_JOGO_CON_ID = "jog_con_id";

    public bdJogo(Context context) {
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
    public long insertJogo(Jogo jogo) {

        ContentValues values = new ContentValues();
        /*
        values.put("jog_Nome", jogo.getJog_Nome());
        values.put("jog_DataLanc", String.valueOf(jogo.getJog_DataLanc()));
        values.put("jog_resumo", jogo.getJog_resumo());
        values.put("jog_con_id", jogo.getJog_con_id());
        values.put("jog_gen_id", jogo.getJog_gen_id());
        values.put("jog_Miniatura",jogo.getJog_Miniatura());
        values.put("jog_Fundo", jogo.getJog_Fundo());
        /*values.put("jog_Miniatura", ImgUtil.getBytes(jogo.getJog_Miniatura()));
        values.put("jog_Fundo", ImgUtil.getBytes(jogo.getJog_Fundo()));

        //inserindo diretamente na tabela pessoa sem a necessidade de script sql*/
        return db.insert("jogo", null, values);
    }

    /**
     * Método para deletar
     */
    public void deleteAllJogo() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM jogo;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteJogo(int jog_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{jog_id+""};

        // Delete query
        db.delete("jogo",// Nome da tabela
                "jog_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Jogo> listarJogos() {
        // Cria lista
        List<Jogo> listaJogos = new LinkedList<Jogo>();
        // Query do banco
        String query = "SELECT * FROM jogo";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Jogo jogo = new Jogo();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            /*jogo.setJog_id(cursor.getInt(0));
            jogo.setJog_Nome(cursor.getString(1));
            jogo.setJog_Miniatura(cursor.getString(2));
            jogo.setJog_Fundo(cursor.getString(3));
            /*jogo.setJog_Miniatura(ImgUtil.getPhoto(cursor.getBlob(2)));
            jogo.setJog_Fundo(ImgUtil.getPhoto(cursor.getBlob(3)));
            jogo.setJog_DataLanc(cursor.getString(4));
            jogo.setJog_gen_id(cursor.getInt(5));
            jogo.setJog_con_id(cursor.getInt(6));
            jogo.setJog_resumo(cursor.getString(7));*/

            // Adiciona as informacoes
            listaJogos.add(jogo);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaJogos;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  jogo;";

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


    public Jogo buscarJogo(String id) {
        Jogo jogo1 = new Jogo();
        Cursor cursor = db.query("jogo",
                new String[]{"jog_id","jog_Nome", "jog_Miniatura", "jog_Fundo", "jog_DataLanc", "jog_gen_id", "jog_con_id", "jog_resumo"},
                "jog_id=?", new String[]{id},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui

                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                /*jogo1.setJog_id(cursor.getInt(0));
                jogo1.setJog_Nome(cursor.getString(1));
                jogo1.setJog_Miniatura(cursor.getString(2));
                jogo1.setJog_Fundo(cursor.getString(3));

                jogo1.setJog_DataLanc(cursor.getString(4));
                jogo1.setJog_gen_id(cursor.getInt(5));
                jogo1.setJog_con_id(cursor.getInt(6));
                jogo1.setJog_resumo(cursor.getString(7));*/

            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo

        }
        // Retorna a lista
        return jogo1;


    }

    //-------------------------------------------------------------------------
    public List<Jogo> getAllSql(){
        return findBySql("SELECT * FROM jogo;");
    }
    // Consulta por sql s
    public List<Jogo> findBySql(String sql) {
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
    private List<Jogo> toList(Cursor c) {
        List<Jogo> jogos = new ArrayList<Jogo>();
        Log.d("Praetech", "Identifica Cursor...");

        if (c.moveToFirst()) {
            do {
                Jogo jogo = new Jogo();

                jogos.add(jogo);

                // recupera os atributos de contatos
                /*
                jogo.setJog_id(c.getInt(c.getColumnIndex(bdJogo.COLUNA_JOGO_ID)));
                jogo.setJog_Nome(c.getString(c.getColumnIndex(bdJogo.COLUNA_JOGO_NOME)));
                jogo.setJog_gen_id(c.getInt(c.getColumnIndex(bdJogo.COLUNA_JOGO_GEN_ID)));
                jogo.setJog_con_id(c.getInt(c.getColumnIndex(bdJogo.COLUNA_JOGO_CON_ID)));
                jogo.setJog_DataLanc(c.getString(c.getColumnIndex(bdJogo.COLUNA_JOGO_DATALANC)));
                jogo.setJog_resumo(c.getString(c.getColumnIndex(bdJogo.COLUNA_JOGO_RESUMO)));
                jogo.setJog_Miniatura(c.getString(c.getColumnIndex(bdJogo.COLUNA_JOGO_MINIATURA)));
                jogo.setJog_Fundo(c.getString(c.getColumnIndex(bdJogo.COLUNA_JOGO_FUNDO)));*/
                /*jogo.setJog_Miniatura(ImgUtil.getPhoto(c.getBlob(c.getColumnIndex(bdJogo.COLUNA_JOGO_MINIATURA))));
                jogo.setJog_Fundo(ImgUtil.getPhoto(c.getBlob(c.getColumnIndex(bdJogo.COLUNA_JOGO_FUNDO))));*/

            } while (c.moveToNext());
        }
        return jogos;
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

    public List<Jogo> getAllPesquisa(String nome) {
        // Cria lista
        List<Jogo> listaJogo = new LinkedList<Jogo>();
        // Query do banco
        Cursor cursor = db.query("jogo",
                new String[]{"jog_id","jog_Nome", "jog_Miniatura", "jog_Fundo", "jog_DataLanc", "jog_gen_id", "jog_con_id", "jog_resumo"},
                "jog_Nome LIKE ?", new String[]{nome+"%"},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui

                Jogo jogo1 = new Jogo();
                /*
                jogo1.setJog_id(cursor.getInt(0));
                jogo1.setJog_Nome(cursor.getString(1));
                jogo1.setJog_Miniatura(cursor.getString(2));
                jogo1.setJog_Fundo(cursor.getString(3));
                /*jogo.setJog_Miniatura(ImgUtil.getPhoto(cursor.getBlob(2)));
                jogo.setJog_Fundo(ImgUtil.getPhoto(cursor.getBlob(3)));
                jogo1.setJog_DataLanc(cursor.getString(4));
                jogo1.setJog_gen_id(cursor.getInt(5));
                jogo1.setJog_con_id(cursor.getInt(6));
                jogo1.setJog_resumo(cursor.getString(7));*/

                // Adiciona as informacoes
                listaJogo.add(jogo1);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaJogo;
    }
}
