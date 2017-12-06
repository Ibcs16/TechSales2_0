package com.example.polo.techsales2_0.bean;

/**
 * Created by Maycon on 03/06/2017.
 */

public class Conta {

    private int cont_id;
    private String cont_UserNome;
    private String cont_UserSenha;
    private int cont_fun_id;
    private String cont_img;

    public String getCont_img() {
        return cont_img;
    }

    public void setCont_img(String cont_img) {
        this.cont_img = cont_img;
    }

    public int getCont_id() {
        return cont_id;
    }

    public void setCont_id(int cont_id) {
        this.cont_id = cont_id;
    }

    public String getCont_UserNome() {
        return cont_UserNome;
    }

    public void setCont_UserNome(String cont_UserNome) {
        this.cont_UserNome = cont_UserNome;
    }

    public String getCont_UserSenha() {
        return cont_UserSenha;
    }

    public void setCont_UserSenha(String cont_UserSenha) {
        this.cont_UserSenha = cont_UserSenha;
    }

    public int getCont_fun_id() {
        return cont_fun_id;
    }

    public void setCont_fun_id(int cont_fun_id) {
        this.cont_fun_id = cont_fun_id;
    }
}
