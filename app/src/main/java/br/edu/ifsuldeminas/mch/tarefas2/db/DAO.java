package br.edu.ifsuldeminas.mch.tarefas2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
//Configuraçao
abstract class DAO<T> {
    private DBHandler dbHandler = null;

    DAO(Context context){
        if (dbHandler == null){
            dbHandler = new DBHandler(context);
        }
    }
    //Aberto para escrita
    SQLiteDatabase openToWrite(){
        SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
        return sqLiteDatabase;
    }
    //Aberto para leitura
    SQLiteDatabase openToRead(){
        return dbHandler.getReadableDatabase();
    }

    public abstract void save(T entity);
    public abstract void update(T entity);
    public abstract void delete(T entity);
    public abstract List<T> listAll();
}
