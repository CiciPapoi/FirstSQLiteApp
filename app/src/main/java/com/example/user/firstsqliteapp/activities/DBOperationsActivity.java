package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;
import com.example.user.firstsqliteapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;
import com.example.user.firstsqliteapp.fragments.InsertFragment;
import com.example.user.firstsqliteapp.fragments.SelectFragment;

/**
 * Created by user on 23.03.2015.
 */
public class DBOperationsActivity extends Activity implements DatabaseOperationStatus{
    private Button selectButton, insertButton, second_select_button;
    private DatabaseOpenHelper myDbHelper;

    // variables for Camera and Gallery functions
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;

    //var for Logcat
    private static final String TAG = DBOperationsActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_operations);

        /*
                INSERT button action    -- old
         */
        final String[] option = new String[] { "Take from Camera",
                "Select from Gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }
            }
        });


        final AlertDialog dialog = builder.create();

        insertButton = (Button)findViewById(R.id.insert_button_id);
        insertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // dialog.show();      //- dialog fot picture -> Blob
                //---------------OR WITH URI:---------------------
                //Start CameraActivity class
                Intent myIntent = new Intent( DBOperationsActivity.this, CameraActivity.class); // (action to be performed, data to operate with)
                startActivity(myIntent);
            }
        });

         /*
                SELECT button action
         */
        second_select_button = (Button) findViewById(R.id.second_select_button_id);
        second_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start ItemsList class
                Intent myIntent = new Intent( DBOperationsActivity.this, ItemsListActivity.class); // (action to be performed, data to operate with)
                startActivity(myIntent);
            }
        });
    }


//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != RESULT_OK)
//            return;
//
//        switch (requestCode) {
//            case CAMERA_REQUEST:
//
//                Bundle extras = data.getExtras();
//
//                if ( extras != null ) {
//                    Bitmap yourImage = extras.getParcelable("data");
//                    // convert bitmap to byte
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//                    byte imageInByte[] = stream.toByteArray();
//                   // Inserting Items
//                    Log.d(TAG, "Inserting ..");
//
//                    Item item = new Item (10,1,1,"12.01.2012", "12.01.2012","" );
//                    DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class),item,this);
//                    Intent i = new Intent(DBOperationsActivity.this, DBOperationsActivity.class);
//                    startActivity(i);
//                    finish();
//
//                }
//                break;
//            case PICK_FROM_GALLERY:
//                Bundle extras2 = data.getExtras();
//
//                if (extras2 != null) {
//                    Bitmap yourImage = extras2.getParcelable("data");
//                    // convert bitmap to byte
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte imageInByte[] = stream.toByteArray();
//                    Log.e(TAG, "before conversion"+imageInByte.toString());
//
//                    // Inserting Item
//                    Log.d("TAG", "Inserting from Gallery..");
//                    Item item = new Item (1,1,"12.01.2012", "12.01.2014","" );
//                    DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class),item,this);
//                    Intent i = new Intent(DBOperationsActivity.this,
//                            DBOperationsActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//
//                break;
//        }
//    }


    /**
     * open camera method
     */
    public void callCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    /**
     * open gallery method
     */

    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }


    /*
            FRAGMENTS OLD STUFF for Users table ( select & insert )
     */
    public void selectBetweenFragments(View view){
        Fragment fragment;
        if ( view == findViewById(R.id.select_button_id)){
            Log.d(TAG,"SELECT FRAGMENT");
            fragment = new SelectFragment();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place_id, fragment);
            fragmentTransaction.commit();
        }
        else {


            //fragment = new InsertFragment();  // old insert (users)
        }
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_place_id, fragment);
//        fragmentTransaction.commit();

    }


    @Override
    public void onComplete(Object result) {

    }

    @Override
    public void onComplete(ArrayList result) {

    }

    @Override
    public void onError(Throwable error) {

    }
}
