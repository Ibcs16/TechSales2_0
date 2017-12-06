package com.example.polo.techsales2_0.bean;



/**
 * Created by User on 27/05/2017.
 */

public class Jogo {

    private String joGenero;
    private String joConsole;
    private String joNome;
    private String joDesc;
    private String joId;
    private String joDataLanc;
    private String joMini;
    private String joPoster;


    public Jogo() {

    }

    @Override
    public String toString() {
        return "Jogo{" +
                "joGenero='" + joGenero + '\'' +
                ", joConsole='" + joConsole + '\'' +
                ", joNome='" + joNome + '\'' +
                ", joDesc='" + joDesc + '\'' +
                ", joId='" + joId + '\'' +
                ", joDataLanc='" + joDataLanc + '\'' +
                ", joMini='" + joMini + '\'' +
                ", joPoster='" + joPoster + '\'' +
                '}';
    }

    public String getJoGenero() {
        return joGenero;
    }

    public void setJoGenero(String joGenero) {
        this.joGenero = joGenero;
    }

    public String getJoConsole() {
        return joConsole;
    }

    public void setJoConsole(String joConsole) {
        this.joConsole = joConsole;
    }

    public String getJoNome() {
        return joNome;
    }

    public void setJoNome(String joNome) {
        this.joNome = joNome;
    }

    public String getJoDesc() {
        return joDesc;
    }

    public void setJoDesc(String joDesc) {
        this.joDesc = joDesc;
    }

    public String getJoId() {
        return joId;
    }

    public void setJoId(String joId) {
        this.joId = joId;
    }

    public String getJoDataLanc() {
        return joDataLanc;
    }

    public void setJoDataLanc(String joDataLanc) {
        this.joDataLanc = joDataLanc;
    }

    public String getJoMini() {
        return joMini;
    }

    public void setJoMini(String joMini) {
        this.joMini = joMini;
    }

    public String getJoPoster() {
        return joPoster;
    }

    public void setJoPoster(String joPoster) {
        this.joPoster = joPoster;
    }
}
