package nao.fit.bstu.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.FragmentManager;
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

public class MainActivity extends AppCompatActivity implements
        FragmentListView.OnItemSelectedListener {

    Intent intent;
    Intent addIntent;
    Button addButton;
    FragmentListView fragmentListView;
    FragmentItem fragmentItem;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);

        intent = new Intent(this, CybersportInfoActivity.class);
        addIntent = new Intent(this, GeneralInfoActivity.class);
        View.OnClickListener itemListenerAddClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addIntent);
            }
        };
        addButton.setOnClickListener(itemListenerAddClick);
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
                FragmentListView fragm =(FragmentListView) getSupportFragmentManager().findFragmentById(R.id.list_frag);
                fragm.sortList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Cybersport cybersport) {
        FragmentItem fragm =(FragmentItem) getSupportFragmentManager().findFragmentById(R.id.info_frag);
        if(fragm==null){
            intent.putExtra(Cybersport.class.getSimpleName(), (Serializable) cybersport);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Был выбран пункт " + cybersport.getId(), Toast.LENGTH_SHORT).show();
        }else {
            ft = getSupportFragmentManager().beginTransaction();
            FragmentItem fragment = (FragmentItem)getSupportFragmentManager().findFragmentById(R.id.info_frag);
            ft.replace(R.id.info_frag,fragment);
            fragm.setCybersport(cybersport);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        fragmentListView=(FragmentListView) getSupportFragmentManager().findFragmentById(R.id.list_frag);
        fragmentItem=(FragmentItem) getSupportFragmentManager().findFragmentById(R.id.info_frag);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.list_frag, new FragmentListView());
        ft.commit();

        if(fragmentItem!=null){
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.info_frag, new FragmentItem());
            ft.commit();
        }
    }
}