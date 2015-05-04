package com.example.user.firstsqliteapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;


public class MainActivity extends ActionBarActivity{
    Button start_up_button;
    //var for Logcat
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Main activity started!");
        start_up_button = (Button) findViewById(R.id.main_button_id);
        start_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start DbOperationsActivity class
                Intent myIntent = new Intent( MainActivity.this, DBOperationsActivity.class); // (action to be performed, data to operate with)
                startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
