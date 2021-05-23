package unilife.com.unilife.brands;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.retro.WebUrls;
import unilife.com.unilife.updated.BaseActivity;

public class InstoreRedeemActivity extends BaseActivity {

    BrandDetailsResponse.Store store;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtDesc)
    TextView txtDesc;

    @BindView(R.id.edt1)
    EditText edt1;
    @BindView(R.id.edt2)
    EditText edt2;
    @BindView(R.id.edt3)
    EditText edt3;
    @BindView(R.id.edt4)
    EditText edt4;
    @BindView(R.id.edt5)
    EditText edt5;
    @BindView(R.id.edt6)
    EditText edt6;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideKeyboard();
        store = getIntent().getParcelableExtra("item");
        txtDesc.setText(getHtmlConverted(store.getDescription()));
        Glide.with(mContext)
                .load(WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance().getPreferenceData(
                        mContext,
                        WebUrls.KEY_PROFILE_IMAGE)).into(imgProfile);
        txtName.setText(PreferenceFile.getInstance().getPreferenceData(mContext, WebUrls.KEY_USERNAME));
        txtEmail.setText(PreferenceFile.getInstance().getPreferenceData(mContext, WebUrls.KEY_EMAIL));

        edt1.requestFocus();
        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edt2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edt3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edt4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edt5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edt6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_instore_redeem;
    }

    public void goBack(View view) {
        finish();
    }


    public void callRedeem(View view) {

        String strCode =
                edt1.getText().toString().trim() + "" +
                        edt2.getText().toString().trim() + "" +
                        edt3.getText().toString().trim() + "" +
                        edt4.getText().toString().trim() + "" +
                        edt5.getText().toString().trim() + "" +
                        edt6.getText().toString().trim();

        Log.e("code", strCode);

        if (strCode.equals("")) {
            showToast("Please enter code");
        } else {
            if (strCode.equalsIgnoreCase(store.getCode())) {
                Intent intent = new Intent(mContext, BranchReceiptActivity.class);
                intent.putExtra("item", store);
                intent.putExtra("code", strCode);
                startActivity(intent);
            } else {
                showToast("Invalid code");
            }
        }
    }
}