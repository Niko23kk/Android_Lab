package nao.fit.bstu.lab3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OtherInfoActivity extends AppCompatActivity {
    Cybersport saveCybersport;
    TextView salary;
    TextView email;
    TextView instagram;
    TextView phoneNumber;
    ImageView photo;
    ProgressBar progressBar;
    Button backButton;
    Button createButton;
    Intent addIntent;
    MyDatabaseHelper databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_info);
        addIntent = new Intent(this, GeneralInfoActivity.class);

        addIntent = new Intent(this, GeneralInfoActivity.class);
        salary = findViewById(R.id.salary);
        email = findViewById(R.id.email);
        instagram = findViewById(R.id.instagram);
        phoneNumber = findViewById(R.id.phoneNumber);
        photo = findViewById(R.id.photo);
        progressBar = findViewById(R.id.progressBar);
        backButton = (Button) findViewById(R.id.backButton);
        createButton = (Button) findViewById(R.id.createButton);
        databaseHelper=new MyDatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            saveCybersport = (Cybersport) arguments.getSerializable(Cybersport.class.getSimpleName());
            salary.setText(String.valueOf(saveCybersport.getSalary()));
            if(saveCybersport.getEmail()!=null) {
                email.setText(saveCybersport.getEmail());
            }
            if(saveCybersport.getInstagram()!=null) {
                instagram.setText(saveCybersport.getInstagram());
            }
            if(saveCybersport.getPhoneNumber()!=null) {
                phoneNumber.setText(saveCybersport.getInstagram());
            }
            if(saveCybersport.getPhoto()!=null){
                photo.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background));
            }
        }else {
            saveCybersport=new Cybersport();
        }
        progressBar.setProgress(50);

        Intent backIntent = new Intent(this, GeneralInfoActivity.class);
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                if (!String.valueOf(salary.getText()).isEmpty()) {
                    saveCybersport.setSalary(Double.parseDouble(String.valueOf(salary.getText())));
                }
                if (!String.valueOf(email.getText()).isEmpty()) {
                    saveCybersport.setEmail(String.valueOf(email.getText()));
                }
                if (!String.valueOf(instagram.getText()).isEmpty()) {
                    saveCybersport.setInstagram(String.valueOf(instagram.getText()));
                }
                if (!String.valueOf(phoneNumber.getText()).isEmpty()) {
                    saveCybersport.setPhoneNumber(String.valueOf(phoneNumber.getText()));
                }
                backIntent.putExtra(Cybersport.class.getSimpleName(), (Serializable) saveCybersport);
                //backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(backIntent);
            }
        };
        backButton.setOnClickListener(backListener);

        Intent createIntent = new Intent(this, MainActivity.class);
        View.OnClickListener createListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(salary.getText()).isEmpty()) {
                    saveCybersport.setSalary(Double.parseDouble(String.valueOf(salary.getText())));
                }
                if (!String.valueOf(email.getText()).isEmpty()) {
                    saveCybersport.setEmail(String.valueOf(email.getText()));
                }
                if (!String.valueOf(instagram.getText()).isEmpty()) {
                    saveCybersport.setInstagram(String.valueOf(instagram.getText()));
                }
                if (!String.valueOf(phoneNumber.getText()).isEmpty()) {
                    saveCybersport.setPhoneNumber(String.valueOf(phoneNumber.getText()));
                }
                saveCybersport.setId((int) (Math.random()*(1000+1)));
                saveObject();
                startActivity(createIntent);
            }
        };
        createButton.setOnClickListener(createListener);
    }

    public void saveObject(){

        ContentValues cv=new ContentValues();
        cv.put(MyDatabaseHelper.COLUMN_ID,saveCybersport.getId());

        cv.put(MyDatabaseHelper.NAME,String.valueOf(saveCybersport.getName()));
        cv.put(MyDatabaseHelper.LAST_NAME,String.valueOf(saveCybersport.getLastName()));
        if (!String.valueOf(saveCybersport.getAge()).isEmpty()) {
            cv.put(MyDatabaseHelper.AGE,String.valueOf(saveCybersport.getAge()));
        } else {
            cv.put(MyDatabaseHelper.AGE,0);
        }
        if (!String.valueOf(salary.getText()).isEmpty()) {
            cv.put(MyDatabaseHelper.SALARY,String.valueOf(saveCybersport.getSalary()));
        } else {

            cv.put(MyDatabaseHelper.SALARY,0.0);
        }
        cv.put(MyDatabaseHelper.EMAIL,String.valueOf(saveCybersport.getEmail()));
        cv.put(MyDatabaseHelper.INSTAGRAM,String.valueOf(saveCybersport.getInstagram()));
        cv.put(MyDatabaseHelper.PHONE_NUMBER,String.valueOf(saveCybersport.getPhoneNumber()));
        databaseHelper.addItem(db,cv);
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

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        Cybersport saveCybersport = new Cybersport();
//        if (!String.valueOf(salary.getText()).isEmpty()) {
//            saveCybersport.setName(String.valueOf(salary.getText()));
//        }
//        saveCybersport.setLastName(String.valueOf(email.getText()));
//        saveCybersport.setLastName(String.valueOf(instagram.getText()));
//        saveCybersport.setLastName(String.valueOf(phoneNumber.getText()));
//        savedInstanceState.putString("salary", String.valueOf(saveCybersport.getSalary()));
//        savedInstanceState.putString("instagram", saveCybersport.getInstagram());
//        savedInstanceState.putString("email", saveCybersport.getEmail());
//        savedInstanceState.putString("phoneNumber", saveCybersport.getPhoneNumber());
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        salary.setText(String.valueOf(((Cybersport)savedInstanceState.getSerializable("salary")).getSalary()));
//        email.setText(((Cybersport)savedInstanceState.getSerializable("email")).getEmail());
//        instagram.setText(((Cybersport)savedInstanceState.getSerializable("instagram")).getInstagram());
//        phoneNumber.setText(((Cybersport)savedInstanceState.getSerializable("phoneNumber")).getPhoneNumber());
//
//    }
}
