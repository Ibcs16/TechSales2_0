package com.example.polo.techsales2_0.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.polo.techsales2_0.bean.Console;
import com.example.polo.techsales2_0.bean.Funcionario;
import com.example.polo.techsales2_0.bean.Login;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by LUCASP on 26/06/2017.
 */

public class bdLogin {
    public SQLiteDatabase db,dbr;

    public bdLogin(Context context) {
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
    public long insertLogin(Login login) {
        ContentValues values = new ContentValues();
        values.put("login_id", login.getLogin_id());
        values.put("login_cont_id", login.getLogin_id());
        values.put("login_cont_UserNome", login.getCont_UserNome());
        values.put("login_cont_UserSenha", login.getCont_UserSenha());
        values.put("login_cont_fun_id", login.getCont_fun_id());
        values.put("login_cont_img",login.getCont_img());
        //inserindo diretamente na tabela login sem a necessidade de script sql
        return db.insert("login", null, values);
    }




    public long AlterarLogin(Login login, String senha, String user) {
        ContentValues values = new ContentValues();

        values.put("login_cont_UserNome", user);
        values.put("login_cont_UserSenha", senha);
        values.put("login_cont_img",login.getCont_img());
        int i = db.update("login", values,"login_id"+"="+login.getLogin_id(),null);
        return  i;
    }

    public Login buscarLogin() {
        Login login = new Login();
        Cursor cursor = db.query("login",
                new String[]{"login_id","login_cont_id","login_cont_UserNome", "login_cont_UserSenha","login_cont_fun_id","login_cont_img"},
                "login_id=?", new String[]{String.valueOf(0)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui

                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                login.setLogin_id(0);
                login.setCont_id(cursor.getInt(1));
                login.setCont_UserNome(cursor.getString(2));
                login.setCont_UserSenha(cursor.getString(3));
                login.setCont_fun_id(cursor.getInt(4));
                login.setCont_img(cursor.getString(5));



            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo

        }
        // Retorna a lista
        return login;


    }

    /**
     * Método para deletar
     */
    public void deleteAllConsole() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM login;");
    }

    /**
     * Deletar por codigo
     */
    public void deleteLogin(int login_id) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{login_id+""};

        // Delete query
        db.delete("login",// Nome da tabela
                "login_id=?",// Coluna da tabela
                args); // Argumentos de delete

    }



    /**
     * Listar
     *
     * @return
     */





    //-------------------------------------------------------------------------
    public List<Login> getAllSql(){
        return findBySql("SELECT * FROM login;");
    }
    // Consulta por sql s
    public List<Login> findBySql(String sql) {
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
    private List<Login> toList(Cursor c) {
        List<Login> logins = new ArrayList<Login>();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Login login = new Login();
                logins.add(login);

                // recupera os atributos de jogos

                login.setLogin_id(c.getInt(c.getColumnIndex("login_id")));
                login.setCont_id(c.getInt(c.getColumnIndex("login_cont_id")));
                login.setCont_UserNome(c.getString(c.getColumnIndex("login_cont_UserNome")));
                login.setCont_UserSenha(c.getString(c.getColumnIndex("login_cont_UserSenha")));
                login.setCont_fun_id(c.getInt(c.getColumnIndex("login_cont_fun_id")));
                login.setCont_img(c.getString(c.getColumnIndex("login_cont_img")));


            } while (c.moveToNext());
        }
        return logins;
    }

    public Login getLogin(){
        return findBySql2("SELECT * FROM login;");
    }
    // Consulta por sql s
    public Login findBySql2(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[Praetech]", "SQL: " + sql);
        try {
            Log.d("[Praetech]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[Praetech]", "Consultou...");
            return toLogin(c);
        } finally {

            //dbr.close();
        }
    }

    // Lê o cursor e cria a lista de jogos
    private Login toLogin(Cursor c) {
        Login login = new Login();
        Log.d("Praetech", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {


                // recupera os atributos de jogos

                login.setLogin_id(c.getInt(c.getColumnIndex("login_id")));
                login.setCont_id(c.getInt(c.getColumnIndex("login_cont_id")));
                login.setCont_UserNome(c.getString(c.getColumnIndex("login_cont_UserNome")));
                login.setCont_UserSenha(c.getString(c.getColumnIndex("login_cont_UserSenha")));
                login.setCont_fun_id(c.getInt(c.getColumnIndex("login_cont_fun_id")));
                login.setCont_img(c.getString(c.getColumnIndex("login_cont_img")));


            } while (c.moveToNext());
        }
        return login;
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
