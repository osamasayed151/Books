package com.example.books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Main_ACT extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private databaseAccess db;
    private RecyclerViewAdapter adapter;
    private Intent in;
    public static final int REQUESR_CODE = 1;
    private static final int ADD_BOOK_REQUSET_CODE= 1;
    private int EDIT_REQUSET_CODE_BOOK = 2;
    public static String BOOK_KEY = "book_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main__act );

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESR_CODE );
        }

        fab = findViewById( R.id.main_fab );
        rv = findViewById( R.id.main_rv );
        toolbar = findViewById( R.id.main_toolbar );
        setSupportActionBar( toolbar );

        db = databaseAccess.getinstance( this );
        db.open();
        ArrayList<Book> b = db.getAllData();
        db.close();
        adapter = new RecyclerViewAdapter( b, new onRecyclerViewItemClickListener() {
            @Override
            public void onClickListener(int itemId) {
                in = new Intent( getBaseContext(), View_Book_ACT.class );
                in.putExtra( BOOK_KEY, itemId );
                startActivityForResult( in, EDIT_REQUSET_CODE_BOOK );
            }
        } );
        rv.setAdapter( adapter );
        RecyclerView.LayoutManager rm = new GridLayoutManager( this, 1 );
        rv.setLayoutManager( rm );
        rv.setHasFixedSize( true );

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent( Main_ACT.this, View_Book_ACT.class );
                startActivityForResult( in, ADD_BOOK_REQUSET_CODE );
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu, menu );
        SearchView searchView = (SearchView) menu.findItem( R.id.search ).getActionView();
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              db.open();
              ArrayList<Book> b = db.search(newText);
              db.close();
              adapter.setBooks(b);
              adapter.notifyDataSetChanged();
              return false;

            }
        } );
        searchView.setOnCloseListener( new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                db.open();
                ArrayList<Book> b = db.getAllData();
                db.close();
                adapter.setBooks(b);
                adapter.notifyDataSetChanged();
                return false;
            }
        } );
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case REQUESR_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                } else {

                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == ADD_BOOK_REQUSET_CODE || requestCode == EDIT_REQUSET_CODE_BOOK) {
            db.open();
            ArrayList<Book> b = db.getAllData();
            db.close();
            adapter.setBooks(b);
            adapter.notifyDataSetChanged();
        }
    }
}