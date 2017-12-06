package com.example.polo.techsales2_0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by LUCASP on 24/06/2017.
 */

public class Perfil_Usuario extends AppCompatActivity{
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuario);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbperfil);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();

        if(savedInstanceState == null)
        {
                Fragment_Perfil_usuario frag = new Fragment_Perfil_usuario();

                frag.setArguments(getIntent().getExtras());

                FragmentTransaction ft =
                        getSupportFragmentManager().
                                beginTransaction();

                ft.add(R.id.flContentPerfil, frag, "Perfil_Fragment");
                ft.commit();

            }else{

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
                iniciarPesquisar();
                return true;

            case R.id.action_opcao3:
                //iniciarPerfil();
                return true;
            case R.id.opcao_tres:

                firebaseAuth.signOut();
                finish();
                Intent i = new Intent(Perfil_Usuario.this,MainActivity.class);
                startActivity(i);

            case R.id.action_opcao4:
                home();
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

