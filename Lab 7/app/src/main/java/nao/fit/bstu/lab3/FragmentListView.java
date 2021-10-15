package nao.fit.bstu.lab3;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListView extends Fragment {

    MyDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    private OnItemSelectedListener mListener;
    Intent intentInfo;
    Intent updateIntent;
    ListView allItems;
    View view;
    int orderSort=0;
    String[] headers = new String[] {
            MyDatabaseHelper.COLUMN_ID,
            MyDatabaseHelper.NAME,
            MyDatabaseHelper.LAST_NAME,
            MyDatabaseHelper.AGE,
            MyDatabaseHelper.SALARY};
    int[] to =  new int[]{R.id.id, R.id.name,R.id.lastName,R.id.age,R.id.salary};


    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        public void onItemSelected(Cybersport cybersport);
    }

    public FragmentListView() {
        // Required empty public constructor
    }

    public static FragmentListView newInstance(String param1, String param2) {
        FragmentListView fragment = new FragmentListView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list_view, container, false);
        intentInfo = new Intent(view.getContext(), CybersportInfoActivity.class);

        databaseHelper=new MyDatabaseHelper(view.getContext());
        db = databaseHelper.getReadableDatabase();
        allItems = view.findViewById(R.id.cybersportList);

        allItems.setAdapter(new SimpleCursorAdapter(this.getContext(), R.layout.item_view, databaseHelper.selectAllItems(db),
                headers,to, 0));

        registerForContextMenu(allItems);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cybersport selectedCybersport = databaseHelper.getItem((Cursor) parent.getItemAtPosition(position));
//                intentInfo.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
//                startActivity(intentInfo);
                mListener.onItemSelected(selectedCybersport);
                Toast.makeText(view.getContext(), "Был выбран пункт " + selectedCybersport.getId(), Toast.LENGTH_SHORT).show();
            }
        };

        allItems.setOnItemClickListener(itemListener);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.open, Menu.NONE, "Open");
        menu.add(Menu.NONE, R.id.edit, Menu.NONE, "Edit");
        menu.add(Menu.NONE, R.id.delete, Menu.NONE, "Delete");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.open:
                Cybersport selectedCybersport = databaseHelper.getItem((Cursor) allItems.getItemAtPosition(info.position));
                intentInfo.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
                startActivity(intentInfo);
                Toast.makeText(view.getContext(), "Был выбран пункт " + selectedCybersport.getId(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit:
                updateIntent = new Intent(view.getContext(), CybersportInfoUpdateActivity.class);
                updateIntent.putExtra(Cybersport.class.getSimpleName(), databaseHelper.getItem((Cursor) allItems.getItemAtPosition(info.position)));
                startActivity(updateIntent);
                return true;
            case R.id.delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Warning")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Want you delete an object")
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        deleteItem(databaseHelper.getItem((Cursor) allItems.getItemAtPosition(info.position)));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }
                                })
                        .create();
                builder.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void deleteItem(Cybersport selectCybersport){
        databaseHelper.deleteItem(db,String.valueOf(selectCybersport.getId()));
        allItems.setAdapter(new SimpleCursorAdapter(this.getContext(), R.layout.item_view, databaseHelper.selectAllItems(db),
                headers,to, 0));
    }

    public void selectOnlyWithSalary(){
        allItems.setAdapter(new SimpleCursorAdapter(this.getContext(), R.layout.item_view, databaseHelper.selectAllItemsWithSalary(db),
                headers,to, 0));
    }

    public void selectDefault(){
        allItems.setAdapter(new SimpleCursorAdapter(this.getContext(), R.layout.item_view, databaseHelper.selectAllItems(db),
                headers,to, 0));
    }

    public void sortList(){
        if(orderSort==0) {
            allItems.setAdapter(new SimpleCursorAdapter(this.getContext(), R.layout.item_view, databaseHelper.selectAllItemsWithOrder(db,""),
                    headers,to, 0));
            orderSort=1;
        }else {
            allItems.setAdapter(new SimpleCursorAdapter(this.getContext(), R.layout.item_view, databaseHelper.selectAllItemsWithOrder(db," desc"),
                    headers,to, 0));
            orderSort=0;
        }
    }
}