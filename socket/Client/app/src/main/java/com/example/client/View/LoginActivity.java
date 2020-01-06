package com.example.client.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.client.R;
import com.example.client.Util.SharedPreferencesUtils;
import com.example.client.Util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mUserName, mUserPwd;

    ImageView img_remember, img_auto_login;

    boolean isRemberPwd;//Whether to remember password
    boolean isAuto;//Whether to auto login

    //Keys used for SharedPreferencesUtils
    String userNameKey = "userNameKey";
    String userPwdKey = "userPwdKey";
    String rememberKey = "rememberKey";
    String autoLoginKey = "autoLoginKey";

    String userName = "";
    String userPwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();

        initWidget();
    }

    //region Initialize variables
    private void initData() {
        isRemberPwd = (boolean) SharedPreferencesUtils.getParam(this, rememberKey, false);
        isAuto = (boolean) SharedPreferencesUtils.getParam(this, autoLoginKey, false);

        if (isRemberPwd) {
            userName = (String) SharedPreferencesUtils.getParam(this, userNameKey, "");
            userPwd = (String) SharedPreferencesUtils.getParam(this, userPwdKey, "");
        }
    }
    //endregion

    //region Initialize page layout
    private void initWidget() {
        mUserName = (EditText) findViewById(R.id.username);
        mUserName.setText(userName);

        mUserPwd = (EditText) findViewById(R.id.userpwd);
        mUserPwd.setText(userPwd);

        img_remember = (ImageView) findViewById(R.id.img_remember);
        initRememberImg();

        img_auto_login = (ImageView) findViewById(R.id.img_auto_login);
        initAutoLoginImg();

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        findViewById(R.id.lly_remember_pwd).setOnClickListener(this);
        findViewById(R.id.lly_auto_login).setOnClickListener(this);

        if (isAuto && userName.equals("hhh") & userPwd.equals("111")) {
            Intent intent_login_to_user = new Intent(LoginActivity.this, UserActivity.class);
            startActivity(intent_login_to_user);
        }
    }
    //endregion

    //region onClick event

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
            case R.id.lly_remember_pwd:
                remember();
                break;
            case R.id.lly_auto_login:
                autoLogin();
                break;
        }
    }

    //endregion

    //region Login
    private void login() {
        userName = mUserName.getText().toString();
        userPwd = mUserPwd.getText().toString();

        if (userName.isEmpty()) {
            ToastUtil.showShort(LoginActivity.this, "Please enter your username");
            return;
        }

        if (userPwd.isEmpty()) {
            ToastUtil.showShort(LoginActivity.this, "Please enter your password");
            return;
        }

        if (userName.equals("hhh") &
                userPwd.equals("111")) {
            Intent intent_login_to_user = new Intent(LoginActivity.this, UserActivity.class);
            startActivity(intent_login_to_user);
        } else {
            ToastUtil.showShort(LoginActivity.this, "This is only a mock login page, only user name \"hhh\" and password \"111\" can log you in ");
        }

        SharedPreferencesUtils.setParam(this, userNameKey, userName);
        SharedPreferencesUtils.setParam(this, userPwdKey, userPwd);
    }
    //endregion

    //region Registration
    private void register() {
        Intent intent_login_to_register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent_login_to_register);
    }
    //endregion

    //region Remember password
    private void remember() {
        isRemberPwd = !isRemberPwd;
        initRememberImg();

        SharedPreferencesUtils.setParam(this, rememberKey, isRemberPwd);
    }

    private void initRememberImg() {
        if (isRemberPwd) {
            img_remember.setBackgroundResource(R.mipmap.remember_checked);
        } else {
            img_remember.setBackgroundResource(R.mipmap.remember_unchecked);
        }
    }
    //endregion

    //region Auto login
    private void autoLogin() {
        isAuto = !isAuto;
        initAutoLoginImg();

        SharedPreferencesUtils.setParam(this, autoLoginKey, isAuto);
    }

    private void initAutoLoginImg() {
        if (isAuto) {
            img_auto_login.setBackgroundResource(R.mipmap.remember_checked);
        } else {
            img_auto_login.setBackgroundResource(R.mipmap.remember_unchecked);
        }
    }

    //endregion
}
