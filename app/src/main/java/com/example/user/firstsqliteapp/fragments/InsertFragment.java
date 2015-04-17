package com.example.user.firstsqliteapp.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;
import com.example.user.firstsqliteapp.R;

public class InsertFragment extends Fragment {
    private Button button;
    private DatabaseOpenHelper myDbHelper;
    private EditText username_edt, initials_edt, address_edt;
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

                myDbHelper.insertUser(username, initials,address);
                Toast.makeText(v.getContext(), "User : " + username + " added!", Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return v;

    }

}
