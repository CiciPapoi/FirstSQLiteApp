package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;
import com.example.user.firstsqliteapp.database.tables.ItemsTable;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 17.06.2015.
 */
public class Outfit extends Activity implements DatabaseOperationStatus{

    Button btnShare;
    ImageView imageView1, imageView2, imageView3, imageView4;
    long imageId1, imageId2,imageId3,imageId4;
    Bitmap imageBmp1, imageBmp2, imageBmp3,imageBmp4;
    private int current;
    private ArrayList<Item> arrayOfItems;

    Item item1 ,item2 ,item4 , item3;
    Item newitem;

    //var for Logcat
    private static final String TAG = Outfit.class.getSimpleName();


    public String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        arrayOfItems = new ArrayList<Item>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit);
        btnShare = (Button) findViewById(R.id.share);
        imageView1 = (ImageView) findViewById(R.id.outfit1);
        imageView2 = (ImageView) findViewById(R.id.outfit2);
        imageView3 = (ImageView) findViewById(R.id.outfit3);
        imageView4 = (ImageView) findViewById(R.id.outfit4);

        /**
         * getting intent data from search and previous screen
         */
        Intent intent = getIntent();
        imageBmp1 = (Bitmap) intent.getParcelableExtra("image1Bmp");
        imageBmp2 = (Bitmap) intent.getParcelableExtra("image2Bmp");
        imageBmp3 = (Bitmap) intent.getParcelableExtra("image3Bmp");
        imageBmp4 = (Bitmap) intent.getParcelableExtra("image4Bmp");

        imageId1 = intent.getLongExtra("image1Id", -1);
        imageId2 = intent.getLongExtra("image2Id", -1);
        imageId3 = intent.getLongExtra("image3Id", -1);
        imageId4 = intent.getLongExtra("image4Id", -1);

        /*
                Get the array of items
         */
        ArrayList<Item> ps =  intent.getParcelableArrayListExtra("array");
        int k = 0;
        for (Object i : ps) {
            arrayOfItems.add((Item)i);
            k++;
        }

         /*
                Ids of items of outfit selected
         */
        item1 = new Item();
        item1.set_id(imageId1);
        item2 = new Item();
        item2.set_id(imageId2);
        item3 = new Item();
        item3.set_id(imageId3);
        item4 = new Item();
        item4.set_id(imageId4);



        current = 1;  newitem = new Item();
        //Search the item1 to update
        for ( Item item : arrayOfItems ) {
            if ( item.get_id() == item1.get_id() ) {
                newitem.setCategory_id(item.getCategory_id());
                newitem.setStyle_id(item.getStyle_id());
                newitem.setRegister_date(item.getRegister_date());
                newitem.setLast_used_date(dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss"));
                newitem.setPhoto_path(item.getPhoto_path());
                newitem.setColors(item.getColors());
//                if ( !item.getMatches().contains(String.valueOf(imageId2)) && imageId2 != 0)
//                    newitem.setMatches(item.getMatches()+item2.get_id()+",");
                if ( !item.getMatches().contains(String.valueOf(imageId3))&& imageId3 != 0)
                    newitem.setMatches(item.getMatches()+item3.get_id()+",");
                else
                    newitem.setMatches(item.getMatches());
//                if ( !item.getMatches().contains(String.valueOf(imageId4))&& imageId4 != 0)
//                    newitem.setMatches(item.getMatches()+item4.get_id()+",");

                break;
            }
        }


//        DatabaseManager.getInstance().findItem(DatabaseManager.getInstance().getTable(Item.class), item1, Outfit.this);
       // DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Item.class), Outfit.this);



        Log.d(TAG, "And before update, newvalue has path : " + newitem.getPhoto_path());


        DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Item.class), item1, newitem, Outfit.this);

        //Update the last_worn_date for the other items too
        if(item2!=null) {
            newitem = new Item();
            for (Item item : arrayOfItems) {
                if (item.get_id() == item2.get_id()) {
                    newitem.setLast_used_date(dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss"));
                    newitem.setMatches(item.getMatches());
                }
            }
            DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Item.class), item2, newitem, Outfit.this);

        }


        if (item3!=null) {
            newitem = new Item();
            for (Item item : arrayOfItems) {
                if (item.get_id() == item3.get_id()) {
                    newitem.setLast_used_date(dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss"));
                    newitem.setMatches(item.getMatches());
                }
            }
            DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Item.class), item3, newitem, Outfit.this);
        }


        if (item4!= null) {
            newitem = new Item();
            for (Item item : arrayOfItems) {
                if (item.get_id() == item4.get_id()) {
                    newitem.setLast_used_date(dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss"));
                    newitem.setMatches(item.getMatches());
                }
            }
            DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Item.class), item4, newitem, Outfit.this);
        }




//        DatabaseManager.getInstance().deleteItem(DatabaseManager.getInstance().getTable(Item.class), item1, Outfit.this);
//        newitem.set_id(item1.get_id());
//        DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class), newitem, Outfit.this);

//            newitem.setPhoto_path(item1.getPhoto_path());
//            newitem.setLast_used_date(item1.getLast_used_date());
//            newitem.setRegister_date(item1.getRegister_date());
//            newitem.setCategory_id(item1.getCategory_id());
//            newitem.setStyle_id((item1.getStyle_id()));
//            newitem.setMatches(item1.getMatches() + "," + imageId2);
          //  DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Item.class), item1, newitem, Outfit.this);
            //current = 2;
//            DatabaseManager.getInstance().findItem(DatabaseManager.getInstance().getTable(Item.class), item2, Outfit.this);
//            current = 3;
//            DatabaseManager.getInstance().findItem(DatabaseManager.getInstance().getTable(Item.class), item3, Outfit.this);
//            current = 4;
//            DatabaseManager.getInstance().findItem(DatabaseManager.getInstance().getTable(Item.class), item4, Outfit.this);
//
//
//
        Log.d(TAG, String.valueOf(imageId1));
            // fill imageViews
            imageView1.setImageBitmap(imageBmp1);
            imageView2.setImageBitmap(imageBmp2);
            imageView3.setImageBitmap(imageBmp3);
            imageView4.setImageBitmap(imageBmp4);


        /*
        Click event for
         */
            btnShare.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }

            });

    }

    @Override
    public void onComplete(Object result) {
//        if ( current == 1)
//            item1 = result;
//        if ( current == 2)
//            item2 = (Item)result;
//        if ( current == 3)
//            item3 = (Item)result;
//        if ( current == 4)
//            item4 = (Item)result;



    }

    @Override
    public void onComplete(final ArrayList result) {
      //  arrayOfItems = result;

//        this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {


//            }
//        });


    }

    @Override
    public void onError(Throwable error) {

    }


}
