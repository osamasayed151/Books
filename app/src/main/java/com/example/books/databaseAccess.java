package com.example.books;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class databaseAccess{
    SQLiteDatabase database;
    SQLiteOpenHelper openHelper;
    private static databaseAccess instance;

    private databaseAccess(Context context){
        this.openHelper = new myDatabase( context );
    }
    public static databaseAccess getinstance(Context context){
        if (instance == null){
            instance = new databaseAccess( context );
        }
        return instance;
    }
    public void open(){
        database = openHelper.getWritableDatabase();
    }
    public void close(){
        if (database != null){
            database.close();
        }
    }

    public boolean insert(Book book){
        ContentValues values = new ContentValues(  );
        values.put(myDatabase.NAME,book.getName());
        values.put( myDatabase.AUTHOR,book.getAuthor() );
        values.put( myDatabase.DEPARTMENT,book.getDepartment() );
        values.put( myDatabase.LANGUAGE,book.getLanguage() );
        values.put( myDatabase.PUBLISHER,book.getPublisher() );
        values.put( myDatabase.PAGES, book.getPages() );
        values.put( myDatabase.IAMGE, book.getImage() );
        values.put(myDatabase.RATING, book.getRating());

        long res = database.insert( myDatabase.TB_NAME,null,values );
        return res != -1;
    }

    public boolean update(Book book){
        ContentValues values = new ContentValues(  );
        values.put(myDatabase.ID,book.getId());
        values.put(myDatabase.NAME,book.getName());
        values.put( myDatabase.AUTHOR,book.getAuthor() );
        values.put( myDatabase.DEPARTMENT,book.getDepartment() );
        values.put( myDatabase.LANGUAGE,book.getLanguage() );
        values.put( myDatabase.PUBLISHER,book.getPublisher() );
        values.put( myDatabase.PAGES, book.getPages() );
        values.put( myDatabase.IAMGE, book.getImage() );
        values.put(myDatabase.RATING,book.getRating());

        String[] args = {String.valueOf(book.getId())};
        int res = database.update( myDatabase.TB_NAME,values,"id = ?",args );
        return res > 0;
    }

    public boolean delete(Book book) {

        String[] args = {String.valueOf( book.getId() )};
        int res = database.delete( myDatabase.TB_NAME, "id = ?", args );
        return res > 0;
    }

    public ArrayList<Book> getAllData(){
        ArrayList<Book> books = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from "+myDatabase.TB_NAME,null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt( cursor.getColumnIndex( myDatabase.ID ) );
                String name = cursor.getString( cursor.getColumnIndex( myDatabase.NAME ) );
                String author = cursor.getString( cursor.getColumnIndex( myDatabase.AUTHOR ) );
                String department = cursor.getString( cursor.getColumnIndex( myDatabase.DEPARTMENT ) );
                String language = cursor.getString( cursor.getColumnIndex( myDatabase.LANGUAGE ) );
                String publisher = cursor.getString( cursor.getColumnIndex( myDatabase.PUBLISHER ) );
                int pages = cursor.getInt( cursor.getColumnIndex( myDatabase.PAGES ) );
                String image = cursor.getString( cursor.getColumnIndex( myDatabase.IAMGE ) );
                float rating = cursor.getFloat(cursor.getColumnIndex(myDatabase.RATING));

                Book b = new Book( id, name, author, department, language, publisher, pages, image,rating) ;
                books.add( b );
            } while (cursor.moveToNext());
            cursor.close();
        }
            return books;
    }

    public ArrayList<Book> search(String itemSearch){
        ArrayList<Book> books = new ArrayList<>();
        String [] arr = {"%"+itemSearch+"%"};
        Cursor cursor = database.rawQuery("select * from "+myDatabase.TB_NAME+" where "+myDatabase.NAME+" like ?",arr);
        if (cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt( cursor.getColumnIndex( myDatabase.ID ) );
                String name = cursor.getString( cursor.getColumnIndex( myDatabase.NAME ) );
                String author = cursor.getString( cursor.getColumnIndex( myDatabase.AUTHOR ) );
                String department = cursor.getString( cursor.getColumnIndex( myDatabase.DEPARTMENT ) );
                String language = cursor.getString( cursor.getColumnIndex( myDatabase.LANGUAGE ) );
                String publisher = cursor.getString( cursor.getColumnIndex( myDatabase.PUBLISHER ) );
                int pages = cursor.getInt( cursor.getColumnIndex( myDatabase.PAGES ) );
                String image = cursor.getString( cursor.getColumnIndex( myDatabase.IAMGE ) );
                float rating = cursor.getFloat(cursor.getColumnIndex(myDatabase.RATING));

                Book b = new Book( id, name, author, department, language, publisher, pages, image, rating );
                books.add( b );
            }while (cursor.moveToNext());
            cursor.close();
        }
        return books;
    }

    public Book getBook(int itemId){
        Cursor cursor = database.rawQuery("select * from "+myDatabase.TB_NAME+" where "+myDatabase.ID+" = ?",new String[]{""+itemId});
        if (cursor != null && cursor.moveToFirst()){
            int id = cursor.getInt( cursor.getColumnIndex(myDatabase.ID));
            String name =cursor.getString(cursor.getColumnIndex( myDatabase.NAME ));
            String author = cursor.getString(cursor.getColumnIndex(myDatabase.AUTHOR));
            String department = cursor.getString( cursor.getColumnIndex(myDatabase.DEPARTMENT));
            String language = cursor.getString(cursor.getColumnIndex(myDatabase.LANGUAGE));
            String publisher = cursor.getString(cursor.getColumnIndex(myDatabase.PUBLISHER));
            int pages = cursor.getInt(cursor.getColumnIndex(myDatabase.PAGES));
            String image = cursor.getString(cursor.getColumnIndex(myDatabase.IAMGE));
            float rating = cursor.getFloat( cursor.getColumnIndex( myDatabase.RATING ) );

            Book b = new Book(id,name,author,department,language,publisher,pages,image, rating);
            cursor.close();
            return b;
        }
        return null;
    }
}
