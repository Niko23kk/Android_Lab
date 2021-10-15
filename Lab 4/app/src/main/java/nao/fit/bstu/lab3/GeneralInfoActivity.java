package nao.fit.bstu.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class GeneralInfoActivity extends AppCompatActivity {
    Cybersport saveCybersport;
    TextView name;
    TextView lastName;
    TextView age;
    ProgressBar progressBar;
    Button backButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_info);

        saveCybersport=new Cybersport();
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        progressBar = findViewById(R.id.progressBar);
        backButton = (Button) findViewById(R.id.backButton);
        nextButton = (Button) findViewById(R.id.nextButton);

//        Bundle arguments = getIntent().getExtras();
//        if (arguments != null) {
//            saveCybersport = (Cybersport) arguments.getSerializable(Cybersport.class.getSimpleName());
//            name.setText(saveCybersport.getName());
//            lastName.setText(saveCybersport.getLastName());
//            age.setText(String.valueOf(saveCybersport.getAge()));
//        }
        progressBar.setProgress(5);

        Intent backIntent = new Intent(this, MainActivity.class);
        View.OnClickListener backButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(backIntent);
                Toast.makeText(getApplicationContext(), "Back ti main", Toast.LENGTH_SHORT).show();
            }
        };
        backButton.setOnClickListener(backButtonListener);

        Intent nextIntent = new Intent(this, OtherInfoActivity.class);
        View.OnClickListener nextButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCybersport.setName(String.valueOf(name.getText()));
                saveCybersport.setLastName(String.valueOf(lastName.getText()));
                if (!String.valueOf(age.getText()).isEmpty()) {
                    saveCybersport.setAge(Integer.parseInt(String.valueOf(age.getText())));
                }
                nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                nextIntent.putExtra(Cybersport.class.getSimpleName(), saveCybersport);
                startActivity(nextIntent);
            }
        };
        nextButton.setOnClickListener(nextButtonListener);
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        Cybersport saveCybersport = new Cybersport();
//        saveCybersport.setName(String.valueOf(name.getText()));
//        saveCybersport.setLastName(String.valueOf(lastName.getText()));
//        if (!String.valueOf(age.getText()).isEmpty()) {
//            saveCybersport.setAge(Integer.parseInt(String.valueOf(age.getText())));
//        }
//        savedInstanceState.putString("name", saveCybersport.getName());
//        savedInstanceState.putString("lastName", saveCybersport.getLastName());
//        savedInstanceState.putString("age", String.valueOf(saveCybersport.getAge()));
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        name.setText(((Cybersport)savedInstanceState.getSerializable("name")).getName());
//        lastName.setText(((Cybersport)savedInstanceState.getSerializable("lastName")).getLastName());
//        age.setText(((Cybersport)savedInstanceState.getSerializable("age")).getAge());
//
//    }

}
