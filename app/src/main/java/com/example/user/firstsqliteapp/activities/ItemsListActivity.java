package com.example.user.firstsqliteapp.activities;
/**
* Created by user on 22.05.2015.
*/
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;


public class ItemsListActivity extends ListActivity implements DatabaseOperationStatus {

    //private String[] data = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine","Ten"};
    private ArrayList<Item> arrayOfItems;
    private SelectionAdapter mAdapter;

    //var for image click handling
    //byte[] imageName;
    String imagePath;
    long imageId;
    Bitmap theImage;
    //var for Logcat
    private static final String TAG = ItemsListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_items);


        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Item.class), this);

        /**
         * go to next activity for detail image
         */
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                imagePath = arrayOfItems.get(position).getPhoto_path();
                imageId = arrayOfItems.get(position).get_id();

                //for debug
                long categ = arrayOfItems.get(position).getCategory_id();

                Log.d(TAG ,"before sending: " + imagePath + "-" + imageId);


                //--------URI-------------
                FileInputStream in = null;
                try {
                    in = new FileInputStream(new File(imagePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);


                Intent intent = new Intent( ItemsListActivity.this,
                        DisplayImageActivity.class);
                intent.putExtra("image_name", bmp);
                intent.putExtra("image_id", imageId);
                startActivity(intent);
            }
        });

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        getListView().setMultiChoiceModeListener(new MultiChoiceModeListener() {

            private int nr = 0;

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                mAdapter.clearSelection();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub

                nr = 0;
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            /*
                Decide the action for buttons in menu actionbar
             */
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {

                    case R.id.item_delete:
                        nr = 0;
                        mAdapter.clearSelection();
                        mode.finish();
                }
                return true;
            }

                /*
                    multiple selection effects
                 */
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // TODO Auto-generated method stub
                if (checked) {
                    nr++;
                    mAdapter.setNewSelection(position, checked);
                } else {
                    nr--;
                    mAdapter.removeSelection(position);
                }
                mode.setTitle(nr + " selected");

            }
        });

        /*
                LongPress on an intem effect
         */
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                // TODO Auto-generated method stub

                getListView().setItemChecked(position, !mAdapter.isPositionChecked(position));
                return false;
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        //get new list of items each time the activity is restarted

        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(Item.class), this);

    }

    /*
        Adapter definition
     */
    private class SelectionAdapter extends ArrayAdapter<Item> {

        private class ViewHolder {
            TextView id;
            ImageView photo;
            TextView last_used_date;
        }

        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public SelectionAdapter(Context context, int resource,
                                int textViewResourceId, List<Item> objects) {
            //super(context, resource, textViewResourceId, objects);
            super(context, R.layout.photo_item, objects);
        }

        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition() {
            return mSelection.keySet();
        }

        public void removeSelection(int position) {
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection() {
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Item item = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.photo_item, parent, false);
                viewHolder.id = (TextView) convertView.findViewById(R.id.item_id);
                viewHolder.photo = (ImageView) convertView.findViewById(R.id.item_photo);
                viewHolder.last_used_date = (TextView) convertView.findViewById(R.id.item_last_used);

                convertView.setTag(viewHolder);
            } else { //recycling the view
                viewHolder = (ViewHolder) convertView.getTag();
            }

            convertView.setBackgroundColor(getResources().getColor(android.R.color.background_light)); //default color

            if (mSelection.get(position) != null) {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));// this is a selected position so make it red
            }
            // Populate the data into the template view using the data object
            viewHolder.id.setText(String.valueOf(item.get_id()));

            /*
            set image: convert byte to bitmap take from Item class
             */


            //-----------URI METHOD-------------

            FileInputStream in = null;
            try {
                in = new FileInputStream(new File(item.getPhoto_path()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeStream(in, null, options);

            viewHolder.photo.setImageBitmap(bmp);
            viewHolder.last_used_date.setText(item.getLast_used_date());
            // Return the completed view to render on screen
            return convertView;

        }
    }

    @Override
    public void onComplete(Object result) {

    }

    @Override
    public void onComplete(final ArrayList result) {
        arrayOfItems = result;

        Log.d(TAG, String.valueOf(result.size()));

        /*
            Setting the adapter on the list
         */
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new SelectionAdapter(getApplicationContext(),
                        R.layout.photo_item, R.id.item_id, result);
                setListAdapter(mAdapter);
            }
        });


    }

    @Override
    public void onError(Throwable error) {

    }
}