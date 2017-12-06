package com.example.polo.techsales2_0;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import com.example.polo.techsales2_0.bean.Jogo;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

/**
 * Created by LUCASP on 07/06/2017.
 */

class MeuAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener{
        void onItemClick(Jogo jogo);
    }

    private List<Jogo> jogos;
    private Context context;
    private OnItemClickListener listener;
    private View view;

    public MeuAdapter(List<Jogo> jogos, Context context, OnItemClickListener listener,View view) {
        this.jogos = jogos;
        this.context = context;
        this.listener = listener;
        this.view = view;
    }

    public MeuAdapter(List<Jogo> jogos, Context context, OnItemClickListener listener) {
        this.jogos = jogos;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_view, parent, false);

        MeuViewHolder holder = new MeuViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        MeuViewHolder holder = (MeuViewHolder) viewHolder;

        Jogo jogo  = jogos.get(position);
        holder.bind(jogo,listener);
        holder.titulo.setText(jogo.getJoNome());
        holder.data.setText(jogo.getJoDataLanc());



        Uri uri = Uri.parse(jogo.getJoMini());
        SimpleDraweeView draweeView = (SimpleDraweeView)  holder.view.findViewById(R.id.iVMini);
        draweeView.setImageURI(uri);
        /*Picasso.with(context)
                .load(jogo.getJoMini())
                .placeholder(R.drawable.preloader)   // optional
                .error(R.drawable.no_image)      // optional
                //.resize(100, 100)                    // optional
                .fit()
                .centerCrop()
                .into(holder.ivMini);*/



    }

    @Override
    public int getItemCount() {
        try{
        return jogos.size();
        }catch (java.lang.NullPointerException e){
            Log.i("getItemCount","Erro -> "+e.getMessage());
        }
        return 0;
    }
}
