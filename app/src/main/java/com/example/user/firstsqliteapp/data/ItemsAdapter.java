package com.example.user.firstsqliteapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.firstsqliteapp.R;

import java.util.ArrayList;

/**
 * Created by user on 10.05.2015.
 */
public class ItemsAdapter extends ArrayAdapter<Item>{
    /**
     * Created by user on 10.05.2015.
     */
    // View lookup cache
    private static class ViewHolder {
        TextView photo;
        TextView category;
        TextView style;
        TextView photo_url;
    }

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.row_list_item, items);
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
            //viewHolder.photo = (TextView) convertView.findViewById(R.id.);
            //viewHolder.category = (TextView) convertView.findViewById(R.id.category_item_id);
            //viewHolder.style= (TextView) convertView.findViewById(R.id.);
            //viewHolder.photo_url = (TextView) convertView.findViewById(R.id.ph);

            convertView.setTag(viewHolder);
        } else { //recycling the view
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
      //  viewHolder.photo.setImageDrawable();
        //viewHolder.category.setText(String.valueOf(item.getCategory_id()));
       // viewHolder.style.setText(String.valueOf(item.getStyle_id()));
        //viewHolder.photo_url.setText(String.valueOf(item.getPhoto_path()));

        // Return the completed view to render on screen
        return convertView;
    }



}
