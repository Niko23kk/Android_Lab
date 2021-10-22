package nao.fit.bstu.lab3.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nao.fit.bstu.lab3.Room.Cybersport;
import nao.fit.bstu.lab3.MyDatabaseHelper;
import nao.fit.bstu.lab3.R;
import nao.fit.bstu.lab3.RequestPermissions;

public class CybersportInfoUpdateActivity extends AppCompatActivity {
    Cybersport cybersport;
    TextView name;
    TextView lastName;
    TextView age;
    TextView salary;
    TextView email;
    TextView instagram;
    TextView phoneNumber;
    ImageView photo;
    TextView backButton;

    MyDatabaseHelper databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cybersport_info_update);

        RequestPermissions requestPermissions = new RequestPermissions();
        if (!requestPermissions.permissionGranted) {
            requestPermissions.checkPermissions(this, this);
        }

        databaseHelper=new MyDatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        salary = findViewById(R.id.salary);
        email = findViewById(R.id.email);
        instagram = findViewById(R.id.intagram);
        phoneNumber = findViewById(R.id.phoneNumber);
        photo = findViewById(R.id.photo);
        backButton = (TextView) findViewById(R.id.backButton);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            cybersport = (Cybersport) arguments.getSerializable(Cybersport.class.getSimpleName());
            name.setText(cybersport.getName());
            lastName.setText(cybersport.getLastName());
            age.setText(String.valueOf(cybersport.getAge()));
            salary.setText(String.valueOf(cybersport.getSalary()));
            email.setText(cybersport.getEmail());
            instagram.setText(cybersport.getInstagram());
            phoneNumber.setText(cybersport.getPhoneNumber());
            if (cybersport.getPhoto() != null) {
//                photo.setImageURI(Uri.parse(cybersport.getPhoto()));
//                photo.setMaxWidth(30);
                //ImagePath.getPath(this, Uri.parse(cybersport.getPhoto()));
//                Intent intentGet = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intentGet.setType("image/*");
//                startActivityForResult(intentGet,10);
//                photo.setImageURI(Uri.parse(cybersport.getPhoto()));
//                photo.getLayoutParams().width=200;
            }
//            if (cybersport.getPhoto() != null) {
//                photo.setImageURI(
//                        FileProvider.getUriForFile(
//                                this,
//                                getApplicationContext().getPackageName() + ".provider",
//                                new File(cybersport.getPhoto())
//                        )
//                );
//            }
        }


        Intent intent = new Intent(this, MainActivity.class);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Back ti main", Toast.LENGTH_SHORT).show();
            }
        };
        backButton.setOnClickListener(itemListener);
    }

    public void saveObject(View view) {
        ContentValues cv=new ContentValues();
        cv.put(MyDatabaseHelper.COLUMN_ID,cybersport.getId());

        cv.put(MyDatabaseHelper.NAME,String.valueOf(name.getText()));
        cv.put(MyDatabaseHelper.LAST_NAME,String.valueOf(lastName.getText()));
        if (!String.valueOf(age.getText()).isEmpty()) {
            cv.put(MyDatabaseHelper.AGE,String.valueOf(age.getText()));
        } else {
            cv.put(MyDatabaseHelper.AGE,0);
        }
        if (!String.valueOf(salary.getText()).isEmpty()) {
            cv.put(MyDatabaseHelper.SALARY,String.valueOf(salary.getText()));
        } else {

            cv.put(MyDatabaseHelper.SALARY,0.0);
        }
        cv.put(MyDatabaseHelper.EMAIL,String.valueOf(email.getText()));
        cv.put(MyDatabaseHelper.INSTAGRAM,String.valueOf(instagram.getText()));
        cv.put(MyDatabaseHelper.PHONE_NUMBER,String.valueOf(phoneNumber.getText()));
        databaseHelper.updateItem(db,String.valueOf(cybersport.getId()),cv);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void takePhoto(View view) {
        startActivityForResult(new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA), 1);
    }

    public void choosePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    cybersport.setPhoto(String.valueOf(data.getData()));
                    photo.setImageURI(data.getData());
                    photo.getLayoutParams().width = 200;
                    break;
            }
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}