package nao.fit.bstu.lab3;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FragmentItem extends Fragment implements View.OnClickListener {
    Cybersport cybersport;
    TextView name;
    TextView lastName;
    TextView age;
    TextView salary;
    TextView email;
    TextView instagram;
    TextView phoneNumber;
    ImageView photo;
    Button takePhoto;
    Button getPhoto;
    Button backFrag;
    View view;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intagram:
                clickSocialNetwork(v);
                break;
            case R.id.phoneNumber:
                clickNumberPhone(v);
                break;
            case R.id.email:
                clickEmail(v);
                break;
            case R.id.photo_button:
                takePhoto(v);
                break;
            case R.id.get_button:
                choosePhoto(v);
                break;
            case R.id.back_frag_button:
                backFrag(v);
                break;
        }
    }

    public static FragmentItem newInstance(String param1, String param2) {
        FragmentItem fragment = new FragmentItem();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);

        name = view.findViewById(R.id.name);
        lastName = view.findViewById(R.id.lastName);
        age = view.findViewById(R.id.age);
        salary = view.findViewById(R.id.salary);
        email = view.findViewById(R.id.email);
        instagram = view.findViewById(R.id.intagram);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        photo = view.findViewById(R.id.photo);
        getPhoto=view.findViewById(R.id.get_button);
        takePhoto=view.findViewById(R.id.photo_button);
        backFrag=view.findViewById(R.id.back_frag_button);

        instagram.setOnClickListener(this);
        phoneNumber.setOnClickListener(this);
        email.setOnClickListener(this);
        getPhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        instagram.setOnClickListener(this);
        backFrag.setOnClickListener(this);
        return view;
    }

    public void setCybersport(Cybersport cybersportParam) {
        cybersport = cybersportParam;
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

    public void saveObject() {
        List<Cybersport> cybersportList = JSONMethod.importFromJSON(view.getContext());
        cybersportList.stream().forEach(cybersportItem -> {
            if (cybersportItem.getId() == cybersport.getId())
                cybersportItem.setPhoto(cybersport.getPhoto());
        });
        boolean result = JSONMethod.exportToJSON(view.getContext(), cybersportList);
    }

    public void clickEmail(View view) {
        TextView email = view.findViewById(R.id.email);

        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lector");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Email text");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

    }

    public void clickSocialNetwork(View view) {
        TextView instagram = view.findViewById(R.id.intagram);
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
            Toast.makeText(view.getContext(), "!!!!!!!!!!!!! ", Toast.LENGTH_SHORT).show();
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

    public void backFrag(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public void backButtonWasPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    cybersport.setPhoto(String.valueOf(data.getData()));
                    photo.setImageURI(data.getData());
                    photo.getLayoutParams().width = 200;
                    break;
            }
        } else {
            Toast.makeText(view.getContext(), "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}