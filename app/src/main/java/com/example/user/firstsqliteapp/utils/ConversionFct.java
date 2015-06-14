package com.example.user.firstsqliteapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.data.Category;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 04.06.2015.
 */
public class ConversionFct {
    public ConversionFct() {
        myapp = MyApp.getInstance();
    }

    private MyApp myapp;

    public Bitmap pathToBmp(String path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        Bitmap bmp = BitmapFactory.decodeStream(in, null, options);

        return bmp;
    }

    public Bitmap getBmpRotated(Item item, Bitmap oldBitmap) {
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface exif = new ExifInterface(item.getPhoto_path());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            rotatedBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotatedBitmap;
    }

    public boolean existsCategory(ArrayList<Integer> categoriesToCheck, ArrayList<Category> DBCategories) {

   //     ArrayList<Category> categoryArray = DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Category.class), );
        boolean ok = false;
        for (int i : categoriesToCheck)
            for ( Category c : DBCategories )
                if (c.getId() == i)
                    if(c.getNritems() > 0 )
                        ok = true;
        return ok;
    }
}