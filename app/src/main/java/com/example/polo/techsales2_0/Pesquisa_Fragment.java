package com.example.polo.techsales2_0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polo.techsales2_0.bd.bdJogo;
import com.example.polo.techsales2_0.bean.Jogo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Polo on 29/10/2017.
 */

public class Pesquisa_Fragment extends Fragment {
    private EditText campo;
    private bdJogo bd;
    private RecyclerView rV;
    private List<Jogo> jogos;
    MeuAdapter adapter;
    String pesquisa = "";
    FirebaseFirestore db;
    List<Jogo> encontrados;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.pesquisa_jogo_fragment,container,false);
        campo = (EditText) view.findViewById(R.id.etPesquisa);
        rV = (RecyclerView) view.findViewById(R.id.rVPesquisa);

        db = FirebaseFirestore.getInstance();
        inicioApp();
        return view;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState!=null){
        pesquisa = campo.getText().toString();
            Log.i("i","worked as a charm");
            Log.i("pega",campo.getText().toString());

        }else{


        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = FirebaseFirestore.getInstance();

        inicioApp();

        campo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (campo.getText().equals("")){
                    inicioApp();
                    Log.i("Nome: ",""+charSequence.toString());
                }else{
                    pesquisa(charSequence.toString());
                    Log.i("Nome: ",""+charSequence.toString());

                }


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (campo.getText().equals("")){
                    inicioApp();
                    Log.i("Nome: ",""+charSequence.toString());
                }else{
                    Log.i("Nome: ",""+charSequence.toString());
                    pesquisa(charSequence.toString());}



            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (campo.getText().equals("")){
                    inicioApp();
                }else{
                    pesquisa(campo.getText().toString());}
            }


        });
    }


public void inicioApp() {


    db.collection("jogo")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        encontrados = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            Jogo jogo = new Jogo();
                            jogo.setJoId(document.getId());
                            jogo.setJoConsole(document.getString("joConsole"));
                            jogo.setJoDataLanc(document.getString("joDataLanc"));
                            jogo.setJoGenero(document.getString("joGenero"));
                            jogo.setJoNome(document.getString("joNome"));
                            jogo.setJoDesc(document.getString("joDesc"));
                            jogo.setJoMini(document.getString("joMini"));
                            jogo.setJoPoster(document.getString("joPoster"));

                            encontrados.add(jogo);

                        }

                        setAdapter2();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());

                    }
                }
            });

}



public void pesquisa(String nome){

        db.collection("jogo")
                .whereEqualTo("joNome",nome)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        encontrados = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            Jogo jogo = new Jogo();
                            jogo.setJoId(document.getId());
                            jogo.setJoConsole(document.getString("joConsole"));
                            jogo.setJoDataLanc(document.getString("joDataLanc"));
                            jogo.setJoGenero(document.getString("joGenero"));
                            jogo.setJoNome(document.getString("joNome"));
                            jogo.setJoDesc(document.getString("joDesc"));
                            jogo.setJoMini(document.getString("joMini"));
                            jogo.setJoPoster(document.getString("joPoster"));
                            Log.i("Jogo: ",""+jogo.toString());
                            encontrados.add(jogo);

                        }

                        setAdapter2();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());

                    }
                }
            });

        }



        public void atualizaLista(List<Jogo> encontrados){


            adapter = new MeuAdapter(encontrados, getContext(), new MeuAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Jogo jogo) {
                    Bundle bundl = new Bundle();

                    bundl.putString("id", jogo.getJoId());
                    bundl.putInt("estado",0);

                    Game_Fragment fragGame = new Game_Fragment();
                    fragGame.setArguments(bundl);
                    getFragmentManager().beginTransaction().replace(R.id.flContent, fragGame).addToBackStack(null).commit();

                }
            });
            rV.setAdapter(adapter);
            adapter.notifyDataSetChanged();



        }

    public void setAdapter2() {

        rV.setAdapter(new MeuAdapter(encontrados, getActivity(), new MeuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Jogo jogo) {
                if (jogo != null) {


                    Bundle bundl = new Bundle();

                    bundl.putString("id", jogo.getJoId());
                    bundl.putInt("estado", 0);

                    Log.i("Id", "" + jogo.getJoId());
                    Game_Fragment fragGame = new Game_Fragment();
                    fragGame.setArguments(bundl);
                    getFragmentManager().beginTransaction().replace(R.id.flContent, fragGame, "Game_Fragment").addToBackStack(null).commit();


                }
            }
        }));

        RecyclerView.LayoutManager layout1 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);


        rV.setLayoutManager(layout1);
        rV.getAdapter().notifyDataSetChanged();
    }
}

