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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.Category;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;
import com.example.user.firstsqliteapp.dialogs.ColorPickerDialog;
import com.example.user.firstsqliteapp.utils.ConversionFct;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 27.05.2015.
 */
public class CameraActivity extends Activity implements DatabaseOperationStatus{
    private static final int REQUEST_IMAGE_CAMERA = 100;
    private static final int REQUEST_IMAGE_GALLERY = 200;

    private static final String TAG = "CameraActivity";

    TextView text_view_path;
    ImageView picture;
    File destination;
    String image_path;
    String name_date;

    private int category_bd;
    private String colors;
    private int style;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        colors = "";
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
                startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
            }
        });

        Button home = (Button) findViewById(R.id.backButton);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

//        Button gallery = (Button) findViewById(R.id.idBtnTakeFromGalley);
//
//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
////                intent.putExtra("return-data", true);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
//                startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
//
//
//            }
//        });



    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if( requestCode == REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK || requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK){


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
        AlertDialog.Builder categoryDialog = new AlertDialog.Builder(this);
            /*
            Dialog for Colors
            */
        final AlertDialog.Builder colorsDialog = new AlertDialog.Builder(this);
            /*
            Dialog for Styles
            */
        final AlertDialog.Builder styleDialog = new AlertDialog.Builder(this);



        //CategoryDialog setting
            // Add the buttons
//        categoryDialog.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//
//            }
//
//        });

        categoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Set other dialog properties
        final String[] categoriesArray = { "Accessories","Blouses","Dresses","Jackets&Coats","Tops","Trousers","Shorts","Shoes","Skirts"};

        categoryDialog.setTitle(R.string.pick_category)
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {                    //R.array.categories_array
                        // The 'which' argument contains the index position of the selected item
                        switch(which) {
                            case 0: category_bd = 1; break;
                            case 1: category_bd = 6; break;
                            case 2: category_bd = 8; break;
                            case 3: category_bd = 7; break;
                            case 4: category_bd = 4; break;
                            case 5: category_bd = 2; break;
                            case 6: category_bd = 3; break;
                            case 7: category_bd = 9; break;
                            case 8: category_bd = 5; break;
                            case 9: category_bd = 2; break;
                        }


//                    // Build the item to be added
//
//                    FileInputStream in = null;
//                    try {
//                        in = new FileInputStream(destination);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 10;
//                    image_path = destination.getAbsolutePath();
//                    text_view_path.setText(image_path);
//                    Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
//                    picture.setImageBitmap(bmp);
//
//                    //Create the object
//                    Item newItem = new Item(category_bd, 1, name_date, null, image_path, "");
//
//                    //Insert the item in database
//                    DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class), newItem, CameraActivity.this);
//
//                    //Update category in database
//                    Category oldCat = new Category(category_bd);
//
//                    DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Category.class), oldCat ,null,  CameraActivity.this);
                        AlertDialog col_dialog = colorsDialog.create();
                        col_dialog.show();
                    }
                });

        //Set properties for Colors Dialog:


        //=========================COLORS DIALOG =====================================================



        categoryDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Set other dialog properties
        final String[] colorsArray = { "Blue", "Black", "Yellow", "Red", "White", "Green", "Gray"};

        colorsDialog.setTitle(R.string.pick_color)
                .setItems(colorsArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {                    //R.array.categories_array
                        colors = colors + String.valueOf(which) ;

                        // Show STYLE dialog
                        AlertDialog style_dialog = styleDialog.create();
                        style_dialog.show();

                        // Build the item to be added
//
//                        FileInputStream in = null;
//                        try {
//                            in = new FileInputStream(destination);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 10;
//                        image_path = destination.getAbsolutePath();
//                        text_view_path.setText(image_path);
//                        Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
//                        picture.setImageBitmap(bmp);
//
//                        //Create the object
//                        Item newItem = new Item(category_bd, 1, name_date, null, image_path, colors);
//
//                        //Insert the item in database
//                        DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class), newItem, CameraActivity.this);
//
//                        //Update category in database
//                        Category oldCat = new Category(category_bd);
//
//                        DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Category.class), oldCat ,null,  CameraActivity.this);
//
                    }
                });


            /*
            Set properties for StylesDiag
             */


            //=========================STYLES DIALOG =====================================================

//            styleDialog.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//
//                }
//            });

            styleDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Set other dialog properties
            final String[] stylesArray = {"casual", "elegant","office", "sport" };

             styleDialog.setTitle(R.string.pick_style)
                    .setItems(stylesArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {                    //R.array.categories_array
                            style = which;

                            // Show STYLE dialog
                            AlertDialog style_dialog = styleDialog.create();
                            style_dialog.show();

                            // Build the item to be added
                if  ( requestCode == REQUEST_IMAGE_CAMERA ) {
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(destination);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;
                    image_path = destination.getAbsolutePath();
                    //text_view_path.setText(image_path);
                    text_view_path.setVisibility(View.VISIBLE);
                    Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
                    picture.setImageBitmap(bmp);
                }
                if (requestCode == REQUEST_IMAGE_GALLERY ) {
                    if(data != null)
                    {
                        Uri selectedImageUri = data.getData();
                        image_path = selectedImageUri.getPath();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 10;
                        //Bitmap thumbnail = BitmapFactory.decodeFile(image_path, options);
                        Bitmap bitmap = null;
                        try {
                            if (bitmap != null) {
                                bitmap.recycle();
                            }
                            InputStream stream = getContentResolver().openInputStream(
                                    data.getData());
                            bitmap = BitmapFactory.decodeStream(stream);
                            stream.close();
                            picture.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        } catch (IOException e) {
                        e.printStackTrace();
                    }
                    }

                    //


                }

                            //Create the object
                            Item newItem = new Item(category_bd, style, name_date, null, image_path, colors, "");

                            //Insert the item in database
                            DatabaseManager.getInstance().insertItem(DatabaseManager.getInstance().getTable(Item.class), newItem, CameraActivity.this);

                            //Update category in database
                            Category oldCat = new Category(category_bd);

                            DatabaseManager.getInstance().updateItem(DatabaseManager.getInstance().getTable(Category.class), oldCat ,null,  CameraActivity.this);
                            style_dialog.dismiss();

                        }
                    });

            //==================================================================================
            // Create the AlertDialog
          AlertDialog categ_dialog = categoryDialog.create();
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
