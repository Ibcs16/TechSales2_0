package com.example.polo.techsales2_0.bean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Polo on 03/12/2017.
 */

public interface IAdd_JogoREST {

        // Método para inserção na API, utiliza para isso
        // o método POST no endereço "baseUrl/livros/". O
        // objeto da classe Livro é convertido para o formato JSON.
        @POST("jogo/")
        Call<Void> insereJogo(@Body Jogo jogo);


        // Método para listagem de registros, utiliza para isso
        // o método GET no endereço "baseUrl/livros/". O retorno
        // JSON é convertido para objetos da classe Livro, e em
        // seguida adicionado a uma lista.

        @GET("jogo/")
        Call<List<Jogo>> getJogos();


        // Método para leitura de um determinado registro, utiliza
        // para isso o método GET no endereço "baseUrl/livros/{id}".
        // O retorno JSON é convertido para um objeto da classe Livro.
        @GET("jogo/{joId}")
        Call<Jogo> getJogoPorId(@Path("joId") String id);


        // Método para alteração de um registro, utiliza para isso o
        // método PUT no endereço "baseUrl/livros/{id}". O objeto da
        // classe Livro que representa o novo valor, é convertido para
        // JSON e então enviado à API.
        @PUT("jogo/{joId}")
        Call<Void> alteraJogo(@Path("joId") String id, @Body Jogo jogo);


        // Método para remoção de um registro, utiliza para isso o
        // método DELETE no endereço "baseUrl/livros/{id}". Então,
        // o registro JSON com id informado é excluido da API.
        @DELETE("jogo/{jogoId}")
        Call<Void> removeJogo(@Path("joId") String id);


        // Cria objeto Retrofit informando a URL base da API.
        // Utilizando o JSON_Server para simular uma API, executando
        // o app no emulador do Android Studio, utilize o endereço
        // "10.0.2.2:3000". Caso o app esteja rodando em seu smartphone
        // utilize o endereço IP dele, e esteja conectado na mesma rede
        // que o computador rodando a API. Por outro lado, caso esteja
        // consumindo uma API real, insira aqui o endereço de conexão.
        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.157:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


