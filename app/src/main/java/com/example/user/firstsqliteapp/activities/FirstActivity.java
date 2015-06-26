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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 02.06.2015.
 */
public class FirstActivity extends Activity implements DatabaseOperationStatus {

    private ArrayList<Item> arrayOfItems;
    private ArrayList<Category> arrayOfCategories;
    ConversionFct convertor;
    private RandomGenerator randomGenerator;
    private Bitmap rotatedBitmap1, rotatedBitmap2, rotatedBitmap3, rotatedBitmap4;
    private long img1Id, img2Id, img3Id, img4Id;
    boolean itemsFor1, itemsFor2, itemsFor3, itemsFor4;
    Intent myIntent1;
    Item item1, item2, item3, item4;
    long prev1, prev2, prev3, prev4;
    ArrayList<Integer> list2;

    String object = "";

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


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FirstActivity.this, CameraActivity.class);
                startActivity(myIntent);

            }

        });

        listItemsButton = (Button) findViewById(R.id.listItemsButton);
        listItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start ItemsList class
                Intent myIntent = new Intent(FirstActivity.this, ItemsListActivity.class); // (action to be performed, data to operate with)

                startActivity(myIntent);
            }
        });
        imageview1.setClickable(true);


        getOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start ItemsList class

              if ( itemsFor1 == true ) {
                  myIntent1 = new Intent(FirstActivity.this, Outfit.class); // (action to be performed, data to operate with)
                  myIntent1.putExtra("image1Bmp", rotatedBitmap1);
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
                else{
                  Toast t = Toast.makeText(FirstActivity.this, "Please choose a top for your outfit!",Toast.LENGTH_SHORT);
                  t.show();
              }
            }
        });
    }

    public void clickImage1(View v) {
        if (itemsFor1 == false) {
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);
        }

        else {
            //Compute nr of items of categ3
            int nrOfCateg1 = 0;
            for (Item i : arrayOfItems) {
                if (i.getCategory_id() == 4 || i.getCategory_id() == 6)
                    nrOfCateg1++;
            }
            if (nrOfCateg1 == 1 ){
                Toast toast = Toast.makeText(FirstActivity.this, "You have just one item for this part.\n Add more clothes for upper-side!", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                item1 = randomGenerator.anyItem(arrayOfItems);
                while (item1.getCategory_id() != 4 && item1.getCategory_id() != 6) {
                    item1 = randomGenerator.anyItem(arrayOfItems);
                }
                Bitmap bmp;
                bmp = convertor.pathToBmp(item1.getPhoto_path());
                rotatedBitmap1 = convertor.getBmpRotated(item1, bmp);
                imageview1.setImageBitmap(rotatedBitmap1);
                img1Id = item1.get_id();
            }
        }
    }

    public void clickImage2(View v) {
        if (itemsFor2 == false) {
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);
        }
        else {
            //Compute nr of items of categ3
            int nrOfCateg2 = 0;
            for (Item i : arrayOfItems) {
                if (i.getCategory_id() == 1 )
                    nrOfCateg2++;
            }
            if (nrOfCateg2 == 1 ){
                Toast toast = Toast.makeText(FirstActivity.this, "You have just one item for this part.\n Add more accessories!", Toast.LENGTH_SHORT);
                toast.show();
            }

            else {
                item2 = randomGenerator.anyItem(arrayOfItems);
                while (item2.getCategory_id() != 1)
                    item2 = randomGenerator.anyItem(arrayOfItems);
                Bitmap bmp;
                bmp = convertor.pathToBmp(item2.getPhoto_path());
                rotatedBitmap2 = convertor.getBmpRotated(item2, bmp);
                imageview2.setImageBitmap(rotatedBitmap2);
                img2Id = item2.get_id();
            }
        }
    }

    public void clickImage3(View v) {
        if (itemsFor3 == false) {
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);

        }
        else {
            //Compute nr of items of categ3
            int nrOfCateg3 = 0;
            for (Item i : arrayOfItems) {
                if (i.getCategory_id() == 2 || i.getCategory_id() == 3 || i.getCategory_id() == 5)
                    nrOfCateg3++;
            }
            if (nrOfCateg3 == 1 ){
                Toast toast = Toast.makeText(FirstActivity.this, "You have just one item for this part.\n Add more!", Toast.LENGTH_SHORT);
                toast.show();
            }
            item3 = randomGenerator.anyItem(arrayOfItems);
            while ( item3.getCategory_id() != 2 && item3.getCategory_id() != 3 && item3.getCategory_id() != 5 ) {
                    item3 = randomGenerator.anyItem(arrayOfItems);
            }
            Bitmap bmp;
            bmp = convertor.pathToBmp(item3.getPhoto_path());
            rotatedBitmap3 = convertor.getBmpRotated(item3, bmp);
            imageview3.setImageBitmap(rotatedBitmap3);
            img3Id = item3.get_id();
            prev3 = item3.get_id();

        }
    }

    public void clickImage4(View v) {
        if (itemsFor4 == false) {
            Intent myIntent1 = new Intent(FirstActivity.this, CameraActivity.class);
            startActivity(myIntent1);
        }
        else {
            //Compute nr of items of categ3
            int nrOfCateg4 = 0;
            for (Item i : arrayOfItems) {
                if (i.getCategory_id() == 9)
                    nrOfCateg4++;
            }
            if (nrOfCateg4 == 1 ){
                Toast toast = Toast.makeText(FirstActivity.this, "You have just one item for this part.\n Add more shoes!", Toast.LENGTH_SHORT);
                toast.show();
            }
            item4 = randomGenerator.anyItem(arrayOfItems);
            while ( item4.getCategory_id() != 9 ) {
                item4 = randomGenerator.anyItem(arrayOfItems);
            }
            Bitmap bmp;
            bmp = convertor.pathToBmp(item3.getPhoto_path());
            rotatedBitmap4 = convertor.getBmpRotated(item3, bmp);
            imageview4.setImageBitmap(rotatedBitmap4);
            img4Id = item4.get_id();
        }
    }


