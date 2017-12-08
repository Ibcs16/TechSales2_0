package com.example.polo.techsales2_0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
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
import com.facebook.shimmer.ShimmerFrameLayout;
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
    String pesquisa = "";
    FirebaseFirestore db;
    ShimmerFrameLayout container;
    private EditText campo;
    private RecyclerView rV;
    private List<Jogo> jogos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup group, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.pesquisa_jogo_fragment, group, false);
        campo = view.findViewById(R.id.etPesquisa);
        rV = view.findViewById(R.id.rVPesquisa);

        container = view.findViewById(R.id.shimmer_pesquisa);
        container.startShimmerAnimation();
        db = FirebaseFirestore.getInstance();

        loadJogosFireBase();
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        campo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (campo.getText().equals("")) {
                    loadJogosFireBase();
                    Log.i("Nome: ", "" + charSequence.toString());
                } else {
                    pesquisa = campo.getText().toString();
                    pesquisa();
                    Log.i("Nome: ", "" + charSequence.toString());

                }


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (campo.getText().equals("")) {
                    loadJogosFireBase();
                    Log.i("Nome: ", "" + charSequence.toString());
                } else {
                    Log.i("Nome: ", "" + charSequence.toString());
                    pesquisa = campo.getText().toString();
                    pesquisa();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (campo.getText().equals("")) {
                    loadJogosFireBase();
                } else {
                    pesquisa = campo.getText().toString();
                    pesquisa();
                }
            }


        });
    }

    private void loadJogosFireBase() {

        db.collection("jogo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            jogos = new ArrayList<Jogo>();
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
                                //Log.d("Jogo",""+jogo.toString());
                                jogos.add(jogo);
                                //Log.d("Firestore", document.getId() + " => " + document.getData());
                            }

                            setAdapter1();

                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });
    }

    private void pesquisaJogosFireBase() {

        db.collection("jogo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            jogos = new ArrayList<Jogo>();
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
                                //Log.d("Jogo",""+jogo.toString());
                                if (jogo.getJoNome().contains(pesquisa)) {
                                    jogos.add(jogo);
                                }
                                //Log.d("Firestore", document.getId() + " => " + document.getData());
                            }
                            setAdapter1();
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });
    }

    public void pesquisa() {

        PesquisaJogo task = new PesquisaJogo();
        task.execute();


    }

    private void setAdapter1() {

        try {
            rV.setAdapter(new MeuAdapter(jogos, getActivity(), new MeuAdapter.OnItemClickListener() {
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

            RecyclerView.LayoutManager layout1 = new GridLayoutManager(getActivity(),3);


            rV.setLayoutManager(layout1);
            rV.getAdapter().notifyDataSetChanged();
            container.stopShimmerAnimation();
        } catch (java.lang.NullPointerException e) {

            Log.i("SetAdapter", "Erro ->" + e.getMessage());

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            pesquisa = campo.getText().toString();
            pesquisa();

        } else {
            loadJogosFireBase();
        }

    }

    private class PesquisaJogo extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            try {
                pesquisaJogosFireBase();
                Log.i("LoadJogos", "carregou");
                return "Jogos baixados";
            } catch (Exception e) {
                Log.i("LoadJogos", "Erro -> " + e.getStackTrace());
                return "Download falhou";
            }

        }

        @Override
        protected void onPostExecute(String string) {
            container.stopShimmerAnimation();
            setAdapter1();
            Log.i("SetAdapter", "Carregou Adapter");
        }
    }
}

