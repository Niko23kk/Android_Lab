package nao.fit.bstu.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Cybersport> cybersportList;
    Intent intent;
    Intent addIntent;
    ListView allItems;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, CybersportInfoActivity.class);
//        cybersportList=new ArrayList<>();
//        cybersportList.add(new Cybersport(1, "Alexsey", "Niko", 21,  500, "+37534354353","eeqwew","12"));
        cybersportList=JSONMethod.importFromJSON(this);
        if(cybersportList.stream().count()==0){
            cybersportList=new ArrayList<>();
        }
        allItems = (ListView) findViewById(R.id.cybersportList);
        addButton = (Button) findViewById(R.id.addButton);

        CybersportView stateAdapter = new CybersportView(this, R.layout.item_view, cybersportList);
        allItems.setAdapter(stateAdapter);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Cybersport selectedCybersport = (Cybersport) parent.getItemAtPosition(position);
                intent.putExtra(Cybersport.class.getSimpleName(), (Serializable) selectedCybersport);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedCybersport.getId(),Toast.LENGTH_SHORT).show();
            }
        };

        addIntent = new Intent(this, GeneralInfoActivity.class);
        View.OnClickListener itemListenerAddClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addIntent);
                Toast.makeText(getApplicationContext(), "Back ti main",Toast.LENGTH_SHORT).show();
            }
        };
        addButton.setOnClickListener(itemListenerAddClick);
        allItems.setOnItemClickListener(itemListener);
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