package com.example.user.firstsqliteapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.firstsqliteapp.R;

import java.util.ArrayList;

/**
 * Created by user on 17.04.2015.
 */
public class UsersAdapter extends ArrayAdapter<User> {

    // View lookup cache
    private static class ViewHolder {
        TextView id;
        TextView username;
        TextView init;
        TextView address;

    }

    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.list_item, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.id = (TextView) convertView.findViewById(R.id.item_id);
            viewHolder.username = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.init= (TextView) convertView.findViewById(R.id.item_init);
            viewHolder.address = (TextView) convertView.findViewById(R.id.item_address);

            convertView.setTag(viewHolder);
        } else { //recycling the view
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.id.setText(String.valueOf(user.getId()));
        viewHolder.address.setText(user.getAddress());
        viewHolder.username.setText(user.getUsername());
        viewHolder.init.setText(user.getInitials());
        // Return the completed view to render on screen
        return convertView;
    }
}
