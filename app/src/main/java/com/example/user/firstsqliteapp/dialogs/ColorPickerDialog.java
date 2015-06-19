package com.example.user.firstsqliteapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.user.firstsqliteapp.R;

/**
 * Created by user on 16.06.2015.
 */
public class ColorPickerDialog extends Dialog {
       private int position;
    public ColorPickerDialog(Context context){
        super(context);
        this.setTitle("Pick a colour: ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker);

        GridView gridViewColors = (GridView) findViewById(R.id.gridViewColors);
        gridViewColors.setAdapter(new ColorPickerAdapter(getContext()));

             // close the dialog on item click
                gridViewColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ColorPickerDialog.this.dismiss();
                    }
        });
    }
}
