package nao.fit.bstu.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    List<Cybersport> cybersportList;
    Intent intent;
    Intent addIntent;
    Intent updateIntent;
    ListView allItems;
    Button addButton;
    int orderSort=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, CybersportInfoActivity.class);
//        cybersportList=new ArrayList<>();
//        cybersportList.add(new Cybersport(1, "Alexsey", "Niko", 21,  500, "+37534354353","eeqwew","12"));
        cybersportList = JSONMethod.importFromJSON(this);
        if(cybersportList==null){
            cybersportList=new ArrayList<>();
        }
        allItems = (ListView) findViewById(R.id.cybersportList);
        addButton = (Button) findViewById(R.id.addButton);

        CybersportView stateAdapter = new CybersportView(this, R.layout.item_view, cybersportList);
        allItems.setAdapter(stateAdapter);

        registerForContextMenu(allItems);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cybersport selectedCybersport = (Cybersport) parent.getItemAtPosition(position);
                intent.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedCybersport.getId(), Toast.LENGTH_SHORT).show();
            }
        };

        addIntent = new Intent(this, GeneralInfoActivity.class);
        View.OnClickListener itemListenerAddClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addIntent);
            }
        };
        addButton.setOnClickListener(itemListenerAddClick);
        allItems.setOnItemClickListener(itemListener);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.open:
                Cybersport selectedCybersport = (Cybersport) allItems.getItemAtPosition(info.position);
                intent.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedCybersport.getId(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit:
                updateIntent = new Intent(this, CybersportInfoUpdateActivity.class);
                updateIntent.putExtra(Cybersport.class.getSimpleName(), (Serializable) allItems.getItemAtPosition(info.position));
                startActivity(updateIntent);
                return true;
            case R.id.delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Warning")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Want you delete an object")
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        deleteItem((Cybersport) allItems.getItemAtPosition(info.position));
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
        cybersportList=JSONMethod.importFromJSON(this);
        cybersportList=cybersportList.stream()
                .filter(item->item.getId()!=selectCybersport.getId()).collect(Collectors.toList());
        boolean result = JSONMethod.exportToJSON(this, cybersportList);
        if (result) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        CybersportView stateAdapter = new CybersportView(this, R.layout.item_view, cybersportList);
        allItems.setAdapter(stateAdapter);
    }

    public void SaveCybersport(List<Cybersport> itemList) {
        boolean result = JSONMethod.exportToJSON(this, itemList);
        if (result) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_bar_add:
            case R.id.action_add:
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addIntent);
                return true;
            case R.id.action_open:
                TextView instagram = findViewById(R.id.intagram);
                Intent appIntent = null;
                appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/humlled"));
                startActivity(appIntent);
                return true;
            case R.id.action_close:
                finishAffinity();
                return true;
            case R.id.action_bar_sort:
                CybersportView stateAdapter = new CybersportView(this, R.layout.item_view, cybersportList);
                if(orderSort==0) {
                    Collections.sort(cybersportList, new Comparator<Cybersport>() {
                        public int compare(Cybersport o1, Cybersport o2) {
                            return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                        }
                    });
                    orderSort=1;
                }else {
                    Collections.sort(cybersportList, new Comparator<Cybersport>() {
                        public int compare(Cybersport o1, Cybersport o2) {
                            return String.valueOf(o2.getId()).compareTo(String.valueOf(o1.getId()));
                        }
                    });
                    orderSort=0;
                }
                allItems.setAdapter(stateAdapter);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SaveCybersport(cybersportList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SaveCybersport(cybersportList);
    }
}