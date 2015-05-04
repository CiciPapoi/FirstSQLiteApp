package com.example.user.firstsqliteapp.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.data.UsersAdapter;
import com.example.user.firstsqliteapp.database.DatabaseManager;
import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;
import com.example.user.firstsqliteapp.R;
import com.example.user.firstsqliteapp.database.DatabaseOperationStatus;
import com.example.user.firstsqliteapp.dialogs.EditDialog;

import java.util.ArrayList;
import java.util.List;


public class SelectFragment extends Fragment  implements DatabaseOperationStatus {
    private ListView viewAllList;
    private SelectFragment context = this;
    private ArrayList<User> arrayOfUsers;
    private User currentSelected;

    //var for Logcat
    private static final String TAG = SelectFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_select, container, false);
        viewAllList = (ListView) root.findViewById(R.id.ListView);

        DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(User.class), SelectFragment.this);

        return root;
    }

    @Override
    public void onComplete(Object result) {

    }

    @Override
    public void onComplete(ArrayList result) {
        // Construct the data source
        arrayOfUsers = result;

        Log.d(TAG, String.valueOf(result.size()));

        context.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Create the adapter to convert the array to views
                UsersAdapter adapter = new UsersAdapter(context.getActivity(), arrayOfUsers);
                // Attach the adapter to a ListView

                viewAllList.setAdapter(adapter);

                registerForContextMenu(viewAllList);
            }
        });
    }

    @Override
    public void onError(Throwable error) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.ListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("action for "+ arrayOfUsers.get(info.position).getUsername() );
            String[] menuItems = new String[3];
            menuItems[0]= getString(R.string.context_menu_item_1);
            menuItems[1]=getString(R.string.context_menu_item_2);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }

            currentSelected = new User(arrayOfUsers.get(info.position).getId(), arrayOfUsers.get(info.position).getUsername(), arrayOfUsers.get(info.position).getInitials(),arrayOfUsers.get(info.position).getAddress());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = new String[3];
        menuItems[0]= getString(R.string.context_menu_item_1);
        menuItems[1]= getString(R.string.context_menu_item_2);
        String menuItemName = menuItems[menuItemIndex];
//        String listItemName = viewAllList.getSelectedItem().toString();

        User test_user = currentSelected;
        Log.d(TAG,"Current Selected : " + currentSelected.getId() + " name : " + currentSelected.getUsername());
        if ( menuItemName.compareTo("Delete") == 0){
            DatabaseManager.getInstance().deleteItem(DatabaseManager.getInstance().getTable(User.class),test_user, SelectFragment.this );
            DatabaseManager.getInstance().getAllItems(DatabaseManager.getInstance().getTable(User.class), SelectFragment.this);

            Toast.makeText(context.getActivity(), " Item : " + menuItemName  , Toast.LENGTH_LONG).show();
        }
        if ( menuItemName.compareTo("Edit") == 0 ) {
            FragmentManager manager = getFragmentManager();
            EditDialog editDialog = new EditDialog();
            editDialog.show(manager,"editDialogIdentifier");
        }

        return true;
    }
}
