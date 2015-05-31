package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
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

import com.example.user.firstsqliteapp.R;
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
            try {
                FileInputStream in = new FileInputStream(destination);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                image_path = destination.getAbsolutePath();
                text_view_path.setText(image_path);
                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
                picture.setImageBitmap(bmp);
                Item newItem = new Item(2, 1, name_date, null, image_path);
                //insert in the database
                DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class), newItem, this);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
