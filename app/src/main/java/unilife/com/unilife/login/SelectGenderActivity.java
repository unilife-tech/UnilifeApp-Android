package unilife.com.unilife.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class SelectGenderActivity extends BaseActivity {

    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radioOther)
    RadioButton radioOther;
    String strGender = "";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                strGender="Male";
            }
        });
        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                strGender="Female";
            }
        });
        radioOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                strGender="Other";
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_gender;
    }

    public void callNext(View view) {
        sharePref.setGender(strGender);
        start(BirthdayActivity.class);
    }
}