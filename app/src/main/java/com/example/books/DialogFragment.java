package com.example.books;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DialogFragment extends androidx.fragment.app.DialogFragment {
    public static final String ARG_TITle ="title";
    public static final String ARG_MESSAGE ="message";
    public static final String ARG_ICON="icon";

    private String title;
    private String message;
    private int icon;

    onFragmentClickItemListener listener;

    public static DialogFragment newInstance(String title,String message,int icon){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITle,title);
        bundle.putString(ARG_MESSAGE,message);
        bundle.putInt(ARG_ICON,icon);
        DialogFragment df = new DialogFragment();
        df.setArguments(bundle);
        return df;
    }


    public DialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach( context );
        if (context instanceof onFragmentClickItemListener){
            listener = (onFragmentClickItemListener) context;
        }
        else{
            throw new ClassCastException("sorry, must be implements onFragmentClickItemListener from DialogFragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Bundle args = getArguments();
        if (args != null){
            title = args.getString(ARG_TITle);
            message = args.getString(ARG_MESSAGE);
            icon = args.getInt(ARG_ICON);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(icon);
        builder.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int num = -1;
                listener.onFragmentClick(num);
                dismiss();

            }
        } );
        builder.setNegativeButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int num = -2;
                listener.onFragmentClick(num);
                dismiss();
            }
        } );

        return builder.create();
    }

    public interface onFragmentClickItemListener{
        void onFragmentClick(int num);
    }
}
