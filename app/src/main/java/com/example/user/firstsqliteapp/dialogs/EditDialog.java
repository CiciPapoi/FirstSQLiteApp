package com.example.user.firstsqliteapp.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;
import com.example.user.firstsqliteapp.fragments.SelectFragment;

import java.util.ArrayList;

/*
 * Created by Cici on 27.04.2015.
 */

public class EditDialog extends DialogFragment implements DatabaseOperationStatus {
    Button doneButton, cancelButton;
    EditText username_editText, address_editText;
    private static final String TAG = EditDialog.class.getSimpleName();
    EditDialog instance = this;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_dialog, null); /// null - independent activity
        doneButton = (Button) view.findViewById(R.id.done_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);

        username_editText = (EditText) view.findViewById(R.id.edit_username);
        address_editText = (EditText) view.findViewById(R.id.edit_address);

        String username_text = username_editText.getText().toString();
        String address_text = address_editText.getText().toString();


        final User new_user = new User();
        new_user.setUsername(username_text);
        new_user.setAddress(address_text);


        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseManager.getInstance().updateItem( DatabaseManager.getInstance().getTable(User.class),new_user , new_user, EditDialog.this );
                Toast.makeText(getActivity(),"Done button pressed",Toast.LENGTH_LONG).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               getDialog().dismiss();
            }
        });


        setCancelable(false);
        return view;
    }

    @Override
    public void onComplete(Object result) {
        //Toast.makeText(this.getActivity(), " Update complete! ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete(ArrayList result) {

    }

    @Override
    public void onError(Throwable error) {

    }
}
