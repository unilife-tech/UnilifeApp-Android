package unilife.com.unilife.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class PasswordActivity extends BaseActivity {

    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;

    String strPassword = "";
    String strConfirmPassword = "";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_password;
    }

    public void callNext(View view) {
        strPassword = edtPassword.getText().toString().trim();
        strConfirmPassword = edtConfirmPassword.getText().toString().trim();

        if (strPassword.equals("")) {
            edtPassword.setHint("Enter password");
            edtPassword.setHintTextColor(Color.RED);
        } else if (strConfirmPassword.equals("")) {
            edtConfirmPassword.setHint("Confirm password");
            edtConfirmPassword.setHintTextColor(Color.RED);
        } else if (!strConfirmPassword.equals(strPassword)) {
            showToast("Password and confirm password must be same");
        } else {
            sharePref.setPassword(strPassword);
            start(AddPhotoActivity.class);
        }
    }
}