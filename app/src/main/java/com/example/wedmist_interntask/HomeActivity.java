package com.example.wedmist_interntask;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    String userEmail, userpassword, username, userDob;

    TextView tvEmail, tvPwd, tvName, tvDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvEmail = findViewById(R.id.tvEmail);
        tvPwd = findViewById(R.id.tvPassword);
        tvName = findViewById(R.id.tvName);
        tvDob = findViewById(R.id.tvDOB);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("email");
        }


        fetchUserDetails(userEmail);
    }


    private void fetchUserDetails(String userEmail) {

        Cursor userCursor = new DBHelper(this).getUserDetails(userEmail);
        try {
            if (userCursor.moveToFirst()) {
                do {
                    userEmail = userCursor.getString(userCursor.getColumnIndex("email"));
                    userpassword = userCursor.getString(userCursor.getColumnIndex("password"));
                    username = userCursor.getString(userCursor.getColumnIndex("name"));
                    userDob = userCursor.getString(userCursor.getColumnIndex("dob"));

                } while (userCursor.moveToNext());

                tvEmail.setText("Email : " + userEmail);
                tvPwd.setText("Password : " + userpassword);
                tvName.setText("Name : " + username);
                tvDob.setText("Date of birth : " + userDob);
            }
        } finally {
            userCursor.close();
        }
    }

    public void onLogOut(View view) {
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}