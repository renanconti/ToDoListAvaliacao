package br.edu.ifsuldeminas.mch.tarefas2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.tarefas2.domain.Category;
//extensão de DAO para categoria para o banco de dados
public class CategoryDAO extends DAO<Category> {

    public CategoryDAO(Context context){
        super(context);
    }


    @Override
    //Salvar a categoria
    public void save(Category entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", entity.getName());

        database.insert("categories", null, contentValues);

        database.close();
    }
    //Atualizacao da categoria
    @Override
    public void update(Category entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", entity.getName());

        String[] params = {entity.getId().toString()};
        database.update("categories", contentValues,
                "id = ?", params);

        database.close();
    }
    //Deletar/concluir a categoria
    @Override
    public void delete(Category entity) {
        SQLiteDatabase database = openToWrite();

        String[] params = {entity.getId().toString()};
        database.delete("categories", " id = ? ", params);

        database.close();
    }
    //Listagem das categorias pelo numero de id em criação
    @Override
    public List<Category> listAll() {
        SQLiteDatabase database = openToRead();
        List<Category> categories = new ArrayList<>();

        String sql = " SELECT * FROM categories ";

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name"));
            Category category = new Category(id, name);
            categories.add(category);
        }

        cursor.close();
        database.close();

        return categories;
    }

    public Category findById(Integer categoryId) {
        SQLiteDatabase database = openToRead();
        Category category = null;

        String sql = " SELECT * FROM categories WHERE id = ?; ";

        String[] params = {categoryId.toString()};

        Cursor cursor = database.rawQuery(sql, params);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name"));

            category = new Category(id, name);
        }

        cursor.close();
        database.close();

        return category;
    }
}
