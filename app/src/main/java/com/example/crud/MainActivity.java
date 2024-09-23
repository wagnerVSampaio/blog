package com.example.crud;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //comunicar com bd
    private Database db;
    //lista dos dados componente android
    private ListView ListViewPosts;
    //array para guardar as listas ao inves de jogar no logcat
    private ArrayList<String> titulosPosts;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// chmando layout
//          EdgeToEdge.enable(this);
//          setContentView(R.layout.activity_main);
//          ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//              Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//              v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//              return insets;
//          });
        setContentView(R.layout.activity_main);

        db = new Database(this);
        titulosPosts = new ArrayList<>();
        ListViewPosts = findViewById(R.id.ListViewPosts);

        carregaPosts(db);

//Evento para redirecionar para a view completa do post
        ListViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //colocar aqui o codigo de chamada para a view do post completo
            }
        });

//insere dados no bd
//            db.inserePost("Segundo post", "Texto do Segundo post");
//            db.inserePost("Terceiro post", "Texto do terceiro postihbfi hiuwehwir rehhriu ehr h");
// chama o método
//           db.getPosts();

//          db.updatePost(1,"segundo atualizado", "Texto atualizado do segundo post");
//          db.getPosts();

//            db.deletePost(1);
//            db.getPosts();
    }
    //getpost recuperar
    public void carregaPosts(Database db){
        Cursor cursor = db.getPosts();
        if(cursor != null & cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                @SuppressLint("Range") String post = cursor.getString(cursor.getColumnIndex("post"));
                String texto = titulo + "\n" + resumoPost(post);
                titulosPosts.add(texto);
            }while(cursor.moveToNext());
            cursor.close();

            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,
                    R.layout.item_post,
                    R.id.textVieewTitulo,
                    titulosPosts);
            ListViewPosts.setAdapter(adaptador);
        }
    }
    public String resumoPost(String post){
        String[] palavrasDoPost = post.split(" ");
        if(palavrasDoPost.length > 5){
            return String.join(" ", Arrays.copyOfRange(palavrasDoPost, 1, 5)) + "...";
        }else{
            return post;
        }
    }
}