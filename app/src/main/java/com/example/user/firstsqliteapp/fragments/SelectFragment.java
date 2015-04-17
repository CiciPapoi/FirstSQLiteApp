package com.example.user.firstsqliteapp.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;
import com.example.user.firstsqliteapp.R;


public class SelectFragment extends Fragment {
    private TextView viewAllList;

    private DatabaseOpenHelper myDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myDbHelper = DatabaseOpenHelper.getInstance(this.getActivity());


        Cursor cursor = myDbHelper.getAllUsers();

        cursor.moveToFirst();
        String text = "ID   NAME    INITIALS    ADDRESS \n";
        while (!cursor.isAfterLast()) {
            text +=  cursor.getInt(0)+"   "
                    + cursor.getString(1)+"    "
                    + cursor.getString(2)+"    "
                    + cursor.getString(3)+"\n";
            cursor.moveToNext();
        }
        cursor.close();
        //String text = getArguments().getString("list");

        View root = inflater.inflate(R.layout.fragment_select, container, false);
        viewAllList = (TextView) root.findViewById(R.id.select_fragment_title_id);
        viewAllList.append(text);

        return root;
    }




}
