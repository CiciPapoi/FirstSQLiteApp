package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.Category;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 27.05.2015.
 */
public class CameraActivity extends Activity implements DatabaseOperationStatus{
    private static final int REQUEST_IMAGE = 100;
    private static final String TAG = "CameraActivity";

    TextView text_view_path;
    ImageView picture;
    File destination;
    String image_path;
    String name_date;

    private int category_bd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        text_view_path = (TextView) findViewById(R.id.idTvPath);
        picture = (ImageView) findViewById(R.id.idIvImage);
        name_date = dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");
        destination = new File(Environment.getExternalStorageDirectory(), name_date + ".jpg");

        Button click = (Button) findViewById(R.id.idBtnTakePicture);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK ){


            //                /*
//                    AlertDialog for COLOURS
//                 */
//
//                Dialog dialog;
//                final String[] colours = {"black", "blue", "brown", "green", "orange","purple", "red","white", "turquoise"};
//                final String[] categories = { "Accessories","Blouses","Dresses","Jackets&Coats","Tops","Trousers","Shorts","Shoes","Skirts"};
//                final String[] styles = {"Casual","Elegant","Office","Sport"};
//                final ArrayList itemsSelected = new ArrayList();
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Select Colour : ");
//                builder.setMultiChoiceItems(categories, null,
//                        new DialogInterface.OnMultiChoiceClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int selectedItemId,
//                                                boolean isSelected) {
//                                if ( isSelected ) {
//                                    itemsSelected.add(selectedItemId);
//                                } else if (itemsSelected.contains(selectedItemId)) {
//                                    itemsSelected.remove(Integer.valueOf(selectedItemId));
//                                }
//                            }
//                        })
//                        .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                            } })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                            }
//                        });
//                dialog = builder.create();
//                dialog.show();
            //-------------- Colours Dialog part ends

                    /*
                            Dialog for Category
                    */

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons

            builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Set other dialog properties
            final String[] categoriesArray = { "Accessories","Blouses","Dresses","Jackets&Coats","Tops","Trousers","Shorts","Shoes","Skirts"};

            builder.setTitle(R.string.pick_category)
                    .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {                    //R.array.categories_array
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch(which) {
                                case 0:
                                    category_bd = 1;
                                    break;
                                case 1:
                                    category_bd = 6;
                                    break;
                                case 2:
                                    category_bd = 8;
                                    break;
                                case 3:
                                    category_bd = 7;
                                    break;
                                case 4:
                                    category_bd = 4;
                                    break;
                                case 5:
                                    category_bd = 2;
                                    break;
                                case 6:
                                    category_bd = 3;
                                    break;
                                case 7:
                                    category_bd = 9;
                                    break;
                                case 8:
                                    category_bd = 5;
                                    break;
                                case 9:
                                    category_bd = 2;
                                    break;
                            }



                            //build the item to be added
                            FileInputStream in = null;
                            try {
                                in = new FileInputStream(destination);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 10;
                            image_path = destination.getAbsolutePath();
                            text_view_path.setText(image_path);
                            Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
                            picture.setImageBitmap(bmp);
                            //Create the object
                            Item newItem = new Item(category_bd, 1, name_date, null, image_path);

//                            MyApp myapp = MyApp.getInstance();
//                            myapp.myGlobalArray.add(category_bd);


                            //Insert the item in database
                            DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class), newItem, CameraActivity.this);

                            //Update category in database
                            Category oldCat = new Category(category_bd);

                            DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Category.class), oldCat ,null,  CameraActivity.this);

                        }
                    });


            // Create the AlertDialog
            AlertDialog categ_dialog = builder.create();

            categ_dialog.show();

        }
        else{
            text_view_path.setText("Request cancelled");
        }
    }
    public String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
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
