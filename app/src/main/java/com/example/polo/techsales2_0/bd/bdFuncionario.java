package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.polo.techsales2_0.bean.Funcionario;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maycon on 03/06/2017.
 */

public class bdFuncionario {

    public SQLiteDatabase db,dbr;

    public bdFuncionario(Context context) {
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
    public long insertFuncionario(Funcionario funcionario) {
        ContentValues values = new ContentValues();
        values.put("fun_Nome", funcionario.getFun_Nome());


        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("funcionario", null, values);
    }

    /**
     * Método para deletar
     */
    public void deleteAllFuncionario() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM funcionario;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteFuncionario(int fun_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{fun_id+""};

        // Delete query
        db.delete("funcionario",// Nome da tabela
                "fun_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Funcionario> listarFuncionarios() {
        // Cria lista
        List<Funcionario> listaFuncionarios = new LinkedList<Funcionario>();
        // Query do banco
        String query = "SELECT * FROM funcionario";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Funcionario funcionario = new Funcionario();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            funcionario.setFun_id(cursor.getInt(0));
            funcionario.setFun_Nome(cursor.getString(1));

            // Adiciona as informacoes
            listaFuncionarios.add(funcionario);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaFuncionarios;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  funcionario;";

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


    public List<Funcionario> buscarFuncionario(Integer id) {
        // Cria lista
        List<Funcionario> listaFuncionario = new LinkedList<Funcionario>();
        // Query do banco
        Cursor cursor = db.query("funcionario",
                new String[]{"fun_Nome"},
                "fun_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Funcionario funcionario = new Funcionario();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                funcionario.setFun_id(cursor.getInt(0));
                funcionario.setFun_Nome(cursor.getString(1));


                // Adiciona as informacoes
                listaFuncionario.add(funcionario);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaFuncionario;
    }

    //-------------------------------------------------------------------------
    public List<Funcionario> getAllSql(){
        return findBySql("SELECT * FROM funcionario;");
    }
    // Consulta por sql s
    public List<Funcionario> findBySql(String sql) {
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
    private List<Funcionario> toList(Cursor c) {
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Funcionario funcionario = new Funcionario();
                funcionarios.add(funcionario);

                // recupera os atributos de jogos

                funcionario.setFun_id(c.getInt(c.getColumnIndex("fun_id")));
                funcionario.setFun_Nome(c.getString(c.getColumnIndex("fun_Nome")));

            } while (c.moveToNext());
        }
        return funcionarios;
    }

    public Funcionario getFun(){
        return findBySql2("SELECT * FROM funcionario;");
    }
    // Consulta por sql s
    public Funcionario findBySql2(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[Praetech]", "SQL: " + sql);
        try {
            Log.d("[Praetech]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[Praetech]", "Consultou...");
            return toFun(c);
        } finally {

            //dbr.close();
        }
    }
    // Lê o cursor e cria a lista de jogos
    private Funcionario toFun(Cursor c) {
        Funcionario funcionario = new Funcionario();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {



                // recupera os atributos de jogos

                funcionario.setFun_id(c.getInt(c.getColumnIndex("fun_id")));
                funcionario.setFun_Nome(c.getString(c.getColumnIndex("fun_Nome")));

            } while (c.moveToNext());
        }
        return funcionario;
    }

    public Funcionario buscarFuncionario(String id) {
        Funcionario fun = new Funcionario();
        Cursor cursor = db.query("funcionario",
                new String[]{"fun_id","fun_Nome"},
                "fun_id=?", new String[]{id},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui

                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                fun.setFun_id(cursor.getInt(0));
                fun.setFun_Nome(cursor.getString(1));
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo

        }
        // Retorna a lista
        return fun;


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
