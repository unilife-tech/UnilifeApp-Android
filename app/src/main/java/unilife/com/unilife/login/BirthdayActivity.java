package unilife.com.unilife.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class BirthdayActivity extends BaseActivity {

    @BindView(R.id.edtBirtday)
    EditText edtBirthday;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_birthday;
    }

    public void callNext(View view) {
        start(PasswordActivity.class);
    }

    public void selectDate(View view) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mdiDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edtBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                sharePref.setBirthday(edtBirthday.getText().toString().trim());

            }
        }, mYear, mMonth, mDay);
        mdiDialog.show();
    }
}
