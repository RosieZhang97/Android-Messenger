package com.example.client.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.R;

public class RegisterActivity extends AppCompatActivity {

    String tempData;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button sure = (Button) findViewById(R.id.sure);
        Button cancel = (Button) findViewById(R.id.cancel);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register_to_user = new Intent(RegisterActivity.this, UserActivity.class);
                startActivity(intent_register_to_user);
                EditText username = (EditText) findViewById(R.id.edit_name);
                tempData = "Hello, " + username.getText().toString() + "!";
                Toast.makeText(RegisterActivity.this, "welcome back, " + tempData + "!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences("data",
                        MODE_PRIVATE).edit();
                editor.putString("name", tempData);
                editor.apply();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register_to_login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent_register_to_login);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText username = (EditText) findViewById(R.id.edit_name);
        //String tempData=username.getText().toString();
        outState.putString("data_key", tempData);
    }
}
