package com.example.polo.techsales2_0;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by alunos on 13/06/17.
 */

public class Main_Games extends AppCompatActivity  implements Main_Games_Fragment.OnItemPressListener{


    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbprincipal);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        if(savedInstanceState == null)
        {

                Main_Games_Fragment frag = new Main_Games_Fragment();

                frag.setArguments(getIntent().getExtras());
                FragmentTransaction ft =
                        getSupportFragmentManager().
                                beginTransaction().addToBackStack("Main_Games_Fragment");

                ft.add(R.id.flContent, frag, "Main_Games_Fragment");
                ft.commit();
                Log.i("Adc aqui","fragMain");
        }

    }


    @Override
    public void onItemPressed(String msg) {
        Log.i("onItemPressed","foi chamado");
        Game_Fragment frag = (Game_Fragment) getSupportFragmentManager().findFragmentByTag("Game_Fragment");
        frag.setId(msg);
        frag.inicioJogo(msg);
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
                iniciarPesquisar();
                return true;

            case R.id.action_opcao3:
                iniciarPerfil();
                return true;
            case R.id.opcao_tres:
                firebaseAuth.signOut();
                finish();
                Intent i = new Intent(Main_Games.this,MainActivity.class);
                startActivity(i);

                return true;
            case R.id.action_opcao4:
                //home();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void home(){
        Intent intent = new Intent(this,Main_Games.class);
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



