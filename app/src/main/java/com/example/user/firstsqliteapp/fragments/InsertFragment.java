package com.example.user.firstsqliteapp.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.firstsqliteapp.activities.MainActivity;
import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.data.UsersAdapter;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;
import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;

import java.util.ArrayList;
import java.util.Dictionary;
import android.content.pm.ActivityInfo;
import android.app.Activity;


public class InsertFragment extends Fragment implements DatabaseOperationStatus {
    private Button button;
    private DatabaseOpenHelper myDbHelper;
    private EditText username_edt, initials_edt, address_edt;
    InsertFragment instance = this;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDbHelper = DatabaseOpenHelper.getInstance(this.getActivity());
        View v = inflater.inflate(R.layout.fragment_insert, container, false);
        button = (Button) v.findViewById(R.id.add_button_id);
        username_edt = (EditText) v.findViewById(R.id.username);
        initials_edt = (EditText) v.findViewById(R.id.initials);
        address_edt = (EditText) v.findViewById(R.id.address);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = username_edt.getText().toString();
                String address = address_edt.getText().toString();
                String initials = initials_edt.getText().toString();
//
                // myDbHelper.insertUser(username, initials,address);
                Toast.makeText(v.getContext(), "User : " + username + " added!", Toast.LENGTH_LONG).show();

                User u = new User (username, address,initials);
                DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(User.class),
                        u, InsertFragment.this);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onComplete(Object result) {
        final User res = (User) result;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String username = username_edt.getText().toString();
                String address = address_edt.getText().toString();
                String initials = initials_edt.getText().toString();

               // myDbHelper.insertUser(username, initials,address);

                Toast.makeText(getActivity(), "User : " + username + " added!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onComplete(ArrayList result) {
        final ArrayList<Dictionary> res = (ArrayList<Dictionary>) result;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String username = username_edt.getText().toString();
                String address = address_edt.getText().toString();
                String initials = initials_edt.getText().toString();

                Toast.makeText(getActivity(), "User : " + username + " added!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onError(Throwable error) {

    }
}
