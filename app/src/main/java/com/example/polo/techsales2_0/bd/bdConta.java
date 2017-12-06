package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.polo.techsales2_0.bean.Conta;
import com.example.polo.techsales2_0.bean.Login;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maycon on 03/06/2017.
 */

public class bdConta {

    public SQLiteDatabase db,dbr;

    public bdConta(Context context) {
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
    public long insertConta(Conta conta) {
        ContentValues values = new ContentValues();
        values.put("cont_UserNome", conta.getCont_UserNome());
        values.put("cont_UserSenha", conta.getCont_UserSenha());
        values.put("cont_fun_id", conta.getCont_fun_id());
        values.put("cont_img",conta.getCont_img());


        return db.insert("conta", null, values);
    }

    public long AlterarConta(Login login) {
        ContentValues values = new ContentValues();
        values.put("cont_UserNome", login.getCont_UserNome());
        values.put("cont_UserSenha", login.getCont_UserSenha());
        values.put("cont_img",login.getCont_img());
        int i = db.update("conta", values,"cont_id"+"="+0,null);
        return i;
    }
    /**
     * Método para deletar
     */
    public void deleteAllConta() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM conta;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteConta(int cont_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{cont_id+""};

        // Delete query
        db.delete("conta",// Nome da tabela
                "cont_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Conta> listarContas() {
        // Cria lista
        List<Conta> listaContas = new LinkedList<Conta>();
        // Query do banco
        String query = "SELECT * FROM conta";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Conta conta = new Conta();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            conta.setCont_id(cursor.getInt(0));
            conta.setCont_UserNome(cursor.getString(1));
            conta.setCont_UserSenha(cursor.getString(2));
            conta.setCont_fun_id(cursor.getInt(3));
            conta.setCont_img(cursor.getString(4));


            // Adiciona as informacoes
            listaContas.add(conta);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaContas;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  conta;";

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


    public List<Conta> buscarConta(Integer id) {
        // Cria lista
        List<Conta> listaConta = new LinkedList<Conta>();
        // Query do banco
        Cursor cursor = db.query("conta",
                new String[]{"cont_id","cont_UserNome", "cont_UserSenha", "cont_fun_id","cont_img"},
                "cont_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Conta conta = new Conta();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                conta.setCont_id(cursor.getInt(0));
                conta.setCont_UserNome(cursor.getString(1));
                conta.setCont_UserSenha(cursor.getString(2));
                conta.setCont_fun_id(cursor.getInt(3));
                conta.setCont_img(cursor.getString(4));


                // Adiciona as informacoes
                listaConta.add(conta);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaConta;
    }


    //-------------------------------------------------------------------------
    public List<Conta> getAllSql(){
        return findBySql("SELECT * FROM conta;");
    }
    // Consulta por sql s
    public List<Conta> findBySql(String sql) {
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
    private List<Conta> toList(Cursor c) {
        List<Conta> contas = new ArrayList<Conta>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Conta conta = new Conta();
                contas.add(conta);

                // recupera os atributos de jogos

                conta.setCont_id(c.getInt(c.getColumnIndex("cont_id")));
                conta.setCont_UserNome(c.getString(c.getColumnIndex("cont_UserNome")));
                conta.setCont_UserSenha(c.getString(c.getColumnIndex("cont_UserSenha")));
                conta.setCont_fun_id(c.getInt(c.getColumnIndex("cont_fun_id")));
                conta.setCont_img(c.getString(c.getColumnIndex("cont_img")));

            } while (c.moveToNext());
        }
        return contas;
    }

    public Conta getConta(){
        return findBySql3("SELECT * FROM conta;");
    }
    // Consulta por sql s
    public Conta findBySql3(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[Praetech]", "SQL: " + sql);
        try {
            Log.d("[Praetech]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[Praetech]", "Consultou...");
            return toConta(c);
        } finally {

            //dbr.close();
        }
    }

    // Lê o cursor e cria a lista de jogos
    private Conta toConta(Cursor c) {
        Conta conta = new Conta();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {


                // recupera os atributos de jogos

                conta.setCont_id(c.getInt(c.getColumnIndex("cont_id")));
                conta.setCont_UserNome(c.getString(c.getColumnIndex("cont_UserNome")));
                conta.setCont_UserSenha(c.getString(c.getColumnIndex("cont_UserSenha")));
                conta.setCont_fun_id(c.getInt(c.getColumnIndex("cont_fun_id")));
                conta.setCont_img(c.getString(c.getColumnIndex("cont_img")));

            } while (c.moveToNext());
        }
        return conta;
    }

    public Conta buscarConta(String nome, String senha) {
        Conta conta = new Conta();
        Cursor cursor = db.query("conta",
                new String[]{"cont_id","cont_UserNome", "cont_UserSenha","cont_fun_id","cont_img"},
                "cont_UserNome=? and cont_UserSenha=?", new String[]{nome,senha},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui

                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                conta.setCont_id(cursor.getInt(0));
                conta.setCont_UserNome(cursor.getString(1));
                conta.setCont_UserSenha(cursor.getString(2));
                conta.setCont_fun_id(cursor.getInt(3));
                conta.setCont_img(cursor.getString(4));



            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo

        }
        // Retorna a lista
        return conta;


    }
    public Conta buscarContaPorId(Integer id) {
        Conta conta = new Conta();
        Cursor cursor = db.query("conta",
                new String[]{"cont_id","cont_UserNome", "cont_UserSenha","cont_fun_id","cont_img"},
                "cont_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui

                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                conta.setCont_id(cursor.getInt(0));
                conta.setCont_UserNome(cursor.getString(1));
                conta.setCont_UserSenha(cursor.getString(2));
                conta.setCont_fun_id(cursor.getInt(3));
                conta.setCont_img(cursor.getString(4));



            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo

        }
        // Retorna a lista
        return conta;


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
