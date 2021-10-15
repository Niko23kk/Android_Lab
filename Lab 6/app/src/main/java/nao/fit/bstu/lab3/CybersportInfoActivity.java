package nao.fit.bstu.lab3;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class CybersportInfoActivity extends AppCompatActivity {

    TextView backButton;
    FragmentTransaction ft;
    FragmentItem fragment;
    Intent addIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cybersport_info);
        FragmentItem x= (FragmentItem) getSupportFragmentManager().findFragmentById(R.id.info_frag);

        RequestPermissions requestPermissions = new RequestPermissions();
        if (!requestPermissions.permissionGranted) {
            requestPermissions.checkPermissions(this, this);
        }
        backButton = (TextView) findViewById(R.id.backButton);
        addIntent = new Intent(this, GeneralInfoActivity.class);

        ft = getSupportFragmentManager().beginTransaction();
        fragment=new FragmentItem();
        ft.add(R.id.info_frag, fragment);
        ft.commit();

        Intent intent = new Intent(this, MainActivity.class);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentItem fragm = (FragmentItem) getSupportFragmentManager().findFragmentById(R.id.info_frag);
                fragm.saveObject();
                Toast.makeText(getApplicationContext(), "Back ti main", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        backButton.setOnClickListener(itemListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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
            case R.id.up:
                Intent main=new Intent(this,MainActivity.class);
                startActivity(main);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            fragment.setCybersport((Cybersport) arguments.getSerializable(Cybersport.class.getSimpleName()));
        }
    }
}