package nao.fit.bstu.lab3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_info);

        salary = findViewById(R.id.salary);
        email = findViewById(R.id.email);
        instagram = findViewById(R.id.instagram);
        phoneNumber = findViewById(R.id.phoneNumber);
        photo = findViewById(R.id.photo);
        progressBar = findViewById(R.id.progressBar);
        backButton = (Button) findViewById(R.id.backButton);
        createButton = (Button) findViewById(R.id.createButton);

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
                finish();
//                if (!String.valueOf(salary.getText()).isEmpty()) {
//                    saveCybersport.setSalary(Double.parseDouble(String.valueOf(salary.getText())));
//                }
//                if (!String.valueOf(email.getText()).isEmpty()) {
//                    saveCybersport.setEmail(String.valueOf(email.getText()));
//                }
//                if (!String.valueOf(instagram.getText()).isEmpty()) {
//                    saveCybersport.setInstagram(String.valueOf(instagram.getText()));
//                }
//                if (!String.valueOf(phoneNumber.getText()).isEmpty()) {
//                    saveCybersport.setPhoneNumber(String.valueOf(phoneNumber.getText()));
//                }
//                backIntent.putExtra(Cybersport.class.getSimpleName(), (Serializable) saveCybersport);
//                startActivity(backIntent);
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
        List<Cybersport> cybersportList=JSONMethod.importFromJSON(this);
        cybersportList.add(saveCybersport);
        boolean result = JSONMethod.exportToJSON(this, cybersportList);
        if (result) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
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
