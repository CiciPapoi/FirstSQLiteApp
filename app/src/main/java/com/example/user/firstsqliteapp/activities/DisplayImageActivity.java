package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.Category;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;

import java.util.ArrayList;

/**
 * Created by user on 25.05.2015.
 */
public class DisplayImageActivity extends Activity implements DatabaseOperationStatus{

        Button btnDelete;
        ImageView imageDetail;
        long imageId;
        Bitmap theImage;
        private ArrayList<Item> arrayOfItems;
        private ArrayList<Category> arrayOfCategories;
        Item deletedItem;
        String newmatches;

    //var for Logcat
    private static final String TAG = DisplayImageActivity.class.getSimpleName();


    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.display);
            btnDelete = (Button) findViewById(R.id.btnDelete);
            imageDetail = (ImageView) findViewById(R.id.bigImage);
            /**
             * getting intent data from search and previous screen
             */
            Intent intent = getIntent();
            theImage = (Bitmap) intent.getParcelableExtra("image_name");
            imageId = intent.getLongExtra("image_id",-1);
            Log.d(TAG, String.valueOf(imageId));
            imageDetail.setImageBitmap(theImage);
            btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    /**
                     * Deleting records from database
                     */
                    Log.d(TAG, "deleting image with id : "+String.valueOf(imageId));

                    deletedItem = new Item();
                    deletedItem.set_id(imageId);

                 //   MyApp myapp = MyApp.getInstance();
//                    myapp.myGlobalArray.remove(deletedItem);

                    DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Item.class), DisplayImageActivity.this);

                    DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Category.class), DisplayImageActivity.this);







                }
            });
        }

    @Override
    public void onComplete(Object result) {

    }

    @Override
    public void onComplete(ArrayList result) {

        Object o = result.get(0);
        //---------------Items case----------------
        if (o instanceof Item)
            arrayOfItems = result;
        else // Category case
            arrayOfCategories = result;


        Category categToDecrease = new Category();
        for ( Item i : arrayOfItems) {
            if (i.get_id() == imageId) {
                for (Category c : arrayOfCategories)
                    if (c.getId() == i.getCategory_id())
                        categToDecrease.setId(c.getId());
            }
        }

        Category updatedCateg = new Category();
        updatedCateg.setNritems( categToDecrease.getNritems()-1);


        DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Category.class),categToDecrease,updatedCateg , DisplayImageActivity.this);


        DatabaseManager.getInstance().deleteItem(DatabaseManager.getInstance().getTable(Item.class),deletedItem,DisplayImageActivity.this);
        // /after deleting data go to main page
        Intent i = new Intent(DisplayImageActivity.this,
                ItemsListActivity.class);
        startActivity(i);
        finish();



//        for ( Item item : arrayOfItems){
//            newmatches = "";
//            if (  item.getMatches().contains(String.valueOf(deletedItem.get_id()))){
//                for ( String m : item.getMatches().split(",")){
//                    if ( m != String.valueOf(deletedItem.get_id()))
//                        newmatches = newmatches + m + ",";
//
//                }
//                Item upitem = new Item();
//                upitem.setLast_used_date(item.getLast_used_date());
//                upitem.setMatches(newmatches);
//
//                DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Item.class),item,upitem, DisplayImageActivity.this);
//
//            }
//        }

    }

    @Override
    public void onError(Throwable error) {

    }

}
