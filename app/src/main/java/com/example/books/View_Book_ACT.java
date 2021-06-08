package com.example.books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;


public class View_Book_ACT extends AppCompatActivity implements DialogFragment.onFragmentClickItemListener {
    public static final int ADD_BOOK_RES_CODE = 3;
    public static final int EDIT_BOOK_RES_CODE = 4;
    private static final int PICK_IMAGE = 1;
    private TextInputEditText name,author, department, language, publisher, pages;
    private ImageView view_img;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private int bookId = -1;
    private Intent in;
    private databaseAccess db = databaseAccess.getinstance(this);
    private Uri imageUri;
    public static float rat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view__book__act );

        toolbar=findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        name=findViewById(R.id.view_txt_name);
        author=findViewById(R.id.view_txt_author);
        department=findViewById(R.id.view_txt_department);
        language=findViewById(R.id.view_txt_language);
        publisher=findViewById(R.id.view_txt_publisher);
        pages=findViewById(R.id.view_txt_pages);
        ratingBar = findViewById( R.id.ratingBar );
        view_img=findViewById(R.id.view_img);

        in = getIntent();
        bookId = in.getIntExtra(Main_ACT.BOOK_KEY,-1);
        if (bookId == -1){
           enableFields();
           clearFields();
        }
        else{
            displayFields();
            db.open();
            Book b =  db.getBook(bookId);
            db.close();
            if(b != null){
                fillFields(b);
            }

//            language.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText( View_Book_ACT.this, "this", Toast.LENGTH_SHORT ).show();
//                }
//            } );

        }
        ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rat = v;
            }
        } );
    }
    public void fillFields(Book b){
        if(b.getImage() != null && !b.getImage().isEmpty()) {
            view_img.setImageURI( Uri.parse( b.getImage() ) );
        }
        name.setText(b.getName());
        author.setText(b.getAuthor());
        department.setText(b.getDepartment());
        language.setText(b.getLanguage());
        publisher.setText(b.getPublisher());
        pages.setText(String.valueOf(b.getPages()));
        ratingBar.setRating(b.getRating());
    }
    public void displayFields(){
        name.setEnabled(false);
        author.setEnabled(false);
        department.setEnabled(false);
        language.setEnabled(false);
        publisher.setEnabled(false);
        pages.setEnabled(false);
        ratingBar.setIsIndicator(true);
    }
    public void enableFields(){
        name.setEnabled(true);
        author.setEnabled(true);
        department.setEnabled(true);
        language.setEnabled(true);
        publisher.setEnabled(true);
        pages.setEnabled(true);
        ratingBar.setIsIndicator(false);
    }
    public void clearFields(){
        name.setText("");
        author.setText("");
        department.setText("");
        language.setText("");
        publisher.setText("");
        pages.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu,menu);
        MenuItem save = menu.findItem(R.id.save);
        MenuItem delete = menu.findItem(R.id.delete);
        MenuItem edit = menu.findItem(R.id.edit);
        MenuItem photo = menu.findItem(R.id.add_image);
        if (bookId == -1){
            // Add
            save.setVisible(true);
            photo.setVisible(true);
            delete.setVisible(false);
            edit.setVisible(false);
        }
        else{
            // View
            save.setVisible(false);
            delete.setVisible(true);
            edit.setVisible(true);
            photo.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name, author, department, language, publisher, image = "";
        int pages;
        float rat;
        switch (item.getItemId()){
            case R.id.save:
                name = this.name.getText().toString();
                author = this.author.getText().toString();
                department = this.department.getText().toString();
                language = this.language.getText().toString();
                publisher = this.publisher.getText().toString();
                pages = Integer.parseInt(this.pages.getText().toString());
                rat = this.ratingBar.getRating();
                if(imageUri != null) {
                    image = imageUri.toString();
                }
                Book b = new Book(bookId,name,author,department,language,publisher,pages,image,rat);
                db.open();
                boolean res;
                if(bookId == -1){
                    res = db.insert(b);
                    if (res){
                        Toast.makeText( this, "Book added successfully", Toast.LENGTH_SHORT ).show();
                    }
                    setResult(ADD_BOOK_RES_CODE,null);
                    finish();
                }
                else{
                    res = db.update(b);
                    if (res){
                        Toast.makeText( this, "Book modified successfully", Toast.LENGTH_SHORT ).show();
                    }
                    setResult(EDIT_BOOK_RES_CODE,null);
                    finish();
                }
                db.close();
                return true;
            case R.id.delete:
                //DialogFragment de = DialogFragment.newInstance("confirm","The item will be deleted, are you sure?",R.drawable.ic_warning);
                //de.show(getSupportFragmentManager(),null);
                Dialog();

                return true;
            case R.id.edit:
                MenuItem save = toolbar.getMenu().findItem(R.id.save);
                MenuItem delete = toolbar.getMenu().findItem(R.id.delete);
                MenuItem edit = toolbar.getMenu().findItem(R.id.edit);
                MenuItem photo = toolbar.getMenu().findItem(R.id.add_image);
                save.setVisible(true);
                photo.setVisible(true);
                delete.setVisible(false);
                edit.setVisible(false);
                enableFields();
                return true;
            case R.id.add_image:
                in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "Select Picture"), PICK_IMAGE);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            if(data != null ){
                imageUri = data.getData();
                view_img.setImageURI(imageUri);
            }
        }
    }
    @Override
    public void onFragmentClick(int num) {

    }
    public void Dialog(){
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("confirm");
        builder.setMessage("The item will be deleted, are you sure?");
        builder.setIcon(R.drawable.ic_warning);
        builder.setPositiveButton( "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.open();
                Book b = new Book( bookId, null, null, null, null, null, 0, "", 0 );
                boolean res = db.delete( b );
                if (res) {
                    Toast.makeText( View_Book_ACT.this, "Done", Toast.LENGTH_SHORT ).show();
                    setResult( EDIT_BOOK_RES_CODE, null );
                    dialogInterface.cancel();
                    finish();
                }
                db.close();

            }
        } );
        builder.setNegativeButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             dialogInterface.cancel();
            }
        } );
        builder.show();

    }

}
