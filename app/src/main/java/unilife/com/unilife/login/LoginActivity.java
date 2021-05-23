package unilife.com.unilife.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login2;
    }

    public void callLogin(View view) {
    }

    public void callRegister(View view) {
    }

    public void callForgotPass(View view) {
    }
}