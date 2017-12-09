package com.example.polo.techsales2_0;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polo.techsales2_0.bd.bdConta;
import com.example.polo.techsales2_0.bd.bdFav_Jogo;
import com.example.polo.techsales2_0.bd.bdJogo;
import com.example.polo.techsales2_0.bd.bdLogin;
import com.example.polo.techsales2_0.bean.Add_Jogo;
import com.example.polo.techsales2_0.bean.IAdd_JogoREST;
import com.example.polo.techsales2_0.bean.Jogo;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Polo on 29/10/2017.
 */

public class Game_Fragment extends Fragment {

    FirebaseFirestore db;
    int estado = 0;
    FirebaseUser user;
    String id = "";
    Button btnT;
    Button btCom, btSalva;
    String URL;
    //mStorageRef = FirebaseStorage.getInstance().getReference(); fica no oncreat (ja coloquei)
    StorageReference riversRef;
    ProgressDialog dialog;
    private bdLogin lg;
    private bdJogo bd;
    private bdFav_Jogo bd_fj;
    private bdConta bd_cont;
    private Jogo jogo;
    private SimpleDraweeView imgMini, imgPoster;
    private TextView nome, qtd, sobre, info;
    private Button add, fav;
    private String jogoN;
    //----Firebase
    private StorageReference mStorageRef;

    //Métodos firebaseDownLoad e firebaseUpload

    //---------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_jogo, container, false);
        add = view.findViewById(R.id.button2);
        fav = view.findViewById(R.id.button);
        btnT = view.findViewById(R.id.buttonTrailer);
        btCom = view.findViewById(R.id.buttonShare);
        btSalva = view.findViewById(R.id.btSalva);

        db = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.game_carregando));
        dialog.setCancelable(false);
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.show();

        try {
            estado = getArguments().getInt("estado");
            id = getArguments().getString("id");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        new CarregaJogoFireBase().execute();

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verTrailer1();
            }
        });

        btCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIMG();
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();


// Create a reference to "mountains.jpg"


