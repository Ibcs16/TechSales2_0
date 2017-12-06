package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.polo.techsales2_0.bean.Console;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maycon on 03/06/2017.
 */

public class bdConsole {

    public SQLiteDatabase db,dbr;

    public bdConsole(Context context) {
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
    public long insertConsole(Console console) {
        ContentValues values = new ContentValues();
        values.put("con_Nome", console.getCon_Nome());


        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("console", null, values);
    }

    /**
     * Método para deletar
     */
    public void deleteAllConsole() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM console;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteConsole(int con_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{con_id+""};

        // Delete query
        db.delete("console",// Nome da tabela
                "con_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Console> listarConsoles() {
        // Cria lista
        List<Console> listaConsoles = new LinkedList<Console>();
        // Query do banco
        String query = "SELECT * FROM console";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Console console = new Console();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            console.setCon_id(cursor.getInt(0));
            console.setCon_Nome(cursor.getString(1));

            // Adiciona as informacoes
            listaConsoles.add(console);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaConsoles;
    }



    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  console;";

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


    public List<Console> buscarFuncionario(Integer id) {
        // Cria lista
        List<Console> listaConsole = new LinkedList<Console>();
        // Query do banco
        Cursor cursor = db.query("console",
                new String[]{"con_Nome"},
                "con_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
            Console console = new Console();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                console.setCon_id(cursor.getInt(0));
                console.setCon_Nome(cursor.getString(1));


                // Adiciona as informacoes
                listaConsole.add(console);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaConsole;
    }

    //-------------------------------------------------------------------------
    public List<Console> getAllSql(){
        return findBySql("SELECT * FROM console;");
    }
    // Consulta por sql s
    public List<Console> findBySql(String sql) {
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
    private List<Console> toList(Cursor c) {
        List<Console> consoles = new ArrayList<Console>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Console console = new Console();
                consoles.add(console);

                // recupera os atributos de jogos

                console.setCon_id(c.getInt(c.getColumnIndex("con_id")));
                console.setCon_Nome(c.getString(c.getColumnIndex("con_Nome")));

            } while (c.moveToNext());
        }
        return consoles;
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
