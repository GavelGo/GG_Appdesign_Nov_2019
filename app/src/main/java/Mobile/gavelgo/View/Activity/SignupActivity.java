package Mobile.gavelgo.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import Mobile.gavelgo.Controller.Apis.UserRegisterApi;
import Mobile.gavelgo.Controller.Utills;
import Mobile.gavelgo.Model.RegisterUserResponse;
import Mobile.gavelgo.R;

public class SignupActivity extends Activity implements View.OnClickListener {

    TextView loginTV;
    EditText usernameET, emailET, passET, conpassET;
    Button signupBT;
    ImageView passIV, conpassIV;
    Spinner userSP;
    String selected_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        loginTV = (TextView) findViewById(R.id.loginTV);
        usernameET = (EditText) findViewById(R.id.usernameET);
        emailET = (EditText) findViewById(R.id.emailET);
        passET = (EditText) findViewById(R.id.passET);
        conpassET = (EditText) findViewById(R.id.conpassET);
        signupBT = (Button) findViewById(R.id.signupBT);
        passIV = (ImageView) findViewById(R.id.passIV);
        conpassIV = (ImageView) findViewById(R.id.conpassIV);
        userSP = (Spinner) findViewById(R.id.userSP);

        loginTV.setOnClickListener(this);
        signupBT.setOnClickListener(this);
        passIV.setOnClickListener(this);
        conpassIV.setOnClickListener(this);


        userSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selected_position = adapterView.getSelectedItem() + "";

                //  Toast.makeText(SignupActivity.this,adapterView.getSelectedItem()+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch ((view.getId())) {

            case R.id.loginTV:

                onBackPressed();

                break;

            case R.id.passIV:

                if ("work".equals(passIV.getTag())) {
                    passIV.setBackground(getResources().getDrawable(R.drawable.hide_password));
                    passET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passET.setSelection(passET.length());
                    passIV.setTag("not work");

                } else {
                    passIV.setBackground(getResources().getDrawable(R.drawable.password_visible));
                    passET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passET.setSelection(passET.length());
                    passIV.setTag("work");
                }


                break;

            case R.id.conpassIV:

                if ("work".equals(conpassIV.getTag())) {
                    conpassIV.setBackground(getResources().getDrawable(R.drawable.hide_password));
                    conpassET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    conpassET.setSelection(conpassET.length());
                    conpassIV.setTag("not work");

                } else {
                    conpassIV.setBackground(getResources().getDrawable(R.drawable.password_visible));
                    conpassET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    conpassET.setSelection(conpassET.length());
                    conpassIV.setTag("work");
                }

                break;

            case R.id.signupBT:

                Intent intent1 = new Intent(SignupActivity.this, AddPartnerInformation.class);
                startActivity(intent1);

                /*if (usernameET.getText().toString().equals("")) {
                    Utills.showalerter(SignupActivity.this, "Please enter username");
                } else if (emailET.getText().toString().equals("")) {
                    Utills.showalerter(SignupActivity.this, "Please enter email");
                } else if (isEmailValid(emailET.getText().toString()) == false) {
                    Utills.showalerter(SignupActivity.this, "Please enter valid email");

                } else if (passET.getText().toString().equals("")) {
                    Utills.showalerter(SignupActivity.this, "Please enter password");
                } else if (conpassET.getText().toString().equals("")) {
                    Utills.showalerter(SignupActivity.this, "Please enter confirm password");
                } else if (!conpassET.getText().toString().equals(passET.getText().toString())) {
                    Utills.showalerter(SignupActivity.this, "Password does not matched");
                } else if (selected_position.equals("Select user type")) {

                    Utills.showalerter(SignupActivity.this, "Please select any user type");

                } else if (selected_position.equals("Consumer")) {

                    registerUserApi();

                } else {

                    Intent intent1 = new Intent(SignupActivity.this, AddPartnerInformation.class);
                    startActivity(intent1);

                }*/
                break;
        }
    }

    private void registerUserApi() {

        UserRegisterApi userRegisterApi = new UserRegisterApi();

        if (Utills.isConnectingToInternet(SignupActivity.this)) {
            try {
                Utills.showDialog(SignupActivity.this);
                userRegisterApi.home_splash(SignupActivity.this, usernameET.getText().toString(), emailET.getText().toString(), passET.getText().toString(), new UserRegisterApi.RegisterUser_CallBack() {


                    @Override
                    public void onSuccess(@Nullable RegisterUserResponse body) {

                        Log.d("tag","Api "+ "success");
                        Utills.progressDialog_dismiss(SignupActivity.this);

                        Intent intent1 = new Intent(SignupActivity.this, AddConsumerInformation.class);
                        startActivity(intent1);

                    }

                    @Override

                    public void onFailure(@NotNull String body) {

                        Utills.progressDialog_dismiss(SignupActivity.this);
                        Log.d("tag","Api"+ "faliure msg="+body);


                    }


                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Utills.showalerter(this, "Please check your internet connection");
        }
    }


    public Boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