//    @Override
//    protected void onResume(){
//        super.onResume();
//    }

    @Override
    protected void onStart() {
        super.onStart();


        /*
            Get all Category elements from db and put them in the ArrayList
         */
        object= "category";
        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Category.class), this);


        /*
            Get all Item elements from db and put them in the ArrayList
         */
        object= "item";
        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Item.class), this);

        Log.d(TAG, "FirstActivity: onStart()");


    }


    @Override
    public void onComplete(Object result) {
    }


    public Item getItemWithId(String id, ArrayList<Item> array) {
        int idd = Integer.valueOf(id);
        for ( Item i : array)
            if (i.get_id() == idd)
                return i;
        return null;

    }


    @Override
    public void onComplete(final ArrayList result) {

        //---------------Items case----------------

        /*
              Fill the views with images
        */


        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.size() == 0) {
//                this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                    imageview1.setImageResource(R.mipmap.tops);
                    imageview2.setImageResource(R.mipmap.accessories2);
                    imageview3.setImageResource(R.mipmap.bottom);
                    imageview4.setImageResource(R.mipmap.shoes);
//                    }
//                });
//
                }

                if (result.size() != 0) {
                    Object o = result.get(0);
                    //---------------Items case----------------
                    if (o instanceof Item) {
                        arrayOfItems = result;
//                    this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                        //Fill image1 ( upper side clothing items )

                        Bitmap bmp;
                        ArrayList<Integer> list1 = new ArrayList<Integer>();
                        list1.add(new Integer(4));
                        list1.add(new Integer(6));
                        //try getting a first random item and then check if it is of desired category

                        if (convertor.existsCategory(list1, arrayOfCategories) == false) {
                            itemsFor1 = false;
                            imageview1.setImageResource(R.mipmap.tops);
                        } else {    // if i have have items of categ: upper side ( id 4 / 6)
                            item1 = randomGenerator.anyItem(arrayOfItems);
                            itemsFor1 = true;
                            while (item1.getCategory_id() != 4 && item1.getCategory_id() != 6)
                                item1 = randomGenerator.anyItem(arrayOfItems);
                            bmp = convertor.pathToBmp(item1.getPhoto_path());
                            rotatedBitmap1 = convertor.getBmpRotated(item1, bmp);
                            imageview1.setImageBitmap(rotatedBitmap1);
                            img1Id = item1.get_id();
                        }

                        // Fill imageview2

                        list2 = new ArrayList<Integer>();
                        list2.add(new Integer(1));
                        //try getting a first random item and then check if it is of desired category

                        if (convertor.existsCategory(list2, arrayOfCategories) == false) {
                            itemsFor2 = false;
                            imageview2.setImageResource(R.mipmap.accessories2);
                        } else {
//                                // we have for sure at least one piece of accessorize in db
                            item2 = randomGenerator.anyItem(arrayOfItems);
                            itemsFor2 = true;
                            while (item2.getCategory_id() != 1)
                                item2 = randomGenerator.anyItem(arrayOfItems);

                            bmp = convertor.pathToBmp(item2.getPhoto_path());
                            rotatedBitmap2 = convertor.getBmpRotated(item2, bmp);
                            imageview2.setImageBitmap(rotatedBitmap2);
                            img2Id = item2.get_id();
                            prev2 = img2Id;
                        }

                        // Fill imageview3

                        ArrayList<Integer> list3 = new ArrayList<Integer>();
                        list3.add(new Integer(2));
                        list3.add(new Integer(3));
                        list3.add(new Integer(5));


                        if (convertor.existsCategory(list3, arrayOfCategories) == false) {
                            itemsFor3 = false;
                            imageview3.setImageResource(R.mipmap.bottom);
                        } else {

                            //We have for sure at least one piece of pants or skirt in db
                            itemsFor3 = true;

                                /*
                                while (item.getCategory_id() != 2 && item.getCategory_id() != 3 && item.getCategory_id() != 5)
                                    item = randomGenerator.anyItem(arrayOfItems);
                                */

                            // Before, we took any random pair of pants / skirt, now we will check which fits
                            // with the chosen blouse ( with style and color ) by checking the matches
                            // done by the user till now.
                            ArrayList<Item> suggestions = new ArrayList<Item>();
                            Item itemFromMatches;
                            if (itemsFor1 == true)//check if exists an item of categ1, to match with
                            {
                                if (item1.getMatches().contains(","))  // we have previous matches
                                {
                                    for (Item i : arrayOfItems) {
                                        suggestions = new ArrayList<Item>();
                                        if (i.getCategory_id() == 2 || i.getCategory_id() == 3 || i.getCategory_id() == 5)// is of category 2
                                            for (String s : item1.getMatches().split(",")) {
                                                if (s != null) {
                                                    // find the item corresponding to current match
                                                    itemFromMatches = getItemWithId(s, arrayOfItems);
                                                    if (itemFromMatches != null)
                                                        if (i.getStyle_id() == itemFromMatches.getStyle_id())
                                                            suggestions.add(i);
                                                }
                                            }
                                    }

                                    Item finalSuggestion = new Item();
                                    // Now select from suggestions the item with the oldest last_date_worn
                                    if (suggestions.size() == 1) {
                                        finalSuggestion = suggestions.get(0);

                                    } else if (suggestions.size() == 0) { // we had matches, but no suggestions because the items does not exist anymore
                                        //try getting a first random item and then check if it is of desired category
                                        item3 = randomGenerator.anyItem(arrayOfItems);
                                        while (item3.getCategory_id() != 2 && item3.getCategory_id() != 3 && item3.getCategory_id() != 5)// is of category 2)
                                            item3 = randomGenerator.anyItem(arrayOfItems);
                                        suggestions.add(item3);

                                        //Fill the imageView now
                                        bmp = convertor.pathToBmp(item3.getPhoto_path());
                                        rotatedBitmap3 = convertor.getBmpRotated(item3, bmp);
                                        imageview3.setImageBitmap(rotatedBitmap3);
                                        img3Id = item3.get_id();
                                        prev3 = item3.get_id();

                                    } else {
                                        //String string = item1.getLast_used_date();
                                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.ENGLISH);
                                        //Date date = format.parse(string);

                                        // get current date
                                        Date currentDate = new Date();
                                        String currentDateString = format.format(currentDate);

                                        // Compute minimum date
                                        for (Item s : suggestions) {
                                            Date itemsDate = new Date();
                                            //get the date of each item from suggestions
                                            if (s.getLast_used_date() == null) {    // never worn it
                                                finalSuggestion = s;
                                                break;
                                            } else {      // compare the dates
                                                try {
                                                    itemsDate = format.parse(s.getLast_used_date());
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                                if (currentDate.after(itemsDate)) {
                                                    currentDate = itemsDate;
                                                    finalSuggestion = s;
                                                }
                                            }
                                        }
                                        //Fill the imageView now
                                        bmp = convertor.pathToBmp(finalSuggestion.getPhoto_path());
                                        rotatedBitmap3 = convertor.getBmpRotated(finalSuggestion, bmp);
                                        imageview3.setImageBitmap(rotatedBitmap3);
                                        img3Id = finalSuggestion.get_id();

                                    }




                                } else // no mathces existing yet
                                {
                                    //try getting a first random item and then check if it is of desired category
                                    item3 = randomGenerator.anyItem(arrayOfItems);
                                    while (item3.getCategory_id() != 2 && item3.getCategory_id() != 3 && item3.getCategory_id() != 5)// is of category 2)
                                        item3 = randomGenerator.anyItem(arrayOfItems);
                                    suggestions.add(item3);

                                    //Fill the imageView now
                                    bmp = convertor.pathToBmp(item3.getPhoto_path());
                                    rotatedBitmap3 = convertor.getBmpRotated(item3, bmp);
                                    imageview3.setImageBitmap(rotatedBitmap3);
                                    img3Id = item3.get_id();
                                    prev3 = item3.get_id();

                                }
                            }

                        }
                        // Fill imageview4

                        ArrayList<Integer> list4 = new ArrayList<Integer>();
                        list4.add(new Integer(9));
                        //try getting a first random item and then check if it is of desired category

                        if (convertor.existsCategory(list4, arrayOfCategories) == false) {
                            itemsFor4 = false;
                            imageview4.setImageResource(R.mipmap.shoes);
                        } else {
                            item4 = randomGenerator.anyItem(arrayOfItems);
                            itemsFor4 = true;
                            while (item4.getCategory_id() != 9)
                                item4 = randomGenerator.anyItem(arrayOfItems);
                            bmp = convertor.pathToBmp(item4.getPhoto_path());
                            rotatedBitmap4 = convertor.getBmpRotated(item4, bmp);
                            imageview4.setImageBitmap(rotatedBitmap4);
                            img4Id = item4.get_id();
                        }
//
//                        }
//                    });
                    }//end if instanceof
                    else if (o instanceof Category) {
                        //-----------------Category case----------------
//                    this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                        arrayOfCategories = result;
//                        }
//                    });
//
//            }
                    }  //last if instance
                }// if result.size
            }
        });
    }

    @Override
    public void onError(Throwable error) {

    }


}
