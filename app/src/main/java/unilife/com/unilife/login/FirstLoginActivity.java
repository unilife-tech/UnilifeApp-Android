package unilife.com.unilife.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;
import unilife.com.unilife.Login;
import unilife.com.unilife.R;
import unilife.com.unilife.login.adapter.LoginPagerAdapter;
import unilife.com.unilife.updated.BaseActivity;

public class FirstLoginActivity extends BaseActivity {

    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    ArrayList<Integer> arrayListBack = new ArrayList<Integer>();
    ArrayList<String> arrayListText = new ArrayList<String>();

    @BindView(R.id.txtSliding)
    TextView txtSliding;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        arrayList.add(R.drawable.background_1);
        arrayList.add(R.drawable.spl_img_2);
        arrayList.add(R.drawable.spl_img_6);
        arrayList.add(R.drawable.spl_img_3);
        arrayList.add(R.drawable.spl_img_4);
        arrayList.add(R.drawable.spl_img_5);

        arrayListBack.add(0);
        arrayListBack.add(R.drawable.spl_back_1);
        arrayListBack.add(R.drawable.spl_back_5);
        arrayListBack.add(R.drawable.spl_back_4);
        arrayListBack.add(R.drawable.spl_back_3);
        arrayListBack.add(R.drawable.spl_back_2);

        arrayListText.add("");
        arrayListText.add("New way to communicate in unilife\n" +
                "individual and group chat");
        arrayListText.add("Be updated on events\n" +
                "and activities in Unilife");
        arrayListText.add("Browse through your\n" +
                "friends profile");
        arrayListText.add("Exclusive Student\n" +
                "Discounts And Offers");
        arrayListText.add("Explore Student\n" +
                "lifestyle contents");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new LoginPagerAdapter(mContext, arrayList));

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        findViewById(R.id.backFL).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.releLogintemp).setVisibility(View.VISIBLE);
        findViewById(R.id.releRegistertemp).setVisibility(View.VISIBLE);
        findViewById(R.id.releLogin).setVisibility(View.GONE);
        findViewById(R.id.releRegister).setVisibility(View.GONE);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                txtSliding.setText(arrayListText.get(i));
                imgBack.setImageResource(arrayListBack.get(i));
                if (i == 0) {
                    findViewById(R.id.backFL).setBackgroundColor(Color.TRANSPARENT);
                    findViewById(R.id.releLogintemp).setVisibility(View.VISIBLE);
                    findViewById(R.id.releRegistertemp).setVisibility(View.VISIBLE);

                    findViewById(R.id.releLogin).setVisibility(View.GONE);
                    findViewById(R.id.releRegister).setVisibility(View.GONE);


                } else {
                    findViewById(R.id.backFL).setBackgroundColor(Color.WHITE);
                    findViewById(R.id.releLogintemp).setVisibility(View.GONE);
                    findViewById(R.id.releRegistertemp).setVisibility(View.GONE);

                    findViewById(R.id.releLogin).setVisibility(View.VISIBLE);
                    findViewById(R.id.releRegister).setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_first_login;
    }

    public void openLogin(View view) {
        intent = new Intent(mContext, Login.class);
        startActivity(intent);
    }

    public void openRegister(View view) {
        start(SchoolNameActivity.class);
    }
}