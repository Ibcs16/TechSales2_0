package com.example.polo.techsales2_0;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.polo.techsales2_0.bd.bdConsole;

import com.example.polo.techsales2_0.bd.bdGenero;
import com.example.polo.techsales2_0.bd.bdJogo;
import com.example.polo.techsales2_0.bd.bdLogin;
import com.example.polo.techsales2_0.bean.Console;
import com.example.polo.techsales2_0.bean.Conta;
import com.example.polo.techsales2_0.bean.Funcionario;
import com.example.polo.techsales2_0.bean.Genero;
import com.example.polo.techsales2_0.bean.Jogo;
import com.example.polo.techsales2_0.bean.Login;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by alunos on 19/09/17.
 */

public class Main_Games_Fragment extends android.support.v4.app.Fragment {

    //OnHeadlineSelectedListener mCallback;


    private bdJogo bd;
    private ShimmerFrameLayout container,container2;
    private List<Jogo> jogos;
    private List<Jogo> jogosLanc;
    private RecyclerView rV, rV2;


    private ImageButton reload1,reload2;



    private List<Login> logins;

    OnItemPressListener mCall;
    FirebaseFirestore db;



    private MeuAdapter adapter;

    public interface OnItemPressListener {
        public void onItemPressed(String id);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Log.i("onAttach","chamou, viu");
            mCall = (OnItemPressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnItemPressListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup group, final Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        final View view = inflater.inflate(R.layout.main_games_fragment, group, false);



        reload1 = (ImageButton) view.findViewById(R.id.reload1);
        reload2 = (ImageButton) view.findViewById(R.id.reload2);



        rV2 = (RecyclerView) view.findViewById(R.id.rVJog_Lanc);
        rV = (RecyclerView) view.findViewById(R.id.rVJog_Rec);

        container = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container1);
        container2 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container2);

        for(int i=0;i==1000;i++){

        }
        container.stopShimmerAnimation();
        container2.stopShimmerAnimation();

        db = FirebaseFirestore.getInstance();
        //loadJogosFireBase();
        //onClickInsertJogo(adapter, jogos) ;
        LoadJogos task = new LoadJogos();
        task.execute();

        return view;


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        container = (ShimmerFrameLayout) getView().findViewById(R.id.shimmer_view_container1);
        container2 = (ShimmerFrameLayout) getView().findViewById(R.id.shimmer_view_container2);

        container.startShimmerAnimation();
        container2.startShimmerAnimation();

        reload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadRV();

            }
        });
        reload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadRV2();
            }
        });


    }


    private class LoadJogos extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... strings) {
            try{
                loadJogosFireBase();
                return "Jogos baixados";
            }catch (Exception e){
                Log.i("LoadJogos","Erro -> "+e.getStackTrace());
                return "Download falhou";
            }

        }

        @Override
        protected void onPostExecute(String string){
            container.stopShimmerAnimation();
            container2.stopShimmerAnimation();
            setAdapter1();
            setAdapter2();
        }
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
                                Log.d("Jogo",""+jogo.toString());
                                jogos.add(jogo);
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                            }

                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });

        db.collection("jogo")
                //.whereEqualTo("joDataLanc","08/2015")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i("JogosLancMethod","Chegou no método");
                        if (task.isSuccessful()) {
                            jogosLanc = new ArrayList<Jogo>();
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

                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                Log.d("Firestore", ""+Integer.parseInt(""+jogo.getJoDataLanc()));
                                if((Integer.parseInt(""+jogo.getJoDataLanc())>=(2017-1))&&(Integer.parseInt(""+jogo.getJoDataLanc())<=(2017+1))){
                                    jogosLanc.add(jogo);
                                }

                            }
                            Log.d("Firestore", "Jogos: "+jogosLanc.toString());

                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });


    }


    private void setAdapter1(){

        try{
        rV.setAdapter(new MeuAdapter(jogos,getActivity(), new MeuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Jogo jogo) {
                if(jogo!=null){

                    Bundle bundl = new Bundle();

                    bundl.putString("id", jogo.getJoId());

                    bundl.putInt("estado",0);
                    Log.i("Id",""+jogo.getJoId());
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
        }catch (java.lang.NullPointerException e){

            Log.i("SetAdapter","Erro ->"+e.getMessage());

        }



    }

    public void setAdapter2(){

        try {
            rV2.setAdapter(new MeuAdapter(jogosLanc, getActivity(), new MeuAdapter.OnItemClickListener() {
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

            RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);

            rV2.setLayoutManager(layout);
            rV2.getAdapter().notifyDataSetChanged();
        }catch (java.lang.NullPointerException e){
            Log.i("SetAdapter2","Erro ->"+e.getMessage());
        }
    }


    //Arrumar reloads
    public void reloadRV(){
        /*db.collection("jogo")
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
                                jogos.add(jogo);
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                setAdapter1();
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });*/
        for(int i=0;i==1000;i++){

        }
        container.stopShimmerAnimation();
        container2.stopShimmerAnimation();
                LoadJogos task = new LoadJogos();
                task.execute();


    }



    public void reloadRV2(){
        /*db.collection("jogo")
                .whereEqualTo("joDataLanc","08/2015")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            jogosLanc = new ArrayList<Jogo>();
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



                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                Log.d("Firestore", ""+Integer.parseInt(""+jogo.getJoDataLanc()));
                                if((Integer.parseInt(""+jogo.getJoDataLanc())>=(2017-1))||(Integer.parseInt(""+jogo.getJoDataLanc())<=(2017+1))){
                                    jogosLanc.add(jogo);
                                }
                                setAdapter2();
                            }
                            Log.d("Firestore", "Jogos: "+jogosLanc.toString());

                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }

                    }

                });
                */

        LoadJogos task = new LoadJogos();
        task.execute();

    }





}
