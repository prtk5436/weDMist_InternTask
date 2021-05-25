package com.example.wedmist_interntask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class SignUpActivity extends AppCompatActivity {


    Button btnSignUp;
    EditText etEMail, etPassword, etCPassword, etName, etDOB;

    String email, password, Cpassword, name, dob;

    DBHelper mydbHelper;
    MaterialDatePicker.Builder materialDateBuilder;
    MaterialDatePicker materialDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.btn_signUp);
        etEMail = findViewById(R.id.et_mail);
        etPassword = findViewById(R.id.et_pwd);
        etCPassword = findViewById(R.id.et_Cpwd);
        etName = findViewById(R.id.et_name);
        etDOB = findViewById(R.id.et_dob);

        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDatePicker = materialDateBuilder.build();
        mydbHelper = new DBHelper(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEMail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                Cpassword = etCPassword.getText().toString().trim();
                name = etName.getText().toString().trim();
                dob = etDOB.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty() || Cpassword.isEmpty() || name.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "please enter all details", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    etPassword.setError("password minimum contain 6 character");
                    etPassword.requestFocus();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEMail.setError("please enter valid email address");
                    etEMail.requestFocus();
                } else if (!email.equals("") && etPassword.getText().toString().length() >= 6 &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // do  your action

                    if (Cpassword.equals(password)) {
                        Boolean result = mydbHelper.checkUser(email);
                        if (result == false) {
                            Boolean resultREG = mydbHelper.insertData(email, password, name, dob);
                            if (resultREG == true) {

                                Toast.makeText(SignUpActivity.this, "Registration success", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            } else {

                                Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "user alresdy aexists", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        etCPassword.setError("password not match");
                        etCPassword.requestFocus();
                    }


                }
            }
        });

    }

    public void onBack(View view) {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void selectDate(View view) {

        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        etDOB.setText(materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });
    }
}