package com.example.polo.techsales2_0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.polo.techsales2_0.bd.bdConsole;
import com.example.polo.techsales2_0.bd.bdConta;
import com.example.polo.techsales2_0.bd.bdFuncionario;
import com.example.polo.techsales2_0.bd.bdGenero;
import com.example.polo.techsales2_0.bd.bdJogo;
import com.example.polo.techsales2_0.bd.bdLogin;
import com.example.polo.techsales2_0.bean.Console;
import com.example.polo.techsales2_0.bean.Conta;
import com.example.polo.techsales2_0.bean.Funcionario;
import com.example.polo.techsales2_0.bean.Genero;
import com.example.polo.techsales2_0.bean.Jogo;
import com.example.polo.techsales2_0.bean.Login;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by LUCASP on 25/06/2017.
 */

public class Pesquisa_Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa_jogo);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbpesquisa);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(savedInstanceState==null) {

            Pesquisa_Fragment pf = new Pesquisa_Fragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.flContent, pf, "FragmentPesquisa");
            ft.commit();


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            /*case R.id.action_opcao1:
                this.finish();
                return true;*/
            case R.id.action_opcao2:

                return true;

            case R.id.action_opcao3:
                iniciarPerfil();
                return true;
            case R.id.opcao_tres:
                firebaseAuth.signOut();
                finish();
                Intent i = new Intent(Pesquisa_Activity.this,MainActivity.class);
                startActivity(i);

            case R.id.action_opcao4:
               home();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void home(){
        Intent intent = new Intent(Pesquisa_Activity.this,Main_Games.class);
        startActivity(intent);

    }

    public void iniciarPerfil(){
        Intent intent = new Intent(getApplicationContext(), Perfil_Usuario.class);
        startActivity(intent);
    }

    public void iniciarPesquisar(){
        Intent intent = new Intent(getApplicationContext(),Pesquisa_Activity.class);
        startActivity(intent);
    }

    public void sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
// Add the buttons
        builder.setMessage("Você sairá da sua conta");
        builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                //lg = new bdLogin(getApplicationContext());
                //lg.deleteLogin(1);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Canceler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg2) {
                dialog.dismiss();
            }
        });

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