// Create a reference to 'images/mountains.jpg'
        final StorageReference mountainsRef = mStorageRef.child("images/mini.jpg");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainsRef.getName());    // true
        mountainsRef.getPath().equals(mountainsRef.getPath());    // false

        btSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data from an ImageView as bytes
                imgMini.setDrawingCacheEnabled(true);
                imgMini.buildDrawingCache();
                Bitmap bitmap = imgMini.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getContext(),"Salvo com sucesso",Toast.LENGTH_LONG);
                        Log.i("Storage", "Deu certo");
                    }
                });
            }
        });



        //FirebaseUser user = FirebaseAuth.getInstance.getCurrentUser();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddJogoJson task = new AddJogoJson();
                task.execute();
                // Configura e exibe o ProgressDialog.
                dialog = new ProgressDialog(getContext());
                dialog.setMessage(getString(R.string.game_carregando_json));
                dialog.setCancelable(false);
                dialog.show();

            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> jogFav = new HashMap<>();

                jogFav.put("joDesc", "" + jogo.getJoDesc());
                jogFav.put("joNome", "" + jogo.getJoNome());
                jogFav.put("joDataLanc", "" + jogo.getJoDataLanc());
                jogFav.put("joConsole", "" + jogo.getJoConsole());
                jogFav.put("joGenero", "" + jogo.getJoGenero());
                jogFav.put("joMini", "" + jogo.getJoMini());
                jogFav.put("joPoster", "" + jogo.getJoPoster());
                jogFav.put("joUserKey", "" + user.getUid());

                db.collection("jogoFav")
                        .add(jogFav)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(), "Jogo favoritado com sucesso!", Toast.LENGTH_LONG).show();

                                //Log.d("firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Erro ao favoritar este jogo!", Toast.LENGTH_LONG).show();
                                Log.w("firestore", "Error adding document", e);
                            }
                        });
            }
        });


    }

    public void inicioJogo(String id) {
        Log.i("Fragment JoogoId ---- ", id);
        //pesquisa no firesbase pela key
        DocumentReference docRef;
        if (estado == 0) {
            docRef = db.collection("jogo").document(id);
        } else {
            docRef = db.collection("jogoFav").document(id);
        }

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                jogo = new Jogo();
                jogo.setJoId(document.getId());
                jogo.setJoConsole(document.getString("joConsole"));
                jogo.setJoDataLanc(document.getString("joDataLanc"));
                jogo.setJoGenero(document.getString("joGenero"));
                jogo.setJoNome(document.getString("joNome"));
                jogo.setJoDesc(document.getString("joDesc"));
                jogo.setJoMini(document.getString("joMini"));
                jogo.setJoPoster(document.getString("joPoster"));
                //Log.d("Fragment Jogo: ", "" + jogo.toString());
                //Toast.makeText(getContext(), "Encontrado = >" + document.getId(), Toast.LENGTH_LONG);

                if (jogo.getJoId() == null) {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().remove(getParentFragment());
                } else {
                    preenche(jogo);
                }
            }
        });


    }

    public void preenche(Jogo jogo) {


        imgMini = getView().findViewById(R.id.imgMini);
        imgPoster = getView().findViewById(R.id.imgPoster);


        Uri uri = Uri.parse(jogo.getJoMini());

        imgMini.setImageURI(uri);

        Uri uri1 = Uri.parse(jogo.getJoPoster());

        imgPoster.setImageURI(uri1);

        if (jogo == null) {
            Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
        } else {
            sobre = getView().findViewById(R.id.txSobre);
            info = getView().findViewById(R.id.txInfo);
            nome = getView().findViewById(R.id.txTitulo);
            //qtd = (TextView) findViewById(R.id.txQtd);
            nome.setText("" + jogo.getJoNome());
            sobre.setText("" + jogo.getJoDesc());
            info.setText(
                    ""+getString(R.string.game_data)+" " + jogo.getJoDataLanc()
                            + "\n" + getString(R.string.game_console)+" " + jogo.getJoConsole()
                            + "\n" + getString(R.string.game_genero)+" " + jogo.getJoGenero());
        }


    }

    public void setId(String id) {
        id = id;
    }

    //Método para acessar youtube
    public void verTrailer1() {
        jogoN = jogo.getJoNome();
        URL = "https://www.youtube.com/results?search_query=" + jogoN + "+trailer";
        watch_video(URL);

    }

    void watch_video(String url) {
        Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent chooser = Intent.createChooser(yt_play, "Open With");

        if (yt_play.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    //Método para compartilhar
    public void compartilhar() {
        jogoN = jogo.getJoDesc();
        //compartilharImagemeDescricao();

    }

    private void shareIMG() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, jogo.getJoMini());
        startActivity(intent);
    }

    //Compartilha texto e imagem
    public void compartilharImagemeDescricao() {


        // Bitmap adv = BitmapFactory.decodeResource(getResources(), jogo.getJoMini());

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //adv.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            new FileOutputStream(f).write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        share.putExtra(Intent.EXTRA_STREAM, Uri.parse
                (Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg"));

        share.putExtra(Intent.EXTRA_TEXT, jogoN);
        startActivity(Intent.createChooser(share, "Share Image"));


    }

    protected class CarregaJogoFireBase extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            inicioJogo(id);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            if(dialog.isShowing()) {
                dialog.setProgress(dialog.getProgress() + 1);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(dialog.isShowing()){
            dialog.dismiss();
            }
        }
    }

    private class AddJogoJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            IAdd_JogoREST iAdd_jogoREST = IAdd_JogoREST.retrofit.create(IAdd_JogoREST.class);

            // Chama de maneira assíncrona o método "insereLivro" da interface do Retrofit.
            final Call<Void> call = iAdd_jogoREST.insereJogo(jogo);
            call.enqueue(new Callback<Void>() {
                // Em caso de sucesso retorna mensagem de sucesso para o usuário.
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    Toast.makeText(getContext(), "Jogo inserido com sucesso", Toast.LENGTH_SHORT).show();
                }

                // Em caso de erro retorna mensagem de erro para o usuário.
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    Toast.makeText(getContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                }
            });

            return "";
        }


        @Override
        protected void onPostExecute(String result) {

        }

    }


}

