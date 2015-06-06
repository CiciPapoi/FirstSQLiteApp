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

                    Item deletedItem = new Item();
                    deletedItem.set_id(imageId);

                    MyApp myapp = MyApp.getInstance();
                    myapp.myGlobalArray.remove(new Integer((int)deletedItem.getCategory_id()));

                    DatabaseManager.getInstance().deleteItem(DatabaseManager.getInstance().getTable(Item.class),deletedItem,DisplayImageActivity.this);

                    // /after deleting data go to main page
                    Intent i = new Intent(DisplayImageActivity.this,
                            ItemsListActivity.class);
                    startActivity(i);
                    finish();
                }
            });

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
