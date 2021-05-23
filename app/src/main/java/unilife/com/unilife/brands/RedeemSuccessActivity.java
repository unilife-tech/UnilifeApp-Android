package unilife.com.unilife.brands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class RedeemSuccessActivity extends BaseActivity {

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_redeem_success;
    }

    public void goBack(View view) {
        finish();
    }

    public void shareFriends(View view) {
        inviteFriends();
    }

    public void gobrandHome(View view) {
        Intent intent = new Intent(getApplicationContext(), BrandsHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}