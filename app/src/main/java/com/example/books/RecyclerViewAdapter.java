package com.example.books;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.bookViewHoloder> {

    private ArrayList<Book> books;
    private onRecyclerViewItemClickListener listener;

    public RecyclerViewAdapter(ArrayList<Book> books, onRecyclerViewItemClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @Override
    public bookViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_book,null,false);
       bookViewHoloder bookViewHoloder = new bookViewHoloder(view);
       return bookViewHoloder;
    }
    @Override
    public void onBindViewHolder(@NonNull bookViewHoloder holder, int position) {
        Book book = books.get(position);
        if(book.getImage() != null && !book.getImage().isEmpty()) {
            holder.img.setImageURI( Uri.parse( book.getImage() ) );
        }
        else {
            holder.img.setImageResource(R.drawable.bookimg);
        }
        holder.txtdepartment.setText(book.getDepartment());
        holder.txtname.setText(book.getName());
        holder.txtauthor.setText(book.getAuthor());
        holder.ratingBar.setRating(book.getRating());
        holder.ratingBar.setIsIndicator(true);
        holder.itemId = (book.getId());


    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class bookViewHoloder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtname, txtauthor, txtdepartment;
        RatingBar ratingBar;
        int itemId;

        public bookViewHoloder(@NonNull View itemView) {
            super( itemView );
            img = itemView.findViewById(R.id.custom_img);
            txtname = itemView.findViewById(R.id.name);
            txtauthor = itemView.findViewById(R.id.author);
            txtdepartment = itemView.findViewById(R.id.department);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListener(itemId);
                }
            } );
        }
    }
}
