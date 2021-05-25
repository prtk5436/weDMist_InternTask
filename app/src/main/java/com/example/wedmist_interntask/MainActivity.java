package com.example.wedmist_interntask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etEMail, etPassword;

    String email, password;

    DBHelper mydbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);
        etEMail = findViewById(R.id.et_mail);
        etPassword = findViewById(R.id.et_pwd);

        mydbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEMail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter valid id/password", Toast.LENGTH_LONG).show();
                } else {

                    Boolean resultLogin = mydbHelper.checkCredentials(email, password);
                    if (resultLogin == true) {

                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        i.putExtra("email", email);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "please enter valid id/password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    public void onSignup(View view) {
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
    }
}