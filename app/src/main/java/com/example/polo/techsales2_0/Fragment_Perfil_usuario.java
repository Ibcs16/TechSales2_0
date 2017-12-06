package com.example.polo.techsales2_0;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polo.techsales2_0.bd.bdConta;
import com.example.polo.techsales2_0.bd.bdFav_Jogo;
import com.example.polo.techsales2_0.bd.bdFuncionario;
import com.example.polo.techsales2_0.bd.bdJogo;
import com.example.polo.techsales2_0.bd.bdLogin;
import com.example.polo.techsales2_0.bean.Conta;
import com.example.polo.techsales2_0.bean.Fav_Jogo;
import com.example.polo.techsales2_0.bean.Funcionario;
import com.example.polo.techsales2_0.bean.Jogo;
import com.example.polo.techsales2_0.bean.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alef on 26/10/17.
 */

public class Fragment_Perfil_usuario extends Fragment {

    private EditText nome,email;
    private ImageButton edit;
    private Button salvar;
    private RecyclerView rV;
    private List<Jogo> jogosFav;
    private List<Jogo> jogosAdd;
    FirebaseFirestore db;
    FirebaseUser user;
    ImageView imgPhoto;
    TextView qtd;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        salvar = (Button) view.findViewById(R.id.btnSalvar);
        nome = (EditText) view.findViewById(R.id.etNome);
        email = (EditText) view.findViewById(R.id.etEmail);
        qtd = (TextView) view.findViewById(R.id.txQtd);

        edit = (ImageButton) view.findViewById(R.id.ibEdit);
        rV = (RecyclerView) view.findViewById(R.id.rVFav);



        return view;


    }

    public void preencherUser(){

        String nameU = user.getDisplayName();
        String emailU = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        nome.setText(nameU);
        email.setText(emailU);

        Picasso.with(getContext())
                .load(photoUrl)
                .placeholder(R.drawable.preloader)   // optional
                .error(R.drawable.no_image)      // optional
                //.resize(100, 76)                    // optional
                //.fit()

                //.centerInside()
                .into(imgPhoto);



        db = FirebaseFirestore.getInstance();

        db.collection("jogoFav")
                .whereEqualTo("joUserKey",""+user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            jogosFav = new ArrayList<Jogo>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Jogo jogo = new Jogo();
                                jogo.setJoId(document.getId());
                                jogo.setJoConsole(document.getString("joConsole"));
                                jogo.setJoDataLanc(document.getString("joDataLanc"));
                                jogo.setJoGenero(document.getString("joGenero"));
                                jogo.setJoNome(document.getString("joNome"));
                                jogo.setJoDesc(document.getString("joDesc"));
                                jogo.setJoMini(document.getString("joMini"));
                                jogo.setJoPoster(document.getString("joPoster"));
                                Log.d("Jogo",""+jogo.toString());
                                jogosFav.add(jogo);
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                setAdapter1();
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });

        db.collection("jogoAdd")
                .whereEqualTo("joUserKey",""+user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            jogosFav = new ArrayList<Jogo>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Jogo jogo = new Jogo();
                                jogo.setJoId(document.getId());
                                jogo.setJoConsole(document.getString("joConsole"));
                                jogo.setJoDataLanc(document.getString("joDataLanc"));
                                jogo.setJoGenero(document.getString("joGenero"));
                                jogo.setJoNome(document.getString("joNome"));
                                jogo.setJoDesc(document.getString("joDesc"));
                                jogo.setJoMini(document.getString("joMini"));
                                jogo.setJoPoster(document.getString("joPoster"));
                                Log.d("Jogo",""+jogo.toString());
                                jogosAdd.add(jogo);
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                atualizaQtd(jogosAdd.size());
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });




    }


    public void atualizaQtd(int qtd){


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();

        preencherUser();




        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Editar",Toast.LENGTH_SHORT).show();
                onClickEditUsuario(nome,email);
            }
        });




        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               atualiza(nome.getText().toString(),email.getText().toString());


            }
        });
    }

    public void setAdapter1(){
        rV.setAdapter(new MeuAdapter(jogosFav,getActivity(), new MeuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Jogo jogo) {
                if(jogo!=null){




                    Bundle bundl = new Bundle();

                    bundl.putString("id", jogo.getJoId());
                    bundl.putInt("estado",1);

                    Log.i("Id",""+jogo.getJoId());
                    Game_Fragment fragGame = new Game_Fragment();
                    fragGame.setArguments(bundl);
                    getFragmentManager().beginTransaction().replace(R.id.flContentPerfil, fragGame, "Game_Fragment").addToBackStack(null).commit();


                }
            }
        }));

        RecyclerView.LayoutManager layout1 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);


        rV.setLayoutManager(layout1);
    }

    public  void atualiza(String nome,String email){
        /*
        user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileUpdates.Builder()
                .setDisplayName(nome)
                .setPhotoUri(Uri.parse(""))
                .build();



        user.updateProfile(profileUpdates)
                .addCompleteListener(new OnCompleteListener<Void>()){
                @Override
                public void onComplete(Task<Void> task){
                    if(task.isSuccessful()){
                        //reiniciarFragment
                    }

            }

        }*/



        /*email

         */

    }

    public void onClickEditUsuario(EditText nome, EditText email){
        nome.setEnabled(true);
        email.setEnabled(true);
        salvar.setVisibility(View.VISIBLE);


    }


    }
