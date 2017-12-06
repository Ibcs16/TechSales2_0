package com.example.polo.techsales2_0.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by Maycon on 03/06/2017.
 */

//bdCore faz a comunicaçao com o bd remoto e ve se o app ta igual o remoto se nao tiver ele atualiza os dados
public class bdCore extends SQLiteOpenHelper{

    private static final String Name_BD = "techSales.db";
    private static final int Versao_BD = 1;

    public bdCore(Context context) {
        super(context, Name_BD, null, Versao_BD);
    }

    public boolean checkDataBase(Context context) {
        File database = context.getDatabasePath(Name_BD);
        if (!database.exists()) {
            Log.i("Prartech", "BD não existente");
            return false;
        } else {
            Log.i("Praetech", "BD não existente");
            return true;
        }
    }

    /**
     * Chamando os metodos de criacao das tabelas
     *
     * @param bd
     */
    @Override
    public void onCreate(SQLiteDatabase bd) {
        //neste local são chamados os métodos que criam as tabelas
        criarTabelaLogin(bd);
        criarTabelaJogo(bd);
        criarTabelaFuncionario(bd);
        criarTabelaConsole(bd);
        criarTabelaGenero(bd);
        criarTabelaFav_Jogo(bd);
        criarTabelaAdd_Jogo(bd);
        criarTabelaConta(bd);



    }

