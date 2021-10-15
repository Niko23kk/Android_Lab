package nao.fit.bstu.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private float age = 0;
    private float height = 0;
    private float weight = 0;
    private float coef = 1.9f;
    private String sex = "";
    private float result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        for (int i = 0; i < spinner.getChildCount(); i++) {
//            View view= spinner.getChildAt(i);
//            ((TextView) view)
//        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 1: {
                        coef = 1.9f;
                        break;
                    }
                    case 2: {
                        coef = 1.6f;
                        break;
                    }
                    case 3: {
                        coef = 1.52f;
                        break;
                    }
                    case 4: {
                        coef = 1.375f;
                        break;
                    }
                    case 5: {
                        coef = 1.3f;
                        break;
                    }
                    case 6: {
                        coef = 1.25f;
                        break;
                    }
                    case 7: {
                        coef = 1.2f;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        initTextChangedEvent();

        RadioGroup sexGroup = (RadioGroup) findViewById(R.id.sexgroup);
        for (int i = 0; i < sexGroup.getChildCount(); i++) {
            sexGroup.getChildAt(i).setOnClickListener(radioButtonClickListener);
        }


        Button calculateButton = (Button) findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(calculateButtonClickListener);
    }

    private void initTextChangedEvent() {
        EditText heightIdText = (EditText) findViewById(R.id.height);
        heightIdText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    height = Float.parseFloat(s.toString());
            }
        });

        EditText weightIdText = (EditText) findViewById(R.id.weight);
        heightIdText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    weight = Float.parseFloat(s.toString());
            }
        });

        EditText ageIdText = (EditText) findViewById(R.id.age);
        heightIdText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    age = Float.parseFloat(s.toString());
            }
        });
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.female:
                    sex = "f";
                    break;
                case R.id.male:
                    sex = "m";
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener calculateButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            double tempBMR=0;
            double tempAMR=0;
            TextView bmrResult=findViewById(R.id.result_bmr);
            TextView amrResult=findViewById(R.id.result_amr);
            TextView tempResult=findViewById(R.id.result_text);
            if(sex.equals("f")) {
                tempBMR = 655.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * age);
            } else if(sex.equals("m")){
                tempBMR = 66.4730 + (13.7516 * weight) + (5.0033 * height) - (6.7550 * age);
            }
            tempAMR=coef;
            bmrResult.setText(Double.toString(Math.ceil(tempBMR*100)/100));
            amrResult.setText(Double.toString(Math.ceil(tempAMR*100)/100));
            tempResult.setText(Double.toString(Math.ceil(tempAMR*tempBMR*100)/100)+"kkal");
        }
    };
}