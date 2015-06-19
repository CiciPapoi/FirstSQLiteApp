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
    private RandomGenerator randomGenerator;
    private Bitmap rotatedBitmap1, rotatedBitmap2, rotatedBitmap3, rotatedBitmap4;
    private long img1Id,img2Id,img3Id,img4Id;
    boolean itemsFor1,itemsFor2,itemsFor3, itemsFor4;
    Intent myIntent1;


    //var for Logcat
    private static final String TAG = FirstActivity.class.getSimpleName();

    //UI elements
    ImageView imageview1, imageview2, imageview3, imageview4;
    Button addButton, listItemsButton, getOutfitButton;

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
//        rotatedBitmap1 = null;

        /*
            Get the elements from layout
         */

        imageview1 = (ImageView) findViewById(R.id.image1);
        imageview2 = (ImageView) findViewById(R.id.image2);
        imageview3 = (ImageView) findViewById(R.id.image3);
        imageview4 = (ImageView) findViewById(R.id.image4);
        addButton = (Button) findViewById(R.id.firstAddButton);

        getOutfitButton = (Button) findViewById(R.id.getOutfit);



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
        imageview1.setClickable(true);



        getOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start ItemsList class
                myIntent1 = new Intent( FirstActivity.this, Outfit.class); // (action to be performed, data to operate with)
                myIntent1.putExtra("image1Bmp",rotatedBitmap1);
                myIntent1.putExtra("image2Bmp", rotatedBitmap2);
                myIntent1.putExtra("image3Bmp", rotatedBitmap3);
                myIntent1.putExtra("image4Bmp", rotatedBitmap4);
                myIntent1.putExtra("image1Id", img1Id);
                myIntent1.putExtra("image2Id", img2Id);
                myIntent1.putExtra("image3Id", img3Id);
                myIntent1.putExtra("image4Id", img4Id);
                myIntent1.putParcelableArrayListExtra("array", arrayOfItems);  // ("array", arrayOfItems);
                startActivity(myIntent1);
            }
        });


    }

    public void clickImage1(View v){
        if(itemsFor1 == false){
        Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
        startActivity(myIntent1);
        }
    }
    public void clickImage2(View v){
        if(itemsFor2 == false){
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);
        }
    }
    public void clickImage3(View v){
        if(itemsFor3 == false){
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);
        }
    }
    public void clickImage4(View v){
        if(itemsFor4 == false){
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);
        }
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
                                itemsFor1 = false;
//                                imageview1.setImageResource(R.drawable.);
                            } else {    // if i have have items of categ: upper side ( id 4 / 6)
                                itemsFor1 = true;
                                while (item.getCategory_id() != 4 && item.getCategory_id() != 6)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap1 = convertor.getBmpRotated(item, bmp);
                                imageview1.setImageBitmap(rotatedBitmap1);
                                img1Id = item.get_id();
                            }


                            // Fill imageview2

                            ArrayList<Integer> list2 = new ArrayList<Integer>();
                            list2.add(new Integer(1));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems);
                            if (convertor.existsCategory(list2,arrayOfCategories) == false) {
                                itemsFor2 = false;
                            } else {
                                itemsFor2 = true;
                                while (item.getCategory_id() != 1)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap2 = convertor.getBmpRotated(item, bmp);
                                imageview2.setImageBitmap(rotatedBitmap2);
                                img2Id = item.get_id();
                            }

                            // Fill imageview3

                            ArrayList<Integer> list3 = new ArrayList<Integer>();
                            list3.add(new Integer(2));
                            list3.add(new Integer(3));
                            list3.add(new Integer(5));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems );
                            if (convertor.existsCategory(list3,arrayOfCategories) == false) {
                                itemsFor3 = false;
                            } else {
                                itemsFor3 = true;
                                while (item.getCategory_id() != 2 && item.getCategory_id() != 3 && item.getCategory_id() != 5)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap3 = convertor.getBmpRotated(item, bmp);
                                imageview3.setImageBitmap(rotatedBitmap3);
                                img3Id = item.get_id();
                            }

                            // Fill imageview4

                            ArrayList<Integer> list4 = new ArrayList<Integer>();
                            list4.add(new Integer(9));
                            //try getting a first random item and then check if it is of desired category
                            item = randomGenerator.anyItem(arrayOfItems);
                            if (convertor.existsCategory(list4, arrayOfCategories) == false) {
                                itemsFor4 = false;
                            } else {
                                itemsFor4 = true;
                                while (item.getCategory_id() != 9)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                bmp = convertor.pathToBmp(item.getPhoto_path());
                                rotatedBitmap4 = convertor.getBmpRotated(item, bmp);
                                imageview4.setImageBitmap(rotatedBitmap4);
                                img4Id = item.get_id();
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
