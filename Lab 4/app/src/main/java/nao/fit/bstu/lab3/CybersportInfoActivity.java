package nao.fit.bstu.lab3;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class CybersportInfoActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cybersport_info);

        RequestPermissions requestPermissions=new RequestPermissions();
        if(!requestPermissions.permissionGranted){
            requestPermissions.checkPermissions(this,this);
        }

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
            if(cybersport.getPhoto()!=null) {
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
                saveObject();
                Toast.makeText(getApplicationContext(), "Back ti main", Toast.LENGTH_SHORT).show();
            }
        };
        backButton.setOnClickListener(itemListener);
    }

    public void saveObject(){
        List<Cybersport> cybersportList=JSONMethod.importFromJSON(this);
        cybersportList.stream().forEach(cybersportItem ->{
            if(cybersportItem.getId()==cybersport.getId())
                cybersportItem.setPhoto(cybersport.getPhoto());
        });
        boolean result = JSONMethod.exportToJSON(this, cybersportList);
    }

    public void clickEmail(View view) {
        TextView email = findViewById(R.id.email);

        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lector");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Email text");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

    }

    public void clickSocialNetwork(View view) {
        TextView instagram = findViewById(R.id.intagram);
        Intent appIntent = null, webIntent = null;
        String idURL = instagram.getText().toString();

        if (idURL.contains("vk.com")) {
            idURL = idURL.replace("vk.com", "");
            appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vk.com" + idURL));
        } else if (idURL.contains("instagram.com")) {
            idURL = idURL.replace("instagram.com", "");
            appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com" + idURL));
        } else {
            appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + idURL));
        }

        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(appIntent);
            Toast.makeText(getApplicationContext(), "!!!!!!!!!!!!! ", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickNumberPhone(View view) {
        String toDial = "tel:" + phoneNumber.getText().toString();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toDial)));
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
                    photo.getLayoutParams().width=200;
                    break;
            }
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}