package com.example.polo.techsales2_0;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.polo.techsales2_0.bean.Jogo;

/**
 * Created by LUCASP on 07/06/2017.
 */

public class MeuViewHolder extends RecyclerView.ViewHolder {

    final TextView titulo;
    final TextView data;
    final ImageView ivMini;
    final ImageButton iBFav;
    final View view;


    public MeuViewHolder(final View itemView) {
        super(itemView);
        view = itemView;
        titulo = (TextView) itemView.findViewById(R.id.txTitulo);
        data = (TextView) itemView.findViewById(R.id.txData);
        ivMini = (ImageView) itemView.findViewById(R.id.iVMini);
        iBFav = (ImageButton) itemView.findViewById(R.id.iBFav);




    }


    public void bind(final Jogo jogo, final MeuAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(jogo);
            }
        });
    }
}
