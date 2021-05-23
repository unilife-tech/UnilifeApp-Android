package unilife.com.unilife.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class FirstLastNameActivity extends BaseActivity {

    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;

    String strFirstName = "";
    String strLastName = "";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_first_last_name;
    }

    public void callNext(View view) {
        strFirstName = edtFirstName.getText().toString().trim();
        strLastName = edtLastName.getText().toString().trim();

        if (strFirstName.equals("")) {
            edtFirstName.setHint("Enter first name");
            edtFirstName.setHintTextColor(Color.RED);
        } else if (strLastName.equals("")) {
            edtLastName.setHint("Enter last name");
            edtLastName.setHintTextColor(Color.RED);
        } else {
            sharePref.setUsername(strFirstName + " " + strLastName);
            start(SelectGenderActivity.class);
        }
    }
}