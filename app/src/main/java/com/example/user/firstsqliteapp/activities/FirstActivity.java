package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.Category;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;
import com.example.user.firstsqliteapp.database.ImageAdapter;
import com.example.user.firstsqliteapp.utils.ConversionFct;
import com.example.user.firstsqliteapp.utils.RandomGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 02.06.2015.
 */
public class FirstActivity extends Activity implements DatabaseOperationStatus {

    private ArrayList<Item> arrayOfItems;
    private ArrayList<Category> arrayOfCategories;
    ConversionFct convertor;
    RandomGenerator randomGenerator;
    Bitmap rotatedBitmap;




    //var for Logcat
    private static final String TAG = FirstActivity.class.getSimpleName();

    //UI elements
    ImageView imageview1, imageview2, imageview3, imageview4 ;
    Button addButton, listItemsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Apply layout
        setContentView(R.layout.first_grid);

        arrayOfItems = new ArrayList<Item>();
        arrayOfCategories = new ArrayList<Category>();

        //initialize help functions objects
        convertor = new ConversionFct();
        randomGenerator = new RandomGenerator();
        rotatedBitmap = null;

        /*
            Get the elements from layout
         */

        imageview1 = (ImageView) findViewById(R.id.image1);
        imageview2 = (ImageView) findViewById(R.id.image2);
        imageview3 = (ImageView) findViewById(R.id.image3);
        imageview4 = (ImageView) findViewById(R.id.image4);
        addButton = (Button) findViewById(R.id.firstAddButton);



        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(FirstActivity.this, CameraActivity.class);
                startActivity(myIntent);

            }

        });

        listItemsButton= (Button) findViewById(R.id.listItemsButton);
        listItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start ItemsList class
                Intent myIntent = new Intent( FirstActivity.this, ItemsListActivity.class); // (action to be performed, data to operate with)
                startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
            Get all Category elements from db and put them in the ArrayList
         */
        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Category.class), this);


        /*
            Get all Item elements from db and put them in the ArrayList
         */
        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Item.class), this);

        Log.d(TAG, "FirstActivity: onStart()");
    }



    @Override
    public void onComplete(Object result) {
    }

    @Override
    public void onComplete(final ArrayList result) {



        //---------------Items case----------------

        /*
              Fill the views with images
        */
            if (result.size() != 0) {
                Object o = result.get(0);
                //---------------Items case----------------
                if (o instanceof Item) {
                    arrayOfItems = result;
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Fill image1 ( upper side clothing items )
                            Item item;
                            Bitmap bmp;
                            ArrayList<Integer> list1 = new ArrayList<Integer>();
                            list1.add(new Integer(4));
                            list1.add(new Integer(6));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems);
                            if (convertor.existsCategory(list1, arrayOfCategories) == false) {

                            } else {
                                while (item.getCategory_id() != 4 && item.getCategory_id() != 6)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap = convertor.getBmpRotated(item, bmp);
                                imageview1.setImageBitmap(rotatedBitmap);

                            }

                            // Fill imageview2

                            ArrayList<Integer> list2 = new ArrayList<Integer>();
                            list2.add(new Integer(1));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems);
                            if (convertor.existsCategory(list2,arrayOfCategories) == false) {

                            } else {
                                while (item.getCategory_id() != 1)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap = convertor.getBmpRotated(item, bmp);
                                imageview2.setImageBitmap(rotatedBitmap);

                            }

                            // Fill imageview3

                            ArrayList<Integer> list3 = new ArrayList<Integer>();
                            list3.add(new Integer(2));
                            list3.add(new Integer(3));
                            list3.add(new Integer(5));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems );
                            if (convertor.existsCategory(list3,arrayOfCategories) == false) {

                            } else {
                                while (item.getCategory_id() != 2 && item.getCategory_id() != 3 && item.getCategory_id() != 5)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap = convertor.getBmpRotated(item, bmp);
                                imageview3.setImageBitmap(rotatedBitmap);

                            }

                            // Fill imageview4

                            ArrayList<Integer> list4 = new ArrayList<Integer>();
                            list4.add(new Integer(9));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems);
                            if (convertor.existsCategory(list4, arrayOfCategories) == false) {

                            } else {
                                while (item.getCategory_id() != 9)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap = convertor.getBmpRotated(item, bmp);
                                imageview1.setImageBitmap(rotatedBitmap);

                            }

                        }
                    });
                }//end if instanceof
            else if (o instanceof Category){
                //-----------------Category case----------------
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayOfCategories = result;

                        }
                    });

            }
            }

    }

    @Override
    public void onError(Throwable error) {

    }


}
