package com.example.polo.techsales2_0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polo.techsales2_0.bd.bdConta;
import com.example.polo.techsales2_0.bd.bdFav_Jogo;
import com.example.polo.techsales2_0.bd.bdJogo;
import com.example.polo.techsales2_0.bd.bdLogin;
import com.example.polo.techsales2_0.bean.Jogo;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


/**
 * Created by LUCASP on 24/06/2017.
 */

public class Game_Activity extends AppCompatActivity{
    private bdLogin lg;
    private bdJogo bd;
    private bdFav_Jogo bd_fj;
    FirebaseAuth firebaseAuth;
    private bdConta bd_cont;
    private Jogo jogo;
    private ImageView imgMini;
    private LinearLayout imgPoster;

    private TextView nome,qtd,sobre,info;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbprincipal);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        inicioJogo("0");
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
                Intent i = new Intent(Game_Activity.this,MainActivity.class);
                startActivity(i);
            case R.id.action_opcao4:
                home();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void inicioJogo(String id){


        bd = new bdJogo(getApplicationContext());
        jogo = bd.buscarJogo(id);
        if(jogo==null){
            Toast.makeText(getApplicationContext(),"Erro",Toast.LENGTH_SHORT).show();
        }else{
        preenche(jogo);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void preenche(Jogo jogo){

        imgMini = (ImageView) findViewById(R.id.imgMini);
        imgPoster = (LinearLayout) findViewById(R.id.imgPoster);

        Picasso.with(this)
                .load(jogo.getJoMini())
                .placeholder(R.drawable.preloader)   // optional
                .error(R.drawable.no_image)      // optional
                //.resize(100, 76)                    // optional
                //.fit()

                //.centerInside()
                .into(imgMini);

        ImageView imageView = new ImageView(getApplicationContext());

        Picasso.with(this)
                .load(jogo.getJoPoster())
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.no_image)
                //.resize(80, 80)
                //.fit()

                //.centerInside()

                .into(imageView);

        imgPoster.setBackground(imageView.getDrawable());

        if(jogo==null){
            Toast.makeText(getApplicationContext(),"Erro",Toast.LENGTH_SHORT).show();
        }else{
        sobre = (TextView) findViewById(R.id.txSobre);
        info = (TextView) findViewById(R.id.txInfo);
        nome = (TextView) findViewById(R.id.txTitulo);
        //qtd = (TextView) findViewById(R.id.txQtd);
        nome.setText(""+jogo.getJoNome());
        sobre.setText(""+jogo.getJoDesc());
        info.setText("Data de lançamento: "+jogo.getJoDataLanc());
        }


        /*Picasso.with(this)
                .load(jogo.getJog_Miniatura())
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.no_image)      // optional
                //.resize(100, 100)                    // optional
                .fit()
                .centerInside()
                .into();*/
    }




    public void onClickInsertFav_Jogo(View v){
        /*Intent i = getIntent();
        bd = new bdJogo(getApplicationContext());
        bd_cont = new bdConta(this);
        Conta conta;
        conta = bd_cont.buscarConta(etUserNome.getText().toString(), ""+i.getStringExtra("cont_id"));
        jogo = bd.buscarJogo(""+i.getStringExtra("id"));
        bd_fj = new bdFav_Jogo(this);
        Fav_Jogo fv = new Fav_Jogo();
        fv.setFj_jog_id(jogo.getJog_id());
        fv.setFj_cont_id(conta.getCont_id());
        bd_fj.insertFav_Jogo(fv);
        Toast.makeText(getApplicationContext(),"Favoritado com sucesso",Toast.LENGTH_SHORT).show();*/
    }

    public void onClickInsertAdd_Jogo(View v){
        /*//Intent i = getIntent();
        bd = new bdJogo(getApplicationContext());
        lg = new bdLogin(this);
        Login login;
        login = lg.buscarLogin();
        //jogo = bd.buscarJogo(""+i.getStringExtra("id"));

        Add_Jogo add = new Add_Jogo();
        add.setAj_jog_id(jogo.getJog_id());
        add.setAj_cont_id(login.getCont_id());
        bd_aj.insertAdd_Jogo(add);
        Toast.makeText(getApplicationContext(),"Adicionado com sucesso",Toast.LENGTH_SHORT).show();*/
    }

    public void iniciarPerfil(){
        Intent i = getIntent();
        Intent intent = new Intent(getApplicationContext(), Perfil_Usuario.class);
        intent.putExtra("cont_id",""+i.getStringExtra("cont_id"));
        startActivity(intent);
    }

    public void iniciarPesquisar(){
        Intent intent = new Intent(getApplicationContext(),Pesquisa_Activity.class);
        startActivity(intent);
    }

    public void sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setMessage("Você sairá da sua conta");
        builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                //lg = new bdLogin(getApplicationContext());
                //lg.deleteLogin(1);
                Intent intent = new Intent(Game_Activity.this, MainActivity.class);
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

    public void home(){
        Intent intent = new Intent(Game_Activity.this,Main_Games.class);
        startActivity(intent);

    }



}