    private void criarTabelaConta(SQLiteDatabase bd) {
        String slqCreateTabelaConta = "CREATE TABLE IF NOT EXISTS conta ("
                + "cont_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "cont_UserNome VARCHAR(45) NOT NULL,"
                + "cont_UserSenha VARCHAR(10) NOT NULL,"
                + "cont_fun_id INTEGER NOT NULL,"
                + "cont_img TEXT NOT NULL"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaConta);
    }

    private void criarTabelaAdd_Jogo(SQLiteDatabase bd) {
        String slqCreateTabelaAdd_Jogo = "CREATE TABLE IF NOT EXISTS add_jogo ("
                + "aj_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "aj_cont_id INTEGER NOT NULL,"
                + "aj_jog_id INTEGER NOT NULL"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaAdd_Jogo);
    }

    private void criarTabelaFav_Jogo(SQLiteDatabase bd) {
        String slqCreateTabelaFav_Jogo = "CREATE TABLE IF NOT EXISTS fav_jogo ("
                + "fj_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "fj_cont_id INTEGER NOT NULL,"
                + "fj_jog_id INTEGER NOT NULL"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaFav_Jogo);
    }

    private void criarTabelaGenero(SQLiteDatabase bd) {
        String slqCreateTabelaGenero = "CREATE TABLE IF NOT EXISTS genero ("
                + "gen_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "gen_Nome VARCHAR(45)"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaGenero);
    }

    private void criarTabelaConsole(SQLiteDatabase bd) {
        String slqCreateTabelaConsole = "CREATE TABLE IF NOT EXISTS console ("
                + "con_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "con_Nome VARCHAR(45)"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaConsole);

    }

    private void criarTabelaLogin(SQLiteDatabase bd) {
        String slqCreateTabelaLogin = "CREATE TABLE IF NOT EXISTS login ("
                + "login_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "login_cont_id INTEGER NOT NULL,"
                + "login_cont_UserNome VARCHAR(45) NOT NULL,"
                + "login_cont_UserSenha VARCHAR(10) NOT NULL,"
                + "login_cont_fun_id INTEGER NOT NULL,"
                + "login_cont_img TEXT NOT NULL"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaLogin);
        Log.i("Prartech", "Login iniciado");

    }


    /**
     * Create Jogo.
     * Cria tabela no banco local;
     * Chamada no metodo onCreate desta classe.
     *
     * @param bd = Nome do banco de dados.
     */
    public void criarTabelaJogo(SQLiteDatabase bd) {
        String slqCreateTabelaJogo = "CREATE TABLE IF NOT EXISTS jogo ("
                + "jog_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "jog_Nome TEXT NOT NULL,"
                //+ "jog_Miniatura LONGBLOB NOT NULL,"
                // + "jog_Fundo LONGBLOB NOT NULL,"
                + "jog_Miniatura TEXT NOT NULL,"
                + "jog_Fundo TEXT NOT NULL,"
                + "jog_DataLanc DATE NOT NULL,"
                + "jog_gen_id INTEGER NOT NULL,"
                + "jog_con_id INTEGER NOT NULL,"
                + "jog_resumo TEXT"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaJogo);
    }

    /**
     * Create Funcionario.
     * Cria tabela no banco local;
     * Chamada no metodo onCreate desta classe.
     *
     * @param bd = Nome do banco de dados.
     */
    public void criarTabelaFuncionario(SQLiteDatabase bd) {
        String slqCreateTabelaFuncionario = "CREATE TABLE IF NOT EXISTS funcionario("
                + "fun_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "fun_Nome TEXT NOT NULL"
                + ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaFuncionario);
    }


    /**
     * Upgrade banco.
     * Sistema chama automaticamente quando a versão do banco é alterada;
     * Realiza o drop e create das tabelas.
     *
     * @param bd         = Banco de dados;
     * @param oldVersion = Versao antiga do banco;
     * @param newVersion = Nova versao do banco.
     */
    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {

        //TABELA Funcionario
        upgradeTabelaFuncionario(bd, oldVersion, newVersion);
        criarTabelaFuncionario(bd);

        //TABELA Jogo
        upgradeTableJogo(bd, oldVersion, newVersion);
        criarTabelaJogo(bd);

        //TABELA Console
        upgradeTabelaConsole(bd, oldVersion, newVersion);
        criarTabelaConsole(bd);

        //TABELA Genero
        upgradeTabelaGenero(bd, oldVersion, newVersion);
        criarTabelaGenero(bd);

        //TABELA Fav_Jogo
        upgradeTabelaFav_Jogo(bd, oldVersion, newVersion);
        criarTabelaFav_Jogo(bd);

        //TABELA Add_Jogo
        upgradeTabelaAdd_Jogo(bd, oldVersion, newVersion);
        criarTabelaAdd_Jogo(bd);

        //TABELA Conta
        upgradeTabelaConta(bd, oldVersion, newVersion);
        criarTabelaConta(bd);

        //TABELA LOGIN
        upgradeTabelaLogin(bd,oldVersion,newVersion);
        criarTabelaLogin(bd);
    }

    /******************************************************************************
     * UPGRADES DAS TABELAS, COMANDOS PARA DELETÁ-LAS CASO UMA VERSÃO NOVA DO BANCO DE DADOS ESTEJA NO CÓDIGO
     */
    private void upgradeTableJogo(SQLiteDatabase bd, int oldVersion, int newVersion) {
        //Drop da tabela
        String sqlDropTabelaJogo = "DROP TABLE jogo";
        //Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaJogo);
    }

    private void upgradeTabelaFuncionario(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaFuncionario = "DROP TABLE funcionario";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaFuncionario);
    }

    private void upgradeTabelaGenero(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaGenero = "DROP TABLE genero";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaGenero);
    }

    private static void upgradeTabelaLogin(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaLogin = "DROP TABLE login";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaLogin);
    }

    private void upgradeTabelaConsole(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaConsole = "DROP TABLE console";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaConsole);
    }

    private void upgradeTabelaFav_Jogo(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaFav_Jogo = "DROP TABLE fav_jogo";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaFav_Jogo);
    }

    private void upgradeTabelaAdd_Jogo(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaAdd_Jogo = "DROP TABLE add_jogo";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaAdd_Jogo);
    }

    private void upgradeTabelaConta(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaConta = "DROP TABLE conta";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaConta);
    }




}


