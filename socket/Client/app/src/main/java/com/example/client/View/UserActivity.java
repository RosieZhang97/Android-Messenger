package com.example.client.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.Util.SharedPreferencesUtils;

public class UserActivity extends AppCompatActivity {

    //The keys used by SharedPreferencesUtils
    String userNameKey = "userNameKey";
    String autoLoginKey = "autoLoginKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView text = (TextView) findViewById(R.id.txt_user);
        text.setText((String) SharedPreferencesUtils.getParam(this, userNameKey, ""));


        findViewById(R.id.returnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.friendly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setParam(UserActivity.this, autoLoginKey, false);

                Intent intent = new Intent(UserActivity.this, FriendlyActivity.class);
                startActivity(intent);
            }
        });
    }
}
