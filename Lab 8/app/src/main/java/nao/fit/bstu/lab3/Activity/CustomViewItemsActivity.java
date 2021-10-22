package nao.fit.bstu.lab3.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nao.fit.bstu.lab3.CustomRecyclerAdapter;
import nao.fit.bstu.lab3.Room.Cybersport;
import nao.fit.bstu.lab3.MyDatabaseHelper;
import nao.fit.bstu.lab3.R;

public class CustomViewItemsActivity extends AppCompatActivity {

    boolean flag=true;
    Intent changeIntent;
    Intent backIntent;
    Button backButton;
    Button changeButton;
    DrawerLayout drawerLayout;
    RecyclerView navigationView;
    MyDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    String[] headers = new String[] {
            MyDatabaseHelper.COLUMN_ID,
            MyDatabaseHelper.NAME,
            MyDatabaseHelper.LAST_NAME,
            MyDatabaseHelper.AGE,
            MyDatabaseHelper.SALARY};
    int[] to =  new int[]{R.id.id, R.id.name,R.id.lastName,R.id.age,R.id.salary};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_items);

        backButton = (Button) findViewById(R.id.backButton);
        changeButton = (Button) findViewById(R.id.changeButton);
        navigationView=(RecyclerView) findViewById(R.id.cybersportList);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        navigationView.setLayoutManager(layoutManager);
        databaseHelper=new MyDatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        CustomRecyclerAdapter stateAdapter = new CustomRecyclerAdapter((ArrayList<Cybersport>) databaseHelper.getAllItems(databaseHelper.selectAllItems(db)));
        navigationView.setAdapter(stateAdapter);

        changeIntent = new Intent(this, CybersportInfoActivity.class);
        backIntent = new Intent(this, MainActivity.class);
        View.OnClickListener itemListenerBackClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(backIntent);
            }
        };

        View.OnClickListener itemListenerChangeClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    navigationView.setLayoutManager(layoutManager);
                    flag=true;
                }
                else {
                    drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    navigationView.setLayoutManager(layoutManager);
                    flag=false;
                }
            }
        };
        changeButton.setOnClickListener(itemListenerChangeClick);
        backButton.setOnClickListener(itemListenerBackClick);
    }
}
