package com.example.crud;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String NOME = "blog.db";
    private static final int VERSION = 1;

    private static final String TABELA_POSTS = "posts";
    private static final String  ID = "id";
    private static final String  TITULO= "titulo";
    private static final String POST = "post";

    private static final String CREATE_TABLE = "CREATE TABLE "+ TABELA_POSTS + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITULO + " TEXT, "+
            POST + " TEXT)";

    public Database(Context context){
        super(context, NOME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.i("Tabela", "Tabela de posts criada!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABELA_POSTS);
        onCreate(sqLiteDatabase);
    }

    public boolean inserePost(String titulo, String post){
        SQLiteDatabase db  = this.getWritableDatabase();

        ContentValues dados  = new ContentValues();

        dados.put(TITULO, titulo);
        dados.put(POST, post);

        long resultado  = db.insert(TABELA_POSTS,null, dados);
        String r = "Dados inseridos retornaram "+ resultado;
        if (resultado != -1){
            Log.i("resultado do insert", r);
        }
        db.close();

        return resultado != -1;
    }

    public Cursor getPosts(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor resultados = db.rawQuery(" SELECT * FROM "+ TABELA_POSTS,null);

        if (resultados != null){
            if (resultados.moveToFirst()){
                do{

                @SuppressLint("Range") int id = resultados.getInt(resultados.getColumnIndex(ID));
                @SuppressLint("Range") String titulo = resultados.getString(resultados.getColumnIndex(TITULO));
                @SuppressLint("Range") String post = resultados.getString(resultados.getColumnIndex(POST));
                Log.d("resultados","ID"+ id + ", Titulo "+ titulo + "Texto do post"+ post);
                }while (resultados.moveToNext());
            }
        }else {
            Log.e("getPost","não foram encontrado dados");
        }

        return  resultados;
    }

    public int updatePost(int id, String titulo, String post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put(TITULO, titulo);
        dados.put(POST, post);

        int resultado;
       resultado = db.update(TABELA_POSTS,dados, ID +"=?", new String[]{String.valueOf(id)});

        return resultado;
    };

    public boolean deletePost(int id){
        SQLiteDatabase db = this.getWritableDatabase();
      int retorno = db.delete(TABELA_POSTS, ID +"=?", new String[]{String.valueOf(id)});
      db.close();
        return retorno > 0;
    }

}